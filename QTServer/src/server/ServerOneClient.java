package server;

import java.io.*;
import java.net.Socket;
import mining.QTMiner;
import data.Data;
import database.DbAccess;
import database.DatabaseConnectionException;
import database.EmptySetException;
import java.sql.SQLException;

/**
 * Gestisce la comunicazione con un singolo client su un thread dedicato.
 * La classe implementa il protocollo di comunicazione per permettere al client di 
 * scegliere tabelle, eseguire il mining di cluster e salvare/caricare i risultati.
 */
public class ServerOneClient extends Thread {

    /** Socket per la comunicazione bidirezionale con il client. */
    private Socket socket;

    /** Stream per la ricezione di oggetti dal client. */
    private ObjectInputStream in;

    /** Stream per l'invio di oggetti al client. */
    private ObjectOutputStream out;

    /** Istanza dell'algoritmo di clustering QT. */
    private QTMiner kmeans;

    /** Nome della tabella attualmente selezionata dal database. */
    private String currentTableName;

    /**
     * Inizializza gli stream di input/output e avvia il thread.
     *
     * @param s Il socket attraverso il quale avviene la connessione con il client.
     * @throws IOException Se si verifica un errore nella creazione degli stream.
     */
    public ServerOneClient(Socket s) throws IOException {
        this.socket = s;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in  = new ObjectInputStream(socket.getInputStream());
        System.out.println("[HANDLER] Thread creato per: " + socket.getInetAddress());
        start();
    }

    /**
     * Ciclo di esecuzione del thread. Legge i comandi inviati dal cliente invoca i relativi metodi di gestione.
     */
    @Override
    public void run() {
        try {
            System.out.println("In attesa di comandi...");

            while (true) {
                Object obj = in.readObject();
                if (obj instanceof Integer) {
                    Integer choice = (Integer) obj;
                    System.out.println("Comando ricevuto: " + choice);

                    switch (choice) {
                        case 0:
                            handleStoreTableFromDb();
                            break;
                        case 1:
                            handleLearningFromDbTable();
                            break;
                        case 2:
                            handleStoreClusterInFile();
                            break;
                        case 3:
                            handleLearningFromFile();
                            break;
                        default:
                            out.writeObject("ERR: Scelta non valida: " + choice);
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connessione chiusa o errore: " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Socket chiuso.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gestisce la scelta della tabella dal database (Caso 0).
     * Invia "OK" al client se la tabella esiste ed è accessibile.
     *
     * @throws IOException            Se si verifica un errore di rete.
     * @throws ClassNotFoundException Se la classe dell'oggetto letto non è trovata.
     */
    private void handleStoreTableFromDb() throws IOException, ClassNotFoundException {
        String tableName = (String) in.readObject();
        System.out.println("storeTableFromDb - Tabella: " + tableName);

        try {
            DbAccess db = new DbAccess();
            db.initConnection();
            new Data(tableName);
            db.closeConnection();

            this.currentTableName = tableName;
            out.writeObject("OK");
            System.out.println("Tabella accettata: " + tableName);

        } catch (Exception e) {
            out.writeObject("ERR: " + e.getMessage());
        }
    }

    /**
     * Esegue l'algoritmo di mining sui dati della tabella selezionata (Caso 1).
     * Invia al client il numero di cluster scoperti e la loro rappresentazione testuale.
     *
     * @throws IOException            Se si verifica un errore di rete.
     * @throws ClassNotFoundException Se la classe dell'oggetto letto non è trovata.
     */
    private void handleLearningFromDbTable() throws IOException, ClassNotFoundException {
        double radius = (Double) in.readObject();
        System.out.println("learningFromDbTable - Raggio: " + radius);

        if (currentTableName == null) {
            out.writeObject("ERR: Nessuna tabella selezionata.");
            return;
        }

        try {
            Data data = getDataFromTable(currentTableName);
            kmeans = new QTMiner(radius);
            int numClusters = kmeans.compute(data);

            out.writeObject("OK");
            out.writeObject(numClusters);
            out.writeObject(kmeans.getC().toString());

            System.out.println("Clustering completato.");

        } catch (Exception e) {
            out.writeObject("ERR: " + e.getMessage());
        }
    }

    /**
     * Serializza i cluster attualmente scoperti in un file specificato dal client (Caso 2).
     *
     * @throws IOException            Se si verifica un errore di rete o di scrittura su file.
     * @throws ClassNotFoundException Se la classe dell'oggetto letto non è trovata.
     */
    private void handleStoreClusterInFile() throws IOException, ClassNotFoundException {
        String fileName = (String) in.readObject();
        System.out.println("storeClusterInFile - Nome scelto: " + fileName);

        if (kmeans == null) {
            out.writeObject("ERR: Nessun cluster da salvare.");
            return;
        }

        try {
            kmeans.salva(fileName);
            out.writeObject("OK");
            System.out.println("Cluster salvati correttamente in: " + fileName);

        } catch (Exception e) {
            out.writeObject("ERR: " + e.getMessage());
        }
    }

    /**
     * Carica un set di cluster precedentemente salvato da un file (Caso 3).
     *
     * @throws IOException            Se si verifica un errore di rete o di lettura da file.
     * @throws ClassNotFoundException Se la classe dell'oggetto letto non è trovata.
     */
    private void handleLearningFromFile() throws IOException, ClassNotFoundException {
        String fileName = (String) in.readObject();
        System.out.println("learningFromFile - File: " + fileName);

        try {
            kmeans = new QTMiner(fileName);
            out.writeObject("OK");
            out.writeObject(kmeans.getC().toString());
            System.out.println("Cluster caricati da: " + fileName);
        } catch (Exception e) {
            out.writeObject("ERR: " + e.getMessage());
        }
    }

    /**
     * Recupera i dati dal database per una determinata tabella.
     *
     * @param tableName Il nome della tabella da interrogare.
     * @return Un oggetto contenente le transazioni distinte.
     * @throws DatabaseConnectionException Se la connessione al database fallisce.
     * @throws SQLException                Se si verifica un errore nella query SQL.
     * @throws EmptySetException           Se la tabella non contiene dati.
     */
    private Data getDataFromTable(String tableName)
            throws DatabaseConnectionException, SQLException, EmptySetException {
        DbAccess db = new DbAccess();
        db.initConnection();
        try {
            return new Data(tableName);
        } finally {
            db.closeConnection();
        }
    }
}