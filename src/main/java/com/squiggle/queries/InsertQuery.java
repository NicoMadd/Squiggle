package com.squiggle.queries;

import java.util.*;
import com.squiggle.base.*;
import com.squiggle.output.*;
import com.squiggle.types.DateType;
import com.squiggle.types.DoubleType;
import com.squiggle.types.FloatType;
import com.squiggle.types.IntegerType;
import com.squiggle.types.NullType;
import com.squiggle.types.StringType;

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
        row.addValue(new StringType(str));
        updateRowStatus();
        return this;

    }

    public InsertQuery value(Integer integer) {
        row.addValue(new IntegerType(integer));
        updateRowStatus();
        return this;
    }

    public InsertQuery value(Date date) {
        row.addValue(new DateType(date));
        updateRowStatus();
        return this;
    }

    public InsertQuery value(Double dbl) {
        row.addValue(new DoubleType(dbl));
        updateRowStatus();
        return this;
    }

    public InsertQuery value(Float flt) {
        row.addValue(new FloatType(flt));
        updateRowStatus();
        return this;
    }

    public InsertQuery endRow() {
        // Fill row with nulls if not enough values were added
        while (row.getValuesCount() < columns.size()) {
            row.addValue(new NullType());
        }
        updateRowStatus();
        return this;
    }

    private void updateRowStatus() {
        // if row is full, add it to the list of rows
        if (row.getValuesCount() == columns.size()) {
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

        if (this.getLastRow().getValuesCount() > 0)
            this.endRow();

        this.parser.insertQuery(this, out);
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
