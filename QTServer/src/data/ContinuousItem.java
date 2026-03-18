package data;

import java.io.Serializable;

/**
 * Rappresenta un item con valore continuo (numerico).
 * Estende Item calcolando la distanza tra valori numerici normalizzati nell'intervallo [0, 1].
 */
public class ContinuousItem extends Item implements Serializable {

    /**
     * Costruisce un item continuo con l'attributo e il valore specificati.
     *
     * @param attribute attributo continuo associato all'item
     * @param value     valore numerico dell'item
     */
    public ContinuousItem(Attribute attribute, Double value) {
        super(attribute, value);
    }

    /**
     * Calcola la distanza tra il valore di questo item e un altro valore. Entrambi i valori vengono normalizzati in [0, 1] prima del calcolo.
     * Restituisce 1.0 se i tipi non corrispondono.
     *
     * @param a valore con cui calcolare la distanza
     * @return  distanza normalizzata tra 0.0 e 1.0
     */
    public double distance(Object a) {
        if (a instanceof Double) {
            double value1 = (Double) getValue();
            double value2 = (Double) a;
            ContinuousAttribute attr = (ContinuousAttribute) getAttribute();

            double scaled1 = attr.getScaledValue(value1);
            double scaled2 = attr.getScaledValue(value2);

            return Math.abs(scaled1 - scaled2);
        }
        return 1.0;
    }
}