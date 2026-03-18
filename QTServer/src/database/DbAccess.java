package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestisce la connessione al database MySQL.
 * Fornisce i metodi per aprire, ottenere e chiudere la connessione.
 */
public class DbAccess {

    /** Nome della classe del driver JDBC per MySQL. */
    private String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    /** Protocollo JDBC utilizzato. */
    private final String DBMS = "jdbc:mysql";

    /** Indirizzo del server database. */
    private final String SERVER = "localhost";

    /** Nome del database a cui connettersi. */
    private final String DATABASE = "MapDB";

    /** Porta del server database. */
    private final String PORT = "3306";

    /** Nome utente per l'autenticazione al database. */
    private final String USER_ID = "MapUser";

    /** Password per l'autenticazione al database. */
    private final String PASSWORD = "map";

    /** Oggetto che rappresenta la connessione attiva al database. */
    private Connection conn;

    /**
     * Inizializza la connessione al database.
     *
     * @throws DatabaseConnectionException se il driver non viene trovato o la connessione non può essere stabilita
     */
    public void initConnection() throws DatabaseConnectionException {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE +
                                    "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
            conn = DriverManager.getConnection(connectionString);
        } catch (ClassNotFoundException e) {
            throw new DatabaseConnectionException("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Connection failed: " + e.getMessage());
        }
    }

    /**
     * Restituisce la connessione attiva al database.
     *
     * @return oggetto Connection rappresentante la connessione corrente
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * Chiude la connessione al database se è aperta.
     */
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}