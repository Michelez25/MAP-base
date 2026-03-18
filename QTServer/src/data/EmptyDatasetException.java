package data;

/**
 * Eccezione lanciata quando il dataset è vuoto o non contiene dati validi.
 */
public class EmptyDatasetException extends Exception {

    /**
     * Costruisce l'eccezione senza messaggio descrittivo.
     */
    public EmptyDatasetException() {
        super();
    }

    /**
     * Costruisce l'eccezione con il messaggio specificato.
     *
     * @param message messaggio descrittivo dell'errore
     */
    public EmptyDatasetException(String message) {
        super(message);
    }
}