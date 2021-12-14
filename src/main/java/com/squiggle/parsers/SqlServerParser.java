package com.squiggle.parsers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.squiggle.base.Column;
import com.squiggle.base.Row;
import com.squiggle.output.Output;
import com.squiggle.output.Outputable;
import com.squiggle.queries.InsertQuery;
import com.squiggle.queries.SelectQuery;

public class SqlServerParser extends Parser {

    public SqlServerParser() {
    }

    /**
     * Iterate through a Collection and append all entries (using .toString()) to a
     * StringBuffer.
     */
    private void appendList(Output out, Collection collection, String seperator) {
        Iterator i = collection.iterator();
        boolean hasNext = i.hasNext();

        while (hasNext) {
            Outputable curr = (Outputable) i.next();
            hasNext = i.hasNext();
            curr.write(out);
            out.print(' ');
            if (hasNext) {
                out.print(seperator);
            }
            // out.println();
        }
    }

    @Override
    public void selectQuery(SelectQuery selectQuery, Output out) {

        out.println("SELECT");
        if (selectQuery.isDistinct()) {
            out.println(" DISTINCT");
        }

        // Add columns to select
        out.indent();
        appendList(out, selectQuery.listColumns(), ",");
        out.unindent();

        // Add tables to select from
        out.println("FROM");

        // Determine all tables used in query
        out.indent();
        appendList(out, selectQuery.getUsedTables(), ",");
        out.unindent();

        // Add criteria
        if (selectQuery.listCriteria().size() > 0) {
            out.println("WHERE");
            out.indent();
            appendList(out, selectQuery.listCriteria(), "AND");
            out.unindent();
        }

        // Add order
        if (selectQuery.listOrder().size() > 0) {
            out.println("ORDER BY");
            out.indent();
            appendList(out, selectQuery.listOrder(), ",");
            out.unindent();
        }

    }

    /**
     * Iterate through a Collection and append all entries (using .toString()) to a
     * StringBuffer.
     */
    private void appendObjectList(Output out, Collection<Object> collection, String separator) {
        Iterator<Object> i = collection.iterator();
        boolean hasNext = i.hasNext();

        while (hasNext) {
            Object curr = i.next();
            hasNext = i.hasNext();
            out.print(curr);
            out.print(' ');
            if (hasNext) {
                out.print(separator);
            }
        }
    }

    @Override
    public void insertQuery(InsertQuery insertQuery, Output out) {

        if (insertQuery.getLastRow().getValuesCount() > 0)
            insertQuery.endRow();

        out.println("INSERT INTO");

        // Add into table
        out.print(insertQuery.getBaseTable());

        // add column names
        out.indent();
        out.print(" (");
        List<Column> insertColumns = insertQuery.listColumns();
        insertColumns.forEach(col -> col.writeWithTable(false));
        appendList(out, insertColumns, ",");
        out.println(")");
        out.unindent();

        // add values
        out.indent();
        out.print("VALUES ");
        System.out.println("rows.size() = " + insertQuery.getRows().size());
        System.out.println(insertQuery.getLastRow().toString());
        for (Row row : insertQuery.getRows()) {
            out.print("(");
            appendObjectList(out, row.getValues(), ",");
            out.println(")");
        }

        out.unindent();

        // Add criteria
        if (insertQuery.listCriteria().size() > 0) {
            out.println("WHERE");
            out.indent();
            appendList(out, insertQuery.listCriteria(), "AND");
            out.unindent();
        }

    }

}