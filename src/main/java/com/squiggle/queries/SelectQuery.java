package com.squiggle.queries;

import java.util.*;
import java.util.function.Function;

import com.squiggle.base.*;
import com.squiggle.output.*;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author nmadeo - Nicolas Madeo
 */
public class SelectQuery extends Query {

    protected boolean isDistinct = false;
    protected List order;

    public SelectQuery() {
        super();
        this.order = new ArrayList();

    }

    public SelectQuery from(Table table) {
        this.baseTable = table;
        return this;
    }

    public SelectQuery from(String tableName, String alias) {
        this.baseTable = new Table(tableName, alias);
        return this;
    }

    public SelectQuery from(String tableName) {
        this.baseTable = new Table(tableName);
        return this;
    }

    public Table getBaseTable() {
        return baseTable;
    }

    public SelectQuery select(Column column) {
        columns.add(column);
        return this;
    }

    public SelectQuery select(String columnName) {
        validateSelect();
        return this.select(this.baseTable.getColumn(columnName));
    }

    private void validateSelect() {
        if (this.baseTable == null) {
            throw new IllegalStateException("Cannot select column without table");
        }
    }

    /**
     * Syntax sugar for select(Column).
     */
    public SelectQuery select(Table table, String columname) {
        return this.select(table.getColumn(columname));
    }

    public SelectQuery removeColumn(Column column) {
        columns.remove(column);
        return this;
    }

    public List listColumns() {
        return Collections.unmodifiableList(columns);
    }

    public boolean isDistinct() {
        return isDistinct;
    }

    public SelectQuery setDistinct(boolean distinct) {
        isDistinct = distinct;
        return this;
    }

    public SelectQuery where(String columnName, Function<CriteriaBuilder, CriteriaBuilder> condition) {
        CriteriaBuilder criteriaBuilder = new CriteriaBuilder(new Column(this.baseTable, columnName));
        return condition.apply(criteriaBuilder).build().getClass() == NoCriteria.class ? this
                : this.addCriteria(condition.apply(criteriaBuilder).build());
    }

    public SelectQuery addCriteria(Criteria criteria) {
        this.criteria.add(criteria);
        return this;
    }

    public SelectQuery removeCriteria(Criteria criteria) {
        this.criteria.remove(criteria);
        return this;
    }

    public List listCriteria() {
        return Collections.unmodifiableList(criteria);
    }

    /**
     * Syntax sugar for addCriteria(JoinCriteria)
     */
    public SelectQuery join(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return addCriteria(new JoinCriteria(srcTable.getColumn(srcColumnname), destTable.getColumn(destColumnname)));
    }

    public SelectQuery join(String srcColumnname, String destTable, String destColumnname) {
        Table dstTable = new Table(destTable);
        addCriteria(new JoinCriteria(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)));
        return this.from(dstTable);
    }

    public SelectQuery join(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        Table dstTable = new Table(destTable, tableAlias);
        addCriteria(new JoinCriteria(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)));
        return this.from(dstTable);
    }

    public SelectQuery order(Order order) {
        this.order.add(order);
        return this;
    }

    public SelectQuery order(String columnName, Boolean orderDirection) {
        Order order = new Order(new Column(this.baseTable, columnName), orderDirection);
        this.order.add(order);
        return this;
    }

    public SelectQuery order(String columnName, String tableName, Boolean orderDirection) {
        Order order = new Order(new Column(new Table(tableName), columnName), orderDirection);
        this.order.add(order);
        return this;
    }

    /**
     * Syntax sugar for order(Order).
     */
    public SelectQuery order(Table table, String columnname, boolean ascending) {
        return SelectQuery.this.order(new Order(table.getColumn(columnname), ascending));
    }

    public SelectQuery removeOrder(Order order) {
        this.order.remove(order);
        return this;
    }

    public List listOrder() {
        return Collections.unmodifiableList(order);
    }

    public void write(Output out) {

        out.println("SELECT");
        if (isDistinct) {
            out.println(" DISTINCT");
        }

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

        // Add order
        if (order.size() > 0) {
            out.println("ORDER BY");
            out.indent();
            appendList(out, order, ",");
            out.unindent();
        }
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

        { // Get all tables used by columns
            Iterator i = order.iterator();
            while (i.hasNext()) {
                Order curr = (Order) i.next();
                Table c = curr.getColumn().getTable();
                if (!allTables.contains(c)) {
                    allTables.add(c);
                }
            }
        }

        return allTables;
    }

}
