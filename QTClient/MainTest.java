import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.ServerException;

import keyboardinput.Keyboard;

/**
 * Client per la comunicazione con il server di clustering QT.
 * Permette di caricare dati dal database, eseguire il mining e salvare/caricare i risultati.
 */
public class MainTest {

	/** Stream per l'invio di oggetti al server. */
	private ObjectOutputStream out;

	/** Stream per la ricezione di oggetti dal server. */
	private ObjectInputStream in;

	/**
	 * Stabilisce la connessione con il server all'indirizzo e porta specificati.
	 *
	 * @param ip   indirizzo IP del server
	 * @param port porta del server
	 * @throws IOException se si verifica un errore nella connessione
	 */
	public MainTest(String ip, int port) throws IOException {
		InetAddress addr = InetAddress.getByName(ip);
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, port);
		System.out.println(socket);

		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	/**
	 * Mostra il menu principale e restituisce la scelta dell'utente.
	 *
	 * @return intero corrispondente alla scelta (1 o 2)
	 */
	private int menu() {
		int answer;
		do {
			System.out.println("(1) Load clusters from file");
			System.out.println("(2) Load data from db");
			System.out.print("(1/2):");
			answer = Keyboard.readInt();
		}
		while (answer <= 0 || answer > 2);
		return answer;
	}

	/**
	 * LETTURA:
	 * Invia al server il nome del file in cui sono serializzati i cluster da recuperare.
	 *
	 * @return stringa con la rappresentazione testuale dei cluster caricati
	 * @throws SocketException        se si verifica un errore di rete
	 * @throws ServerException        se il server restituisce un errore
	 * @throws IOException            se si verifica un errore di I/O
	 * @throws ClassNotFoundException se la classe dell'oggetto ricevuto non è trovata
	 */
	private String learningFromFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
		out.writeObject(3);

		System.out.print("File Name:");
		String fileName = Keyboard.readString();
		out.writeObject(fileName);

		String result = (String) in.readObject();
		if (result.equals("OK"))
			return (String) in.readObject();
		else
			throw new ServerException(result);
	}

	/**
	 * Invia al server il nome della tabella da caricare dal database (Caso 0).
	 *
	 * @throws SocketException        se si verifica un errore di rete
	 * @throws ServerException        se il server restituisce un errore
	 * @throws IOException            se si verifica un errore di I/O
	 * @throws ClassNotFoundException se la classe dell'oggetto ricevuto non è trovata
	 */
	private void storeTableFromDb() throws SocketException, ServerException, IOException, ClassNotFoundException {
		out.writeObject(0);
		System.out.print("Table name:");
		String tabName = Keyboard.readString();
		out.writeObject(tabName);
		String result = (String) in.readObject();
		if (!result.equals("OK"))
			throw new ServerException(result);
	}

	/**
	 * Invia il raggio al server e avvia il mining sulla tabella selezionata (Caso 1).
	 *
	 * @return stringa con la rappresentazione testuale dei cluster scoperti
	 * @throws SocketException        se si verifica un errore di rete
	 * @throws ServerException        se il server restituisce un errore
	 * @throws IOException            se si verifica un errore di I/O
	 * @throws ClassNotFoundException se la classe dell'oggetto ricevuto non è trovata
	 */
	private String learningFromDbTable() throws SocketException, ServerException, IOException, ClassNotFoundException {
		out.writeObject(1);
		double r = 1.0;
		do {
			System.out.print("Radius:");
			r = Keyboard.readDouble();
		} while (r <= 0 || Double.isNaN(r));
		out.writeObject(r);
		String result = (String) in.readObject();
		if (result.equals("OK")) {
			System.out.println("Number of Clusters:" + in.readObject());
			return (String) in.readObject();
		} else
			throw new ServerException(result);
	}

	/**
	 * SALVATAGGIO:
	 * Invia al server il nome del file in cui serializzare i cluster scoperti.
	 *
	 * @throws SocketException        se si verifica un errore di rete
	 * @throws ServerException        se il server restituisce un errore
	 * @throws IOException            se si verifica un errore di I/O
	 * @throws ClassNotFoundException se la classe dell'oggetto ricevuto non è trovata
	 */
	private void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
		out.writeObject(2);

		System.out.print("File name:");
		String fileName = Keyboard.readString();
		out.writeObject(fileName);

		String result = (String) in.readObject();
		if (!result.equals("OK"))
			throw new ServerException(result);
	}

	/**
	 * Entry point dell'applicazione client.
	 * Accetta IP e porta come argomenti e avvia il ciclo di interazione con l'utente.
	 *
	 * @param args args[0] indirizzo IP del server, args[1] porta del server
	 */
	public static void main(String[] args) {
		String ip = args[0];
		int port = new Integer(args[1]).intValue();
		MainTest main = null;
		try {
			main = new MainTest(ip, port);
		}
		catch (IOException e) {
			System.out.println(e);
			return;
		}

		do {
			int menuAnswer = main.menu();
			switch (menuAnswer) {
				case 1: // lettura cluster da file
					try {
						String kmeans = main.learningFromFile();
						System.out.println(kmeans);
					} catch (SocketException e) {
						System.out.println(e);
						return;
					} catch (FileNotFoundException e) {
						System.out.println(e);
						return;
					} catch (ServerException e) {
						System.out.println(e.getMessage());
					} catch (IOException e) {
						System.out.println(e);
						return;
					} catch (ClassNotFoundException e) {
						System.out.println(e.getMessage());
					}
					break;

				case 2: // learning from db
					while (true) {
						try {
							main.storeTableFromDb();
							break;
						} catch (SocketException e) {
							System.out.println(e);
							return;
						} catch (FileNotFoundException e) {
							System.out.println(e);
							return;
						} catch (ServerException e) {
							System.out.println(e.getMessage());
						} catch (IOException e) {
							System.out.println(e);
							return;
						} catch (ClassNotFoundException e) {
							System.out.println(e);
						}
					}

					char answer = 'y';
					do {
						try {
							String clusterSet = main.learningFromDbTable();
							System.out.println(clusterSet);

							main.storeClusterInFile();
						} catch (SocketException e) {
							System.out.println(e);
							return;
						} catch (FileNotFoundException e) {
							System.out.println(e);
							return;
						} catch (ServerException e) {
							System.out.println(e.getMessage());
							return;
						} catch (IOException e) {
							System.out.println(e);
							return;
						} catch (ClassNotFoundException e) {
							System.out.println(e);
						}
						System.out.print("Would you repeat?(y/n)");
						answer = Keyboard.readChar();
					}
					while (Character.toLowerCase(answer) == 'y');
					break;

				default:
					System.out.println("Invalid option!");
			}

			System.out.print("would you choose a new operation from menu?(y/n)");
			if (Keyboard.readChar() != 'y')
				break;
		}
		while (true);
	}
}