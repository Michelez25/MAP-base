package data;

import database.DatabaseConnectionException;
import database.Example;
import database.DbAccess;
import database.TableData;
import database.EmptySetException;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 * Rappresenta il dataset caricato dal database.
 * Contiene la lista degli esempi e il relativo insieme di attributi.
 */
public class Data {
    private List<Example> data;
    private int numberOfExamples;
    private List<Attribute> attributeSet;

    /**
     * Costruisce il dataset caricando le transazioni distinte dalla tabella specificata.
     *
     * @param tableName nome della tabella del database da cui caricare i dati
     * @throws DatabaseConnectionException se non è possibile connettersi al database
     * @throws SQLException                se si verifica un errore nella query SQL
     * @throws EmptySetException           se la tabella è vuota o non contiene dati validi
     */
    public Data(String tableName) throws DatabaseConnectionException, SQLException, EmptySetException {
        DbAccess db = new DbAccess();
        try {
            db.initConnection();
            TableData tableData = new TableData(db);

            // Carica i dati dalla tabella
            this.data = tableData.getDistinctTransazioni(tableName);
            this.numberOfExamples = data.size();

            // Inizializza attributeSet basandosi sui dati del database
            initializeAttributesFromData(tableData, tableName);

        } finally {
            db.closeConnection();
        }
    }

    /**
     * Inizializza l'insieme degli attributi del dataset.
     * Gli attributi vengono definiti manualmente in base alla struttura attesa della tabella.
     *
     * @param tableData oggetto per l'accesso ai dati della tabella
     * @param tableName nome della tabella del database
     * @throws SQLException      se si verifica un errore SQL durante l'inizializzazione
     * @throws EmptySetException se il risultato della query è vuoto
     */
    private void initializeAttributesFromData(TableData tableData, String tableName)
            throws SQLException, EmptySetException {
        attributeSet = new ArrayList<Attribute>();

        String[] outlookValues = {"overcast", "rain", "sunny"};
        attributeSet.add(new DiscreteAttribute("Outlook", 0, outlookValues));

        attributeSet.add(new ContinuousAttribute("Temperature", 1, 0.0, 40.0));

        String[] humidityValues = {"high", "normal"};
        attributeSet.add(new DiscreteAttribute("Humidity", 2, humidityValues));

        String[] windValues = {"strong", "weak"};
        attributeSet.add(new DiscreteAttribute("Wind", 3, windValues));

        String[] playTennisValues = {"no", "yes"};
        attributeSet.add(new DiscreteAttribute("PlayTennis", 4, playTennisValues));
    }

    /**
     * Restituisce il numero di esempi nel dataset.
     *
     * @return numero di esempi
     */
    public int getNumberOfExamples() {
        return numberOfExamples;
    }

    /**
     * Restituisce il numero di attributi nel dataset.
     *
     * @return numero di attributi
     */
    public int getNumberOfAttributes() {
        return attributeSet.size();
    }

    /**
     * Restituisce il valore di un attributo per un determinato esempio.
     *
     * @param exampleIndex   indice dell'esempio
     * @param attributeIndex indice dell'attributo
     * @return               valore dell'attributo per l'esempio specificato
     */
    public Object getAttributeValue(int exampleIndex, int attributeIndex) {
        return data.get(exampleIndex).get(attributeIndex);
    }

    /**
     * Restituisce l'attributo in posizione specificata.
     *
     * @param index indice dell'attributo nell'insieme degli attributi
     * @return      attributo corrispondente all'indice fornito
     */
    public Attribute getAttribute(int index) {
        return attributeSet.get(index);
    }

    /**
     * Restituisce la tupla corrispondente all'esempio nella posizione indicata.
     * Il metodo converte ogni valore dell'esempio in un oggetto specifico (DiscreteItem o ContinuousItem)
     * basandosi sulla natura dell'attributo (discreto o continuo).
     *
     * @param index indice dell'esempio nel dataset
     * @return      tupla con gli item costruiti a partire dai valori dell'esempio
     */
    public Tuple getItemSet(int index) {
        Tuple tuple = new Tuple(attributeSet.size());
        for (int i = 0; i < attributeSet.size(); i++) {
            Attribute attr = attributeSet.get(i);
            Object value = data.get(index).get(i);

            if (attr instanceof DiscreteAttribute) {
                tuple.add(new DiscreteItem((DiscreteAttribute) attr, (String) value), i);
            } else if (attr instanceof ContinuousAttribute) {
                tuple.add(new ContinuousItem((ContinuousAttribute) attr, (Double) value), i);
            }
        }
        return tuple;
    }

    /**
     * Restituisce una rappresentazione testuale del dataset.
     * Ogni riga contiene l'indice dell'esempio seguito dai valori degli attributi.
     *
     * @return stringa con tutti gli esempi del dataset
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numberOfExamples; i++) {
            result.append(i + ":");
            for (int j = 0; j < attributeSet.size(); j++) {
                result.append(data.get(i).get(j));
                if (j < attributeSet.size() - 1) {
                    result.append(",");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }
}