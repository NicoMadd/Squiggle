package com.squiggle.parsers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.squiggle.base.Column;
import com.squiggle.base.ColumnDef;
import com.squiggle.base.JoinCriteria;
import com.squiggle.base.Row;
import com.squiggle.base.Table;
import com.squiggle.constraints.ForeignKey;
import com.squiggle.constraints.NotNullable;
import com.squiggle.constraints.Nullable;
import com.squiggle.constraints.PrimaryKey;
import com.squiggle.constraints.Unique;
import com.squiggle.output.Output;
import com.squiggle.output.Outputable;
import com.squiggle.queries.CreateTableQuery;
import com.squiggle.queries.DeleteQuery;
import com.squiggle.queries.InsertQuery;
import com.squiggle.queries.SelectQuery;
import com.squiggle.queries.UpdateQuery;
import com.squiggle.types.values.TypeValue;
import com.squiggle.utils.TriConsumer;

public class SqlServerParser extends Parser {

    public SqlServerParser() {
    }

    /**
     * Iterate through a Set and execute a function on each
     * entry.
     */
    private void iterateEntryCollection(Output out, Collection<Entry<? extends Outputable, ? extends Outputable>> set,
            TriConsumer<Output, Entry<? extends Outputable, ? extends Outputable>, Boolean> consumer) {
        for (Iterator<Entry<? extends Outputable, ? extends Outputable>> i = set.iterator(); i.hasNext();)
            consumer.accept(out, i.next(), i.hasNext());
    }

    /**
     * Iterate through an Outputable Collection and execute a function on each
     * entry.
     */
    private void iterateOutputableCollection(Output out, Collection<? extends Outputable> outputables,
            TriConsumer<Output, ? super Outputable, Boolean> consumer) {
        for (Iterator<? extends Outputable> i = outputables.iterator(); i.hasNext();)
            consumer.accept(out, i.next(), i.hasNext());
    }

    /**
     * Iterate through a Collection and append all entries (using .toString()) to a
     * StringBuffer.
     */
    private void appendList(Output out, Collection<? extends Outputable> outputables, String separator) {
        this.iterateOutputableCollection(out, outputables, (output, current, hasNext) -> {
            current.write(out);
            if (hasNext) {
                out.print(separator);
                out.space();
            }
        });
    }

    @Override
    public void selectQuery(SelectQuery selectQuery, Output out) {

        out.print("SELECT");
        if (selectQuery.isDistinct()) {
            out.print(" DISTINCT");
        }

        // Add columns to select
        out.space();
        appendList(out, selectQuery.listColumns(), ",");

        // Add tables to select from
        out.space();
        out.print("FROM");

        // Determine all tables used in query
        out.space();
        addFroms(out, selectQuery.getUsedTables(), selectQuery.getJoins());

        // Add criteria
        if (selectQuery.listCriteria().size() > 0) {
            out.space();
            out.print("WHERE");
            out.space();

            appendList(out, selectQuery.listCriteria(), "AND");

        }

        // Add order
        if (selectQuery.listOrder().size() > 0) {
            out.space();
            out.print("ORDER BY");

            appendList(out, selectQuery.listOrder(), ",");

        }

    }

    private void addFroms(Output out, List<Table> usedTables, List<JoinCriteria> joins) {

        iterateOutputableCollection(out, joins, (output, currentJoin, hasNext) -> {
            out.print(currentJoin);
            JoinCriteria join = (JoinCriteria) currentJoin;
            // if table in usedTables, remove it
            usedTables.remove(join.getSource().getTable());
            usedTables.remove(join.getDest().getTable());

        });

        // iterate through all tables used in query and add them to the FROM clause and
        // dont add , if its the last one

        iterateOutputableCollection(out, usedTables, (output, currentTable, hasNext) -> {
            currentTable.write(out);
            if (hasNext) {
                out.print(",");
                out.space();
            }
        });

    }

    /**
     * Iterate through a Collection and append all entries (using .toString()) to a
     * StringBuffer.
     */
    private void appendObjectList(Output out, List<TypeValue> typeList, String separator) {

        Integer length = typeList.size();
        for (int i = 0; i < length; i++) {
            typeList.get(i).write(out);
            if (i < length - 1) {
                out.print(separator);
                out.print(' ');
            }
        }
    }

    @Override
    public void insertQuery(InsertQuery insertQuery, Output out) {

        out.print("INSERT INTO");

        // Add into table
        out.space();
        out.print(insertQuery.getBaseTable());

        // add column names
        out.space();
        out.print("(");
        List<Column> insertColumns = insertQuery.listColumns();
        insertColumns.forEach(col -> col.writeWithTable(false));
        appendList(out, insertColumns, ",");
        out.print(")");
        out.space();

        // add values

        out.print("VALUES ");
        List<Row> rows = insertQuery.getRows();
        Integer length = rows.size();
        Integer i = 0;

        for (Row row : rows) {
            out.print("(");
            appendObjectList(out, row.getValues(), ",");
            out.print(")");
            if (length != ++i)
                out.print(", ");
        }

        // Add criteria
        if (insertQuery.listCriteria().size() > 0) {
            out.print("WHERE");

            appendList(out, insertQuery.listCriteria(), "AND");

        }

    }

    @Override
    public void updateQuery(UpdateQuery updateQuery, Output out) {
        out.print("UPDATE");
        out.space();
        out.print(updateQuery.getBaseTable());
        out.space();
        out.print("SET");
        out.space();

        iterateEntryCollection(out, updateQuery.getEntries(), (o, entry, hasNext) -> {
            o.print(entry.getKey());
            o.print("=");
            entry.getValue().write(o);
            if (hasNext) {
                o.print(",");
                o.space();
            }
        });

        // Add criteria
        if (updateQuery.listCriteria().size() > 0) {
            out.space();
            out.print("WHERE");
            out.space();

            appendList(out, updateQuery.listCriteria(), "AND");

        }
    }

    @Override
    public void deleteQuery(Output out, DeleteQuery deleteQuery) {
        out.print("DELETE FROM");
        out.space();
        out.print(deleteQuery.getBaseTable());
        out.space();

        // Add criteria

        if (deleteQuery.listCriteria().size() > 0) {
            out.print("WHERE");
            out.space();
            appendList(out, deleteQuery.listCriteria(), " AND");
        }

    }

    @Override
    public void createTableQuery(Output out, CreateTableQuery createTable) {
        out.print("CREATE TABLE");
        out.space();
        out.print(createTable.getTable().getName());
        out.space();

        // Add columns definitions

        List<ColumnDef> colsDefs = createTable.getColumnsDefs();
        List<ColumnDef> primaryKeys = new LinkedList<>();

        if (colsDefs.stream().filter(col -> col.isPrimaryKey()).count() > 1) {
            for (ColumnDef colDef : colsDefs) {
                if (colDef.isPrimaryKey()) {
                    primaryKeys.add(colDef);
                }
            }
        }

        out.print("(");

        /*
         * if any filtered column is primary key it filters it before and prints it now
         * removing the primaryKey constraint, in order to print it at the end of the
         * table definition
         * 
         */

        if (primaryKeys.size() > 0) {

            iterateOutputableCollection(out, colsDefs, (output, current, hasNext) -> {
                ((ColumnDef) current).write(output, Arrays.asList(new Class[] { PrimaryKey.class }));
                if (hasNext) {
                    out.print(",");
                    out.space();
                }
            });
        } else {
            appendList(out, colsDefs, ",");
        }

        /*
         * prints primary key constraint
         */

        if (!primaryKeys.isEmpty()) {
            out.print(",");
            out.space();
            out.print("PRIMARY KEY");
            out.space();
            out.print("(");

            Iterator<ColumnDef> pks = primaryKeys.iterator();
            while (pks.hasNext()) {
                out.print(pks.next().getName());
                if (pks.hasNext()) {
                    out.print(",");
                    out.space();
                }
            }

            out.print(")");

        }

        out.print(")");

    }

    @Override
    public void foreignKey(Output out, ForeignKey foreignKey) {
        out.print("FOREIGN KEY REFERENCES");
        out.space();
        out.print(foreignKey.getTableName());
        out.print("(");
        out.print(foreignKey.getForeignColumnName());
        out.print(")");

    }

    @Override
    public void notNullable(Output out, NotNullable notNullable) {
        out.print("NOT NULL");
    }

    @Override
    public void nullable(Output out, Nullable nullable) {
        out.print("NULL");
    }

    @Override
    public void primaryKey(Output out, PrimaryKey primaryKey) {
        out.print("PRIMARY KEY");

    }

    @Override
    public void unique(Output out, Unique unique) {
        out.print("UNIQUE");

    }

}