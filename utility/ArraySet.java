package utility;

import java.util.Arrays;

/**
 * Rappresenta un insieme di interi non negativi implementato tramite array di booleani.
 * Supporta le operazioni di aggiunta, rimozione e verifica degli elementi.
 */
public class ArraySet {

    /** Array booleano che indica la presenza di ciascun elemento nell'insieme. */
    private boolean set[];

    /** Indice massimo occupato + 1. */
    private int size = 0;

    /** Numero di elementi presenti nell'insieme. */
    private int cardinality = 0;

    /**
     * Costruisce un insieme vuoto con capacità iniziale di 50 elementi.
     */
    public ArraySet() {
        set = new boolean[50];
        for (int i = 0; i < set.length; i++)
            set[i] = false;
    }

    /**
     * Aggiunge un elemento all'insieme.
     * Se l'indice supera la capacità attuale, l'array viene espanso. Restituisce true se l'insieme è stato modificato.
     *
     * @param i indice dell'elemento da aggiungere
     * @return  true se l'elemento non era già presente nell'insieme
     */
    public boolean add(int i) {
        if (i >= set.length) {
            boolean temp[] = new boolean[set.length * 2];
            Arrays.fill(temp, false);
            System.arraycopy(set, 0, temp, 0, set.length);
            set = temp;
        }
        boolean added = set[i];
        set[i] = true;
        if (i >= size)
            size = i + 1;
        if (!added)
            cardinality++;
        return !added;
    }

    /**
     * Rimuove un elemento dall'insieme. Restituisce true se l'elemento era presente ed è stato rimosso.
     *
     * @param i indice dell'elemento da rimuovere
     * @return  true se l'elemento era presente nell'insieme
     */
    public boolean delete(int i) {
        if (i < size) {
            boolean deleted = set[i];
            set[i] = false;
            if (i == size - 1) {
                int j;
                for (j = size - 1; j >= 0 && !set[j]; j--);
                size = j + 1;
            }
            if (deleted)
                cardinality--;
            return deleted;
        }
        return false;
    }

    /**
     * Verifica se un elemento è presente nell'insieme.
     *
     * @param i indice dell'elemento da verificare
     * @return  true se l'elemento è presente nell'insieme
     */
    public boolean get(int i) {
        return set[i];
    }

    /**
     * Restituisce il numero di elementi presenti nell'insieme.
     *
     * @return cardinalità dell'insieme
     */
    public int size() {
        return cardinality;
    }

    /**
     * Restituisce un array contenente gli indici degli elementi presenti nell'insieme.
     *
     * @return array degli indici degli elementi presenti
     */
    public int[] toArray() {
        int a[] = new int[0];
        for (int i = 0; i < size; i++) {
            if (get(i)) {
                int temp[] = new int[a.length + 1];
                System.arraycopy(a, 0, temp, 0, a.length);
                a = temp;
                a[a.length - 1] = i;
            }
        }
        return a;
    }
}