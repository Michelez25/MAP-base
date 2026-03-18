package mining;

import data.Data;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

/**
 * Rappresenta l'insieme dei cluster prodotti dall'algoritmo di clustering.
 * I cluster sono mantenuti ordinati per dimensione decrescente tramite TreeSet.
 */
public class ClusterSet implements Iterable<Cluster>, Serializable {

    private static final long serialVersionUID = 1L;

    /** Insieme ordinato dei cluster. */
    private Set<Cluster> C = new TreeSet<Cluster>();

    /**
     * Costruisce un insieme di cluster vuoto.
     */
    public ClusterSet() {
    }

    /**
     * Aggiunge un cluster all'insieme.
     *
     * @param c cluster da aggiungere
     */
    public void add(Cluster c) {
        C.add(c);
    }

    /**
     * Restituisce un iteratore sui cluster dell'insieme.
     *
     * @return iteratore sui cluster
     */
    public Iterator<Cluster> iterator() {
        return C.iterator();
    }

    /**
     * Restituisce una rappresentazione testuale dell'insieme dei cluster con il solo centroide di ciascuno.
     *
     * @return stringa con i centroidi di tutti i cluster
     */
    public String toString() {
        String str = "";
        for (Cluster cluster : C) {
            str += cluster.toString() + "\n";
        }
        return str;
    }

    /**
     * Restituisce una rappresentazione testuale dell'insieme dei cluster con centroide, esempi assegnati e distanza media per ciascuno.
     *
     * @param data dataset contenente gli esempi
     * @return     stringa con la descrizione completa di tutti i cluster
     */
    public String toString(Data data) {
        String str = "";
        int index = 1;
        for (Cluster cluster : C) {
            str += index + ":" + cluster.toString(data) + "\n";
            index++;
        }
        return str;
    }
}