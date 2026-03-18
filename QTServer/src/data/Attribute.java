package data;

import java.io.Serializable;

/**
 * Rappresenta un attributo del dataset in modo astratto. Ogni attributo è caratterizzato da un nome e un indice posizionale.
 */
public abstract class Attribute implements Serializable {

    /** Nome dell'attributo. */
    protected String name;

    /** Indice posizionale dell'attributo nel dataset. */
    protected int index;

    /**
     * Costruisce un attributo con il nome e l'indice specificati.
     *
     * @param name  nome dell'attributo
     * @param index indice posizionale dell'attributo
     */
    public Attribute(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Restituisce il nome dell'attributo.
     *
     * @return nome dell'attributo
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce l'indice posizionale dell'attributo.
     *
     * @return indice dell'attributo
     */
    public int getIndex() {
        return index;
    }

    /**
     * Restituisce una rappresentazione testuale dell'attributo.
     *
     * @return nome dell'attributo
     */
    public String toString() {
        return name;
    }
}
