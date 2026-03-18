import java.io.Serializable;

/**
 * Eccezione sollevata dal server e trasmessa al client tramite lo stream di oggetti.
 * Deve implementare Serializable per poter essere serializzata e inviata via ObjectOutputStream.
 */
public class ServerException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
    * Costruisce l'eccezione con il messaggio specificato.
    *
    * @param message messaggio descrittivo dell'errore
    */
    public ServerException(String message) {
        super(message);
    }
}
