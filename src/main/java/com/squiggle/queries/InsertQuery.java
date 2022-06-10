package com.squiggle.queries;

import java.util.*;
import com.squiggle.base.*;
import com.squiggle.base.Joins.JoinCriteria;
import com.squiggle.exceptions.NoColumnsException;
import com.squiggle.exceptions.NoTableException;
import com.squiggle.exceptions.NoValuesInsertedException;
import com.squiggle.output.*;
import com.squiggle.types.values.BooleanTypeValue;
import com.squiggle.types.values.DateTypeValue;
import com.squiggle.types.values.DoubleTypeValue;
import com.squiggle.types.values.FloatTypeValue;
import com.squiggle.types.values.IntegerTypeValue;
import com.squiggle.types.values.NullTypeValue;
import com.squiggle.types.values.StringTypeValue;
import com.squiggle.types.values.TypeValue;

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

    public InsertQuery value(String str) {
        TypeValue value = str == null ? new NullTypeValue() : new StringTypeValue(str);
        row.addValue(value);
        updateRowStatus();
        return this;

    }

    public InsertQuery value(Integer integer) {
        TypeValue value = integer == null ? new NullTypeValue() : new IntegerTypeValue(integer);
        row.addValue(value);
        updateRowStatus();
        return this;
    }

    public InsertQuery value(Date date) {
        TypeValue value = date == null ? new NullTypeValue() : new DateTypeValue(date);
        row.addValue(value);
        updateRowStatus();
        return this;
    }

    public InsertQuery value(Double dbl) {
        TypeValue value = dbl == null ? new NullTypeValue() : new DoubleTypeValue(dbl);
        row.addValue(value);
        updateRowStatus();
        return this;
    }

    public InsertQuery value(Float flt) {
        TypeValue value = flt == null ? new NullTypeValue() : new FloatTypeValue(flt);
        row.addValue(value);
        updateRowStatus();
        return this;
    }

    public InsertQuery value(Boolean bool) {
        TypeValue value = bool == null ? new NullTypeValue() : new BooleanTypeValue(bool);
        row.addValue(value);
        updateRowStatus();
        return this;
    }

    public InsertQuery endRow() {
        // Fill row with nulls if not enough values were added
        while (row.getValuesCount() < columns.size()) {
            row.addValue(new NullTypeValue());
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

    public void validate() {
        if (this.baseTable == null) {
            throw new NoTableException("Cannot make query without table");
        }

        if (this.getRows().size() == 0) {
            throw new NoValuesInsertedException("Cannot make query without values");
        }

        if (this.getLastRow().getValuesCount() > 0)
            this.endRow();
    }

    @Override
    protected void validateMain() {
        super.validateMain();
        if (this.columns.size() == 0) {
            throw new NoColumnsException("Cannot make query without related column");
        }
    }

    public void write(Output out) {
        validate();
        this.parser.insertQuery(this, out);
    }

}
