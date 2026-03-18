package mining;

/**
 * Eccezione lanciata quando il raggio specificato per il clustering non consente di formare alcun cluster valido.
 */
public class ClusteringRadiusException extends Exception {

    /**
     * Costruisce l'eccezione senza messaggio descrittivo.
     */
    public ClusteringRadiusException() {
        super();
    }

    /**
     * Costruisce l'eccezione con il messaggio specificato.
     *
     * @param message messaggio descrittivo dell'errore
     */
    public ClusteringRadiusException(String message) {
        super(message);
    }
}