package com.squiggle.queries;

import java.util.*;
import java.util.function.Function;

import com.squiggle.base.*;
import com.squiggle.output.*;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author nmadeo - Nicolas Madeo
 */
public class DeleteQuery extends Query {

    public static final int indentSize = 4;

    public DeleteQuery() {
        super();

    }

    public void write(Output out) {

        out.println("DELETE");

        // Add columns to select
        out.indent();
        appendList(out, columns, ",");
        out.unindent();

        // Add tables to select from
        out.println("FROM");

        // Determine all tables used in query
        out.indent();
        appendList(out, findAllUsedTables(), ",");
        out.unindent();

        // Add criteria
        if (criteria.size() > 0) {
            out.println("WHERE");
            out.indent();
            appendList(out, criteria, "AND");
            out.unindent();
        }
    }

    /**
     * Find all the tables used in the query (from columns, criteria and order).
     *
     * @return List of {@link com.squiggle.base.systech.Squiggle.Table}s
     */
    private List findAllUsedTables() {

        List allTables = new ArrayList();
        allTables.add(baseTable);

        { // Get all tables used by columns
            Iterator i = columns.iterator();
            while (i.hasNext()) {
                Table curr = ((Column) i.next()).getTable();
                if (!allTables.contains(curr)) {
                    allTables.add(curr);
                }
            }
        }

        { // Get all tables used by criteria
            Iterator i = criteria.iterator();
            while (i.hasNext()) {
                try {
                    JoinCriteria curr = (JoinCriteria) i.next();
                    if (!allTables.contains(curr.getSource().getTable())) {
                        allTables.add(curr.getSource().getTable());
                    }
                    if (!allTables.contains(curr.getDest().getTable())) {
                        allTables.add(curr.getDest().getTable());
                    }
                } catch (ClassCastException e) {
                } // not a JoinCriteria
            }
        }

        return allTables;
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
            out.println();
        }
    }

}
