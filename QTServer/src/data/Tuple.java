package data;

import java.util.Set;
import java.io.Serializable;

/**
 * Rappresenta una tupla del dataset come array di item.
 * Ogni elemento della tupla corrisponde al valore di un attributo per un esempio.
 */
public class Tuple implements Serializable {

    /** Array di item che compongono la tupla. */
    private Item[] tuple;

    /**
     * Costruisce una tupla di dimensione specificata.
     *
     * @param size numero di item della tupla
     */
    public Tuple(int size) {
        tuple = new Item[size];
    }

    /**
     * Restituisce il numero di item nella tupla.
     *
     * @return lunghezza della tupla
     */
    public int getLength() {
        return tuple.length;
    }

    /**
     * Restituisce l'item nella posizione specificata.
     *
     * @param i indice dell'item
     * @return  item in posizione i
     */
    public Item get(int i) {
        return tuple[i];
    }

    /**
     * Inserisce un item nella posizione specificata della tupla.
     *
     * @param c item da inserire
     * @param i indice in cui inserire l'item
     */
    public void add(Item c, int i) {
        tuple[i] = c;
    }

    /**
     * Calcola la distanza tra questa tupla e un'altra tupla come somma delle distanze tra i rispettivi item.
     *
     * @param obj tupla con cui calcolare la distanza
     * @return    distanza totale tra le due tuple
     */
    public double getDistance(Tuple obj) {
        double distance = 0.0;
        for (int i = 0; i < tuple.length; i++) {
            distance += tuple[i].distance(obj.get(i).getValue());
        }
        return distance;
    }

    /**
     * Calcola la distanza media tra questa tupla e tutte le tuple del dataset identificate dagli indici in clusteredData.
     *
     * @param data          dataset contenente gli esempi
     * @param clusteredData insieme degli indici degli esempi nel cluster
     * @return              distanza media, 0.0 se il cluster è vuoto
     */
    public double avgDistance(Data data, Set<Integer> clusteredData) {
        if (clusteredData.isEmpty()) return 0.0;

        double sumD = 0.0;
        for (int id : clusteredData) {
            double d = getDistance(data.getItemSet(id));
            sumD += d;
        }
        return sumD / clusteredData.size();
    }

    /**
     * Restituisce una rappresentazione testuale della tupla con i valori degli item separati da spazio e racchiusi tra parentesi.
     *
     * @return stringa rappresentante la tupla
     */
    @Override
    public String toString() {
        String s = "( ";
        for (int i = 0; i < tuple.length; i++) {
            s += tuple[i].getValue() + " ";
        }
        s += ")";
        return s;
    }
}