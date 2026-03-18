package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Avvia il server in ascolto sulla porta specificata.
 * Per ogni nuova connessione istanzia un thread ServerOneClient.
 */
public class MultiServer {

    /** Porta su cui il server rimane in ascolto. */
    private int PORT;

    /**
     * Inizializza la porta.
     *
     * @param port porta su cui il server rimane in ascolto
     */
    public MultiServer(int port) {
        this.PORT = port;
        run();
    }

    /**
     * Istanzia un ServerSocket e rimane in attesa di connessioni.
     * Ad ogni nuova connessione crea e avvia un thread ServerOneClient.
     */
    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[SERVER] In ascolto sulla porta " + PORT);
            System.out.println("[SERVER] Pronto per accettare connessioni...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[SERVER] Nuova connessione da: " + socket.getInetAddress());
                try {
                    new ServerOneClient(socket);
                } catch (IOException e) {
                    System.err.println("[SERVER] Errore nella creazione del handler: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("[SERVER] Errore critico: " + e.getMessage());
        }
    }

    /**
     * Istanzia un oggetto MultiServer. La porta può essere passata come argomento, altrimenti si usa 8080.
     *
     */
    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Porta non valida. Uso porta di default: 8080");
            }
        }
        new MultiServer(port);
    }
}