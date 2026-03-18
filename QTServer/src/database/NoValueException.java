package database;

/**
 * Eccezione lanciata quando non è disponibile alcun valore aggregato per la colonna richiesta.
 */
public class NoValueException extends Exception {

    /**
     * Costruisce l'eccezione senza messaggio descrittivo.
     */
    public NoValueException() {
        super();
    }

    /**
     * Costruisce l'eccezione con il messaggio specificato.
     *
     * @param message messaggio descrittivo dell'errore
     */
    public NoValueException(String message) {
        super(message);
    }
}