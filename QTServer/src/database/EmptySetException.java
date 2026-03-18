package database;

/**
 * Eccezione lanciata quando il risultato di una query al database è vuoto.
 */
public class EmptySetException extends Exception {

    /**
     * Costruisce l'eccezione senza messaggio descrittivo.
     */
    public EmptySetException() {
        super();
    }

    /**
     * Costruisce l'eccezione con il messaggio specificato.
     *
     * @param message messaggio descrittivo dell'errore
     */
    public EmptySetException(String message) {
        super(message);
    }
}