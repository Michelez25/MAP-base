package mining;

import data.Data;
import data.Tuple;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Rappresenta un cluster formato da un centroide e dall'insieme degli indici degli esempi ad esso assegnati.
 */
public class Cluster implements Iterable<Integer>, Comparable<Cluster>, Serializable {

    private static final long serialVersionUID = 1L;

    /** Centroide del cluster. */
    private Tuple centroid;

    /** Insieme degli indici degli esempi assegnati al cluster. */
    private Set<Integer> clusteredData;

    /**
     * Costruisce un cluster con il centroide specificato.
     *
     * @param centroid tupla che rappresenta il centroide del cluster
     */
    public Cluster(Tuple centroid) {
        this.centroid = centroid;
        this.clusteredData = new HashSet<Integer>();
    }

    /**
     * Restituisce il centroide del cluster.
     *
     * @return tupla centroide
     */
    public Tuple getCentroid() {
        return centroid;
    }

    /**
     * Aggiunge un esempio al cluster. Restituisce true se l'esempio ha cambiato cluster.
     *
     * @param id indice dell'esempio da aggiungere
     * @return   true se l'esempio non era già presente nel cluster
     */
    public boolean addData(int id) {
        return clusteredData.add(id);
    }

    /**
     * Verifica se una transazione è clusterizzata nell'array corrente.
	 *
     * @param id indice dell'esempio da verificare
     * @return   true se l'esempio appartiene al cluster
     */
    public boolean contain(int id) {
        return clusteredData.contains(id);
    }

    /**
     * Rimuove un esempio dal cluster.
     *
     * @param id indice dell'esempio da rimuovere
     */
    public void removeTuple(int id) {
        clusteredData.remove(id);
    }

    /**
     * Restituisce il numero di esempi assegnati al cluster.
     *
     * @return dimensione del cluster
     */
    public int getSize() {
        return clusteredData.size();
    }

    /**
     * Restituisce un iteratore sugli indici degli esempi del cluster.
     *
     * @return iteratore sugli indici
     */
    public Iterator<Integer> iterator() {
        return clusteredData.iterator();
    }

    /**
     * Confronta questo cluster con un altro in base alla dimensione.
     * I cluster più grandi vengono considerati minori (ordinamento decrescente).
     *
     * @param other cluster con cui effettuare il confronto
     * @return      valore negativo, zero o positivo in base alla dimensione
     */
    @Override
    public int compareTo(Cluster other) {
        if (this == other) return 0;
        int compare = Integer.compare(other.getSize(), this.getSize());
        if (compare == 0) return 1;
        return compare;
    }

    /**
     * Restituisce una rappresentazione testuale del centroide del cluster.
     *
     * @return stringa con i valori del centroide
     */
    public String toString() {
        String str = "Centroid=(";
        for (int i = 0; i < centroid.getLength(); i++) {
            str += centroid.get(i) + (i == centroid.getLength() - 1 ? "" : " ");
        }
        str += ")";
        return str;
    }

    /**
     * Restituisce una rappresentazione testuale del cluster con centroide, esempi assegnati e distanza media dal centroide.
     *
     * @param data dataset contenente gli esempi
     * @return     stringa con centroide, esempi e distanza media
     */
    public String toString(Data data) {
        String str = "Centroid=(";
        for (int i = 0; i < centroid.getLength(); i++) {
            str += centroid.get(i) + (i == centroid.getLength() - 1 ? "" : " ");
        }
        str += ")\nExamples:\n";

        for (Integer id : clusteredData) {
            str += "[";
            for (int j = 0; j < data.getNumberOfAttributes(); j++) {
                str += data.getAttributeValue(id, j) + (j == data.getNumberOfAttributes() - 1 ? "" : " ");
            }
            str += "] dist=" + centroid.getDistance(data.getItemSet(id)) + "\n";
        }
        str += "AvgDistance=" + centroid.avgDistance(data, clusteredData) + "\n";
        return str;
    }
}