package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.TableSchema.Column;

/**
 * Fornisce i metodi per interrogare una tabella del database e recuperarne i dati sotto forma di esempi e valori distinti.
 */
public class TableData {

    /** Oggetto per l'accesso al database. */
    DbAccess db;

    /**
     * Costruisce un oggetto TableData associato alla connessione specificata.
     *
     * @param db oggetto DbAccess per l'accesso al database
     */
    public TableData(DbAccess db) {
        this.db = db;
    }

    /**
     * Restituisce la lista delle transazioni distinte presenti nella tabella specificata.
     * Ogni transazione è rappresentata come un oggetto Example.
     *
     * @param table nome della tabella da interrogare
     * @return      lista di esempi distinti
     * @throws SQLException      se si verifica un errore nella query SQL
     * @throws EmptySetException se la tabella non contiene dati
     */
    public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException {
        LinkedList<Example> transSet = new LinkedList<Example>();
        Statement statement;
        TableSchema tSchema = new TableSchema(db, table);

        String query = "select distinct ";

        for (int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
            Column c = tSchema.getColumn(i);
            if (i > 0)
                query += ",";
            query += c.getColumnName();
        }
        if (tSchema.getNumberOfAttributes() == 0)
            throw new SQLException();
        query += (" FROM " + table);

        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        boolean empty = true;
        while (rs.next()) {
            empty = false;
            Example currentTuple = new Example();
            for (int i = 0; i < tSchema.getNumberOfAttributes(); i++)
                if (tSchema.getColumn(i).isNumber())
                    currentTuple.add(rs.getDouble(i + 1));
                else
                    currentTuple.add(rs.getString(i + 1));
            transSet.add(currentTuple);
        }
        rs.close();
        statement.close();
        if (empty) throw new EmptySetException();

        return transSet;
    }

    /**
     * Restituisce l'insieme dei valori distinti presenti in una colonna della tabella, ordinati in modo crescente.
     *
     * @param table  nome della tabella da interrogare
     * @param column colonna di cui recuperare i valori distinti
     * @return       insieme ordinato dei valori distinti della colonna
     * @throws SQLException se si verifica un errore nella query SQL
     */
    public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
        Set<Object> valueSet = new TreeSet<Object>();
        Statement statement;
        TableSchema tSchema = new TableSchema(db, table);

        String query = "select distinct ";
        query += column.getColumnName();
        query += (" FROM " + table);
        query += (" ORDER BY " + column.getColumnName());

        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            if (column.isNumber())
                valueSet.add(rs.getDouble(1));
            else
                valueSet.add(rs.getString(1));
        }
        rs.close();
        statement.close();

        return valueSet;
    }

    /**
     * Restituisce il valore aggregato (minimo o massimo) di una colonna della tabella.
     *
     * @param table     nome della tabella da interrogare
     * @param column    colonna su cui calcolare il valore aggregato
     * @param aggregate tipo di aggregazione da applicare (MIN o MAX)
     * @return          valore minimo o massimo della colonna
     * @throws SQLException    se si verifica un errore nella query SQL
     * @throws NoValueException se non è disponibile alcun valore aggregato per la colonna
     */
    public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate)
            throws SQLException, NoValueException {
        Statement statement;
        TableSchema tSchema = new TableSchema(db, table);
        Object value = null;
        String aggregateOp = "";

        String query = "select ";
        if (aggregate == QUERY_TYPE.MAX)
            aggregateOp += "max";
        else
            aggregateOp += "min";
        query += aggregateOp + "(" + column.getColumnName() + ") FROM " + table;

        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            if (column.isNumber())
                value = rs.getFloat(1);
            else
                value = rs.getString(1);
        }
        rs.close();
        statement.close();
        if (value == null)
            throw new NoValueException("No " + aggregateOp + " on " + column.getColumnName());

        return value;
    }
}