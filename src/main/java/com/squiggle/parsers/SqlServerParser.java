package com.squiggle.parsers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.squiggle.base.AggregatedColumn;
import com.squiggle.base.Column;
import com.squiggle.base.ColumnDef;
import com.squiggle.base.Row;
import com.squiggle.base.Table;
import com.squiggle.base.Joins.JoinCondition;
import com.squiggle.base.Joins.JoinCriteria;
import com.squiggle.base.Transactables.Commit;
import com.squiggle.base.Transactables.Rollback;
import com.squiggle.base.Transactables.Transactable;
import com.squiggle.constraints.AutoIncrement;
import com.squiggle.constraints.DefaultValue;
import com.squiggle.constraints.ForeignKey;
import com.squiggle.constraints.NotNullable;
import com.squiggle.constraints.Nullable;
import com.squiggle.constraints.PrimaryKey;
import com.squiggle.constraints.Unique;
import com.squiggle.output.Output;
import com.squiggle.output.Outputable;
import com.squiggle.queries.CreateDatabaseQuery;
import com.squiggle.queries.DeleteQuery;
import com.squiggle.queries.DropDatabaseQuery;
import com.squiggle.queries.InsertQuery;
import com.squiggle.queries.SelectQuery;
import com.squiggle.queries.TransactionQuery;
import com.squiggle.queries.UpdateQuery;
import com.squiggle.queries.TableQueries.CreateTableQuery;
import com.squiggle.queries.TableQueries.DropTableQuery;
import com.squiggle.types.values.TypeValue;
import com.squiggle.utils.TriConsumer;

public class SqlServerParser extends Parser {

    public SqlServerParser() {
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

            appendList(out, selectQuery.listCriteria(), " AND");
        }

        // Add group by

        if (selectQuery.listGroupBys().size() > 0) {
            out.space();
            out.print("GROUP BY");
            out.space();

            appendList(out, selectQuery.listGroupBys(), ",");
        }

        // Add order
        if (selectQuery.listOrder().size() > 0) {
            out.space();
            out.print("ORDER BY");

            appendList(out, selectQuery.listOrder(), ",");

        }

    }

    private void addFroms(Output out, List<Table> usedTables, List<JoinCriteria> joins) {

        // Add the first table
        Table firstTable = usedTables.get(0);
        firstTable.write(out);

        usedTables.remove(0);

        iterateParserableCollection(out, joins, (output, currentJoin, hasNext) -> {
            out.space();
            currentJoin.write(out);
            JoinCriteria join = (JoinCriteria) currentJoin;
            // if table in usedTables, remove it

            usedTables.remove(join.getSourceTable());
            usedTables.remove(join.getDestTable());
            // out.space();
        });

        // iterate through all tables used in query and add them to the FROM clause and
        // dont add , if its the last one

        iterateParserableCollection(out, usedTables, (output, currentTable, hasNext) -> {
            out.print(",");
            out.space();
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

        Iterator<Row> rowsIterator = rows.iterator();

        while (rowsIterator.hasNext()) {
            Row row = rowsIterator.next();
            out.print("(");
            appendObjectList(out, row.getValues(), ",");
            out.print(")");
            if (rowsIterator.hasNext())
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

            iterateParserableCollection(out, colsDefs, (output, current, hasNext) -> {
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

    @Override
    public void defaultValue(Output out, DefaultValue defaultValue) {
        out.print("DEFAULT");
        out.space();
        defaultValue.getValue().write(out);

    }

    @Override
    public void createDatabase(Output out, CreateDatabaseQuery createDatabaseQuery) {
        out.print("CREATE DATABASE");
        out.space();
        out.print(createDatabaseQuery.getDatabaseName());

    }

    @Override
    public void dropDatabase(Output out, DropDatabaseQuery dropDatabaseQuery) {
        out.print("DROP DATABASE");
        out.space();
        out.print(dropDatabaseQuery.getDatabaseName());

    }

    @Override
    public void autoIncrement(Output out, AutoIncrement autoIncrement) {
        out.print("IDENTITY");
        out.print("(");
        out.print(autoIncrement.getStart());
        out.print(",");
        out.print(autoIncrement.getIncrement());
        out.print(")");

    }

    @Override
    public void dropTableQuery(Output out, DropTableQuery dropTableQuery) {
        out.print("DROP TABLE");
        out.space();
        out.print(dropTableQuery.getTable().getName());

    }

    @Override
    public void simpleColumn(Output out, Column column) {

        String name = column.getName();
        if (column.getWriteWithTable())
            out.print(column.getTable().getAlias()).print('.');

        if (name.contains(" ")) {
            out.print("\"");
            out.print(name);
            out.print("\"");
        } else {
            out.print(name);
        }
        columnAlias(out, column);
    }

    @Override
    public void columnAlias(Output out, Column column) {
        if (column.getAlias() != null) {
            out.print(" AS ");
            out.print(column.getAlias());
        }
    }

    @Override
    public void sum(Output out, AggregatedColumn aggregatedColumn) {

        // System.out.println(aggregatedColumn.getAlias());
        out.print("SUM");
        out.print("(");
        if (aggregatedColumn.getWriteWithTable())
            out.print(aggregatedColumn.getTable().getAlias()).print('.');
        out.print(aggregatedColumn.getName());
        out.print(")");
        columnAlias(out, aggregatedColumn);

    }

    @Override
    public void average(Output out, AggregatedColumn aggregatedColumn) {
        out.print("AVG");
        out.print("(");
        if (aggregatedColumn.getWriteWithTable())
            out.print(aggregatedColumn.getTable().getAlias()).print('.');
        out.print(aggregatedColumn.getName());
        out.print(")");
        columnAlias(out, aggregatedColumn);

    }

    @Override
    public void count(Output out, AggregatedColumn aggregatedColumn) {
        out.print("COUNT");
        out.print("(");
        if (aggregatedColumn.getWriteWithTable())
            out.print(aggregatedColumn.getTable().getAlias()).print('.');
        out.print(aggregatedColumn.getName());
        out.print(")");
        columnAlias(out, aggregatedColumn);

    }

    @Override
    public void aggregatedColumn(Output out, AggregatedColumn aggregatedColumn) {
        aggregatedColumn.getFunction().write(out, aggregatedColumn);

    }

    @Override
    public void commit(Output out, Commit commit) {
        out.print("COMMIT TRANSACTION");
        out.space();

    }

    @Override
    public void rollback(Output out, Rollback rollback) {
        out.print("ROLLBACK TRANSACTION");
        out.space();

    }

    @Override
    public void transaction(Output out, TransactionQuery transactionQuery) {
        out.print("BEGIN TRANSACTION");
        out.space();

        // print all transactables
        /*
         * Idea is to print all transactables in the same order as they were added to
         * the transaction
         * example
         * BEGIN TRANSACTION
         * INSERT INTO table1 (col1, col2) VALUES (1, 2)
         * INSERT INTO table2 (col1, col2) VALUES (1, 2)
         * COMMIT TRANSACTION
         * 
         */
        List<Transactable> tsx = transactionQuery.getTransactables();
        for (Transactable t : tsx) {
            t.write(out);
            out.space();
        }

    }

    @Override
    public void table(Output out, Table table) {

        String name = table.getName();

        if (name.contains(" ")) {
            out.print("\"");
            out.print(name);
            out.print("\"");
        } else {
            out.print(name);
        }
        if (table.hasAlias()) {
            out.print(' ');
            out.print(table.getAlias());
        }

    }

    @Override
    public void join(Output out, JoinCriteria joinCriteria) {
        String type = joinCriteria.getJoinType().getType();
        Column source = joinCriteria.getSource();
        Column dest = joinCriteria.getDest();
        JoinCondition joinCondition = joinCriteria.getJoinCondition();

        out.print(type);
        out.space();
        out.print("JOIN");
        out.space();

        // if dest column available then simple ON join

        if (dest != null) {
            dest.getTable().write(out);
            out.space();

            out.print("ON");
            out.space();
            out.print(source);
            out.space();
            out.print("=");
            out.space();
            out.print(dest);
        } else {
            // if no dest column available then ON join with joinCondition
            joinCondition.getTable().write(out);
            out.space();
            out.print("ON");
            out.space();
            joinCondition.write(out);
        }
    }

}