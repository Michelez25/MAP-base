package database;

/**
 * Eccezione lanciata quando non è possibile stabilire la connessione con il database.
 */
public class DatabaseConnectionException extends Exception {

    /**
     * Costruisce l'eccezione senza messaggio descrittivo.
     */
    public DatabaseConnectionException() {
        super();
    }

    /**
     * Costruisce l'eccezione con il messaggio specificato.
     *
     * @param message messaggio descrittivo dell'errore
     */
    public DatabaseConnectionException(String message) {
        super(message);
    }
}