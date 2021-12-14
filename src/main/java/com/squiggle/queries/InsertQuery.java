package com.squiggle.queries;

import java.util.*;
import java.util.function.Function;

import com.squiggle.base.*;
import com.squiggle.output.*;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author nmadeo - Nicolas Madeo
 */
public class InsertQuery extends Query {

    List<Row> rows;
    Row row;

    public InsertQuery() {
        super();
        rows = new LinkedList<Row>();
        row = new Row();
    }

    public InsertQuery into(Table table) {
        this.baseTable = table;
        return this;
    }

    public InsertQuery into(String tableName, String alias) {
        this.baseTable = new Table(tableName, alias);
        return this;
    }

    public InsertQuery into(String tableName) {
        this.baseTable = new Table(tableName);
        return this;
    }

    public InsertQuery to(Column column) {
        columns.add(column);
        return this;
    }

    public InsertQuery to(String columnName) {
        if (this.baseTable == null) {
            throw new IllegalStateException("Cannot add column without table");
        }
        return this.to(this.baseTable.getColumn(columnName));
    }

    /**
     * Syntax sugar for column(Column).
     */
    public InsertQuery to(Table table, String columname) {
        return this.to(table.getColumn(columname));
    }

    // public InsertQuery removeColumn(Column column) {
    // columns.remove(column);
    // return this;
    // }

    public InsertQuery value(String str) {
        row.addValue(str);
        updateRowStatus();
        return this;

    }

    public InsertQuery value(Integer integer) {
        row.addValue(integer);
        updateRowStatus();
        return this;
    }

    public InsertQuery value(Date date) {
        row.addValue(date);
        updateRowStatus();
        return this;
    }

    public InsertQuery value(Double dbl) {
        row.addValue(dbl);
        updateRowStatus();
        return this;
    }

    public InsertQuery value(Float flt) {
        row.addValue(flt);
        updateRowStatus();
        return this;
    }

    public InsertQuery endRow() {
        if (row.getValuesCount() < columns.size())
            for (int i = 0; i < columns.size() - row.getValuesCount(); i++) {
                row.addValue("NULL");
            }
        updateRowStatus();
        return this;
    }

    private void updateRowStatus() {
        System.out.println(row.getValuesCount());
        System.out.println(columns.size());
        if (row.getValuesCount() == columns.size()) {
            System.out.println("adds row");
            rows.add(row);
            row = new Row();
        }
    }

    public List<Row> getRows() {
        return rows;
    }

    public Row getLastRow() {
        return row;
    }

    public void write(Output out) {
        this.parser.insertQuery(this, out);
    }

    /**
     * Iterate through a Collection and append all entries (using .toString()) to a
     * StringBuffer.
     */
    private void appendList(Output out, Collection collection, String separator) {
        Iterator i = collection.iterator();
        boolean hasNext = i.hasNext();

        while (hasNext) {
            Outputable curr = (Outputable) i.next();
            hasNext = i.hasNext();
            curr.write(out);
            out.print(' ');
            if (hasNext) {
                out.print(separator);
            }
        }
    }

    /**
     * Find all the tables used in the query (from columns).
     *
     * @return List of {@link com.squiggle.Squiggle.Table}s
     */

    @Override
    public List<Table> getUsedTables() {
        LinkedHashSet<Table> allTables = new LinkedHashSet<>();
        allTables.add(this.getBaseTable());

        for (Column column : this.listColumns()) {
            allTables.add(column.getTable());
        }

        // Get all tables used by criteria TODO capaz convendria separar los criteria.
        // una collection para Joins y una para Matchs. Esto es bueno? si hay mas tipos
        // que onda?
        // cambio a comprobar la clase. :( no se si es mejor hacer un metodo por cada
        // tipo

        for (Criteria criteria : this.listCriteria()) {
            if (criteria instanceof JoinCriteria) {
                JoinCriteria joinCriteria = (JoinCriteria) criteria;
                allTables.add(joinCriteria.getSource().getTable());
                allTables.add(joinCriteria.getDest().getTable());
            }
        }

        return new LinkedList<>(allTables);
    }

}
