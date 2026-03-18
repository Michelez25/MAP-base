package database;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un esempio del database come lista di valori oggetto.
 * Implementa Comparable per consentire l'ordinamento e il confronto tra esempi.
 */
public class Example implements Comparable<Example> {

    /** Lista dei valori che compongono l'esempio. */
    private List<Object> example = new ArrayList<Object>();

    /**
     * Aggiunge un valore all'esempio.
     *
     * @param o valore da aggiungere
     */
    public void add(Object o) {
        example.add(o);
    }

    /**
     * Restituisce il valore nella posizione specificata.
     *
     * @param i indice del valore
     * @return  valore in posizione i
     */
    public Object get(int i) {
        return example.get(i);
    }

    /**
     * Confronta questo esempio con un altro per determinarne l'ordinamento.
     * Il confronto avviene elemento per elemento; il primo valore diverso determina il risultato. Restituisce 0 se gli esempi sono uguali.
     *
     * @param ex esempio con cui effettuare il confronto
     * @return   valore negativo, zero o positivo se questo esempio è minore, uguale o maggiore di ex
     */
    public int compareTo(Example ex) {
        int i = 0;
        for (Object o : ex.example) {
            Object thisObj = this.example.get(i);
            if (!o.equals(thisObj)) {
                if (o instanceof Comparable && thisObj instanceof Comparable) {
                    try {
                        Comparable<Object> comp1 = (Comparable<Object>) o;
                        Comparable<Object> comp2 = (Comparable<Object>) thisObj;
                        return comp1.compareTo(comp2);
                    } catch (ClassCastException e) {
                        return o.hashCode() - thisObj.hashCode();
                    }
                } else {
                    return o.hashCode() - thisObj.hashCode();
                }
            }
            i++;
        }
        return 0;
    }

    /**
     * Restituisce una rappresentazione testuale dell'esempio.
     *
     * @return stringa con i valori dell'esempio
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Object o : example) {
            str.append(o.toString()).append(" ");
        }
        return str.toString();
    }
}