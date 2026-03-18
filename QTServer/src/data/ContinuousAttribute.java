package data;

import java.io.Serializable;

/**
 * Rappresenta un attributo continuo (numerico) del dataset.
 * Estende Attribute aggiungendo i valori minimo e massimo per la normalizzazione dei valori.
 */
public class ContinuousAttribute extends Attribute implements Serializable {

    /** Valore massimo assunto dall'attributo nel dataset. */
    private double max;

    /** Valore minimo assunto dall'attributo nel dataset. */
    private double min;

    /**
     * Costruisce un attributo continuo con nome, indice e intervallo di valori.
     *
     * @param name  Nome dell'attributo
     * @param index Indice posizionale dell'attributo
     * @param min   Valore minimo dell'attributo
     * @param max   Valore massimo dell'attributo
     */
    public ContinuousAttribute(String name, int index, double min, double max) {
        super(name, index);
        this.min = min;
        this.max = max;
    }

    /**
     * Calcola e restituisce il valore normalizzato nell'intervallo [0, 1].
     *
     * @param v Valore da normalizzare
     * @return  Valore scalato tra 0 e 1
     */
    public double getScaledValue(double v) {
        return (v - min) / (max - min);
    }

    /**
     * Restituisce il valore massimo dell'attributo.
     *
     * @return Valore massimo
     */
    public double getMax() {
        return max;
    }

    /**
     * Restituisce il valore minimo dell'attributo.
     *
     * @return Valore minimo
     */
    public double getMin() {
        return min;
    }
}