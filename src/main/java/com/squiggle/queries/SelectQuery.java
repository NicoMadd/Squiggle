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
    protected Set<Order> order;
    protected List<JoinCriteria> joins;

    public SelectQuery() {
        super();
        this.order = new HashSet<>();
        this.joins = new LinkedList<>();

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

    private SelectQuery addCriteria(Criteria criteria) {
        this.criteria.add(criteria);
        return this;
    }

    private SelectQuery addJoin(JoinCriteria joinCriteria) {
        this.joins.add(joinCriteria);
        return this;
    }

    /**
     * Syntax sugar for addCriteria(JoinCriteria)
     */
    public SelectQuery join(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return addJoin(new JoinCriteria(srcTable.getColumn(srcColumnname), destTable.getColumn(destColumnname)))
                .from(destTable);
    }

    public SelectQuery join(String srcColumnname, String destTable, String destColumnname) {
        Table dstTable = new Table(destTable);
        return addJoin(new JoinCriteria(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery join(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        Table dstTable = new Table(destTable, tableAlias);
        return addJoin(new JoinCriteria(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
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

    public Set<Order> listOrder() {
        return order;
    }

    public void write(Output out) {
        this.parser.selectQuery(this, out);
    }

    /**
     * Find all the tables used in the query (from columns, criteria and order).
     *
     * @return List of {@link com.squiggle.base.systech.Squiggle.Table}s
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

        for (Order order : this.listOrder()) {
            allTables.add(order.getColumn().getTable());
        }
        LinkedList<Table> linkedList = new LinkedList<>(allTables);
        Collections.reverse(linkedList);
        return linkedList;
    }

    public List<JoinCriteria> getJoins() {
        return this.joins;
    }

}
