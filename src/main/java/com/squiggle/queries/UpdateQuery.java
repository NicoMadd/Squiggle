package com.squiggle.queries;

import java.util.*;
import java.util.function.Function;

import com.squiggle.base.*;
import com.squiggle.output.*;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author nmadeo - Nicolas Madeo
 */
public class UpdateQuery extends Query {

    public static final int indentSize = 4;

    private Table baseTable;
    private List columns;
    private boolean isDistinct = false;
    private List criteria;
    private List order;

    public UpdateQuery() {
        // this.table = table;
        columns = new ArrayList();
        criteria = new ArrayList();
        order = new ArrayList();
    }

    public UpdateQuery from(Table table) {
        this.baseTable = table;
        return this;
    }

    public UpdateQuery from(String tableName, String alias) {
        this.baseTable = new Table(tableName, alias);
        return this;
    }

    public UpdateQuery from(String tableName) {
        this.baseTable = new Table(tableName);
        return this;
    }

    public Table getBaseTable() {
        return baseTable;
    }

    public UpdateQuery select(Column column) {
        columns.add(column);
        return this;
    }

    public UpdateQuery select(String columnName) {
        if (this.baseTable == null) {
            throw new IllegalStateException("Cannot select column without table");
        }
        return this.select(this.baseTable.getColumn(columnName));
    }

    /**
     * Syntax sugar for select(Column).
     */
    public UpdateQuery select(Table table, String columname) {
        return this.select(table.getColumn(columname));
    }

    public UpdateQuery removeColumn(Column column) {
        columns.remove(column);
        return this;
    }

    public List listColumns() {
        return Collections.unmodifiableList(columns);
    }

    public boolean isDistinct() {
        return isDistinct;
    }

    public UpdateQuery setDistinct(boolean distinct) {
        isDistinct = distinct;
        return this;
    }

    public UpdateQuery where(String columnName, Function<CriteriaBuilder, CriteriaBuilder> condition) {
        CriteriaBuilder criteriaBuilder = new CriteriaBuilder(new Column(this.baseTable, columnName));
        return condition.apply(criteriaBuilder).build().getClass() == NoCriteria.class ? this
                : this.addCriteria(condition.apply(criteriaBuilder).build());
    }

    public UpdateQuery addCriteria(Criteria criteria) {
        this.criteria.add(criteria);
        return this;
    }

    public UpdateQuery removeCriteria(Criteria criteria) {
        this.criteria.remove(criteria);
        return this;
    }

    public List listCriteria() {
        return Collections.unmodifiableList(criteria);
    }

    /**
     * Syntax sugar for addCriteria(JoinCriteria)
     */
    public UpdateQuery join(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return addCriteria(new JoinCriteria(srcTable.getColumn(srcColumnname),
                destTable.getColumn(destColumnname)));
    }

    public UpdateQuery join(String srcColumnname, String destTable, String destColumnname) {
        Table dstTable = new Table(destTable);
        addCriteria(new JoinCriteria(this.baseTable.getColumn(srcColumnname),
                dstTable.getColumn(destColumnname)));
        return this.from(dstTable);
    }

    public UpdateQuery join(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        Table dstTable = new Table(destTable, tableAlias);
        addCriteria(new JoinCriteria(this.baseTable.getColumn(srcColumnname),
                dstTable.getColumn(destColumnname)));
        return this.from(dstTable);
    }

    public UpdateQuery order(Order order) {
        this.order.add(order);
        return this;
    }

    public UpdateQuery order(String columnName, Boolean orderDirection) {
        Order order = new Order(new Column(this.baseTable, columnName),
                orderDirection);
        this.order.add(order);
        return this;
    }

    public UpdateQuery order(String columnName, String tableName, Boolean orderDirection) {
        Order order = new Order(new Column(new Table(tableName), columnName),
                orderDirection);
        this.order.add(order);
        return this;
    }

    /**
     * Syntax sugar for order(Order).
     */
    public UpdateQuery order(Table table, String columnname, boolean ascending) {
        return UpdateQuery.this.order(new Order(table.getColumn(columnname),
                ascending));
    }

    public UpdateQuery removeOrder(Order order) {
        this.order.remove(order);
        return this;
    }

    public List listOrder() {
        return Collections.unmodifiableList(order);
    }

    public void write(Output out) {
        this.parser.updateQuery(this, out);
    }

    /**
     * Iterate through a Collection and append all entries (using .toString()) to
     * a
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

    @Override
    public List<Table> getUsedTables() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void validate() {
        // TODO Auto-generated method stub

    }

}
