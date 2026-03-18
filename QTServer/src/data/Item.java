package data;

import java.io.Serializable;

/**
 * Rappresenta in modo astratto un item del dataset.
 */
public abstract class Item implements Serializable {

    /** Attributo associato all'item. */
    private Attribute attribute;

    /** Valore dell'item. */
    private Object value;

    /**
     * Costruisce un item con l'attributo e il valore specificati.
     *
     * @param attribute attributo associato all'item
     * @param value     valore dell'item
     */
    public Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Restituisce l'attributo associato all'item.
     *
     * @return attributo dell'item
     */
    public Attribute getAttribute() {
        return attribute;
    }

    /**
     * Restituisce il valore dell'item.
     *
     * @return valore dell'item
     */
    public Object getValue() {
        return value;
    }

    /**
     * Restituisce una rappresentazione testuale dell'item.
     *
     * @return stringa corrispondente al valore dell'item
     */
    public String toString() {
        return value.toString();
    }

    /**
     * Calcola la distanza tra il valore di questo item e un altro valore.
     *
     * @param a valore con cui calcolare la distanza
     * @return  distanza tra i due valori
     */
    public abstract double distance(Object a);
}