package com.squiggle.parsers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.squiggle.base.Column;
import com.squiggle.base.Row;
import com.squiggle.output.Output;
import com.squiggle.output.Outputable;
import com.squiggle.queries.DeleteQuery;
import com.squiggle.queries.InsertQuery;
import com.squiggle.queries.SelectQuery;
import com.squiggle.queries.UpdateQuery;
import com.squiggle.types.Type;

public class SqlServerParser extends Parser {

    public SqlServerParser() {
    }

    /**
     * Iterate through a Collection and append all entries (using .toString()) to a
     * StringBuffer.
     */
    private void appendList(Output out, Collection<? extends Outputable> outputables, String seperator) {
        for (Iterator<? extends Outputable> i = outputables.iterator(); i.hasNext();) {
            Outputable curr = (Outputable) i.next();
            curr.write(out);
            if (i.hasNext()) {
                out.print(seperator);
                out.space();

            }
        }

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
        appendList(out, selectQuery.getUsedTables(), ",");

        // Add criteria
        if (selectQuery.listCriteria().size() > 0) {
            out.space();
            out.print("WHERE");
            out.space();

            appendList(out, selectQuery.listCriteria(), "AND");

        }

        // Add order
        if (selectQuery.listOrder().size() > 0) {
            out.print(" ORDER BY");

            appendList(out, selectQuery.listOrder(), ",");

        }

    }

    /**
     * Iterate through a Collection and append all entries (using .toString()) to a
     * StringBuffer.
     */
    private void appendObjectList(Output out, List<Type> typeList, String separator) {

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
        System.out.print("rows.size() = " + insertQuery.getRows().size());
        System.out.print(insertQuery.getLastRow().toString());
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
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteQuery(Output out, DeleteQuery deleteQuery) {
        // TODO Auto-generated method stub

    }

}