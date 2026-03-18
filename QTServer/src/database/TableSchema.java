package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Rappresenta lo schema di una tabella del database.
 * Legge i metadati della tabella e li mappa in una lista di colonne con il rispettivo tipo Java.
 */
public class TableSchema {

    /** Oggetto per l'accesso al database. */
    DbAccess db;

    /**
     * Rappresenta una colonna della tabella con nome e tipo.
     */
    public class Column {

        /** Nome della colonna. */
        private String name;

        /** Tipo della colonna (string o number). */
        private String type;

        /**
         * Costruisce una colonna con il nome e il tipo specificati.
         *
         * @param name nome della colonna
         * @param type tipo della colonna
         */
        Column(String name, String type) {
            this.name = name;
            this.type = type;
        }

        /**
         * Restituisce il nome della colonna.
         *
         * @return nome della colonna
         */
        public String getColumnName() {
            return name;
        }

        /**
         * Indica se la colonna è di tipo numerico.
         *
         * @return true se la colonna è di tipo number, false altrimenti
         */
        public boolean isNumber() {
            return type.equals("number");
        }

        /**
         * Restituisce una rappresentazione testuale della colonna.
         *
         * @return stringa nel formato nome:tipo
         */
        public String toString() {
            return name + ":" + type;
        }
    }

    /** Lista delle colonne che compongono lo schema della tabella. */
    List<Column> tableSchema = new ArrayList<Column>();

    /**
     * Costruisce lo schema della tabella leggendo i metadati dal database.
     * I tipi SQL vengono mappati nei tipi Java "string" o "number".
     *
     * @param db        oggetto DbAccess per l'accesso al database
     * @param tableName nome della tabella di cui leggere lo schema
     * @throws SQLException se si verifica un errore nella lettura dei metadati
     */
    public TableSchema(DbAccess db, String tableName) throws SQLException {
        this.db = db;
        HashMap<String, String> mapSQL_JAVATypes = new HashMap<String, String>();
        mapSQL_JAVATypes.put("CHAR", "string");
        mapSQL_JAVATypes.put("VARCHAR", "string");
        mapSQL_JAVATypes.put("LONGVARCHAR", "string");
        mapSQL_JAVATypes.put("BIT", "string");
        mapSQL_JAVATypes.put("SHORT", "number");
        mapSQL_JAVATypes.put("INT", "number");
        mapSQL_JAVATypes.put("LONG", "number");
        mapSQL_JAVATypes.put("FLOAT", "number");
        mapSQL_JAVATypes.put("DOUBLE", "number");

        Connection con = db.getConnection();
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getColumns(null, null, tableName, null);

        while (res.next()) {
            if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
                tableSchema.add(new Column(
                        res.getString("COLUMN_NAME"),
                        mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
                );
        }
        res.close();
    }

    /**
     * Restituisce il numero di attributi dello schema.
     *
     * @return numero di colonne della tabella
     */
    public int getNumberOfAttributes() {
        return tableSchema.size();
    }

    /**
     * Restituisce la colonna nella posizione specificata.
     *
     * @param index indice della colonna
     * @return      colonna corrispondente all'indice fornito
     */
    public Column getColumn(int index) {
        return tableSchema.get(index);
    }
}
