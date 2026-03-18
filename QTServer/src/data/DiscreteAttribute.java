package data;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.io.Serializable;

/**
 * Rappresenta un attributo discreto del dataset.
 * Estende Attribute e mantiene l'insieme ordinato dei valori distinti che l'attributo può assumere.
 */
public class DiscreteAttribute extends Attribute implements Iterable<String>, Serializable {

    /** Insieme ordinato dei valori distinti dell'attributo. */
    private Set<String> values;

    /**
     * Costruisce un attributo con nome, indice e insieme di valori ammessi.
     *
     * @param name   nome dell'attributo
     * @param index  indice posizionale dell'attributo
     * @param values array dei valori distinti che l'attributo può assumere
     */
    public DiscreteAttribute(String name, int index, String[] values) {
        super(name, index);
        this.values = new TreeSet<>();
        for (String v : values) {
            this.values.add(v);
        }
    }

    /**
     * Restituisce il numero di valori distinti dell'attributo.
     *
     * @return numero di valori distinti
     */
    public int getNumberOfDistinctValues() {
        return values.size();
    }

    /**
     * Restituisce un iteratore sull'insieme dei valori distinti dell'attributo.
     *
     * @return iteratore sui valori distinti
     */
    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }
}