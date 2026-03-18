package data;

import java.io.Serializable;

/**
 * Rappresenta un item con valore discreto.
 * Estende Item calcolando la distanza tra valori discreti come uguaglianza: 0.0 se uguali, 1.0 se diversi.
 */
public class DiscreteItem extends Item implements Serializable {

    /**
     * Costruisce un item discreto con l'attributo e il valore specificati.
     *
     * @param attribute attributo associato all'item
     * @param value     valore dell'item
     */
    public DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }

    /**
     * Calcola la distanza tra il valore di questo item e un altro valore.
     * Restituisce 0.0 se i valori sono uguali, 1.0 altrimenti.
     *
     * @param a valore con cui calcolare la distanza
     * @return  0.0 se i valori coincidono, 1.0 se sono diversi
     */
    public double distance(Object a) {
        if (getValue().equals(a)) {
            return 0.0;
        } else {
            return 1.0;
        }
    }
}