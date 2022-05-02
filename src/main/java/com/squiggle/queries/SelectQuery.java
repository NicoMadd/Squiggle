package com.squiggle.queries;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.squiggle.base.Column;
import com.squiggle.base.Criteria;
import com.squiggle.base.NoCriteria;
import com.squiggle.base.Order;
import com.squiggle.base.Table;
import com.squiggle.base.Joins.FullJoin;
import com.squiggle.base.Joins.InnerJoin;
import com.squiggle.base.Joins.JoinCriteria;
import com.squiggle.base.Joins.LeftJoin;
import com.squiggle.base.Joins.OuterJoin;
import com.squiggle.base.Joins.RightJoin;
import com.squiggle.builders.CriteriaBuilder;
import com.squiggle.exceptions.NoColumnsException;
import com.squiggle.exceptions.NoTableException;
import com.squiggle.functions.Average;
import com.squiggle.functions.Count;
import com.squiggle.functions.Sum;
import com.squiggle.output.Output;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author nmadeo - Nicolas Madeo
 */
public class SelectQuery extends Query {

    protected boolean isDistinct = false;
    protected Set<Order> order;
    protected List<JoinCriteria> joins;
    protected List<Column> groupBys;

    public SelectQuery() {
        super();
        this.order = new HashSet<>();
        this.joins = new LinkedList<>();
        this.groupBys = new LinkedList<>();

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
        validate();
        return this.select(this.baseTable.getColumn(columnName));
    }

    public SelectQuery select(String columnName, String alias) {
        validate();
        return this.select(this.baseTable.getColumn(columnName, alias));

    }

    public SelectQuery sum(String columName) {
        return this.select(this.baseTable.getAggregatedColumn(columName, new Sum()));
    }

    public SelectQuery avg(String columName) {
        return this.average(columName);

    }

    public SelectQuery average(String columName) {
        return this.select(this.baseTable.getAggregatedColumn(columName, new Average()));

    }

    public SelectQuery count(String columName) {
        return this.select(this.baseTable.getAggregatedColumn(columName, new Count()));
    }

    public SelectQuery sum(String columName, String alias) {
        return this.select(this.baseTable.getAggregatedColumn(columName, alias, new Sum()));
    }

    public SelectQuery avg(String columName, String alias) {
        return this.average(columName, alias);
    }

    public SelectQuery average(String columName, String alias) {
        return this.select(this.baseTable.getAggregatedColumn(columName, alias, new Average()));

    }

    public SelectQuery count(String columName, String alias) {
        return this.select(this.baseTable.getAggregatedColumn(columName, alias, new Count()));
    }

    public void validate() {
        if (this.baseTable == null) {
            throw new NoTableException("Cannot select column without table");
        }
    }

    @Override
    protected void validateMain() {
        super.validateMain();
        if (this.columns.size() == 0) {
            throw new NoColumnsException("Cannot make query without related column");
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

    public SelectQuery groupBy(Column column) {
        groupBys.add(column);
        return this;
    }

    public SelectQuery groupBy(String columnName) {
        validate();
        return this.groupBy(this.baseTable.getColumn(columnName));
    }

    public SelectQuery groupBy(String columnName, String alias) {
        validate();
        return this.groupBy(this.baseTable.getColumn(columnName, alias));

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

    public List<Column> listGroupBys() {
        return groupBys;
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

    private SelectQuery addJoin(JoinCriteria joinCriteria) {
        this.joins.add(joinCriteria);
        return this;
    }

    /**
     * Syntax sugar for addCriteria(JoinCriteria)
     */
    public SelectQuery join(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return innerJoin(srcTable, srcColumnname, destTable, destColumnname);
    }

    public SelectQuery join(String srcColumnname, String destTable, String destColumnname) {
        return innerJoin(srcColumnname, destTable, destColumnname);
    }

    public SelectQuery join(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        return innerJoin(srcColumnname, destTable, destColumnname);
    }

    // TODO provide a way to implement joins, left join, right join, inner join,
    // outer join

    public SelectQuery leftJoin(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return addJoin(new LeftJoin(srcTable.getColumn(srcColumnname), destTable.getColumn(destColumnname)))
                .from(destTable);
    }

    public SelectQuery leftJoin(String srcColumnname, String destTable, String destColumnname) {
        Table dstTable = new Table(destTable);
        return addJoin(new LeftJoin(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery leftJoin(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        Table dstTable = new Table(destTable, tableAlias);
        return addJoin(new LeftJoin(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery rightJoin(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return addJoin(new RightJoin(srcTable.getColumn(srcColumnname), destTable.getColumn(destColumnname)))
                .from(destTable);
    }

    public SelectQuery rightJoin(String srcColumnname, String destTable, String destColumnname) {
        Table dstTable = new Table(destTable);
        return addJoin(new RightJoin(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery rightJoin(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        Table dstTable = new Table(destTable, tableAlias);
        return addJoin(new RightJoin(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery outerJoin(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return addJoin(new OuterJoin(srcTable.getColumn(srcColumnname), destTable.getColumn(destColumnname)))
                .from(destTable);
    }

    public SelectQuery outerJoin(String srcColumnname, String destTable, String destColumnname) {
        Table dstTable = new Table(destTable);
        return addJoin(new OuterJoin(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery outerJoin(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        Table dstTable = new Table(destTable, tableAlias);
        return addJoin(new OuterJoin(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery innerJoin(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return addJoin(new InnerJoin(srcTable.getColumn(srcColumnname), destTable.getColumn(destColumnname)))
                .from(destTable);
    }

    public SelectQuery innerJoin(String srcColumnname, String destTable, String destColumnname) {
        Table dstTable = new Table(destTable);
        return addJoin(new InnerJoin(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery innerJoin(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        Table dstTable = new Table(destTable, tableAlias);
        return addJoin(new InnerJoin(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery fullJoin(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return addJoin(new FullJoin(srcTable.getColumn(srcColumnname), destTable.getColumn(destColumnname)))
                .from(destTable);
    }

    public SelectQuery fullJoin(String srcColumnname, String destTable, String destColumnname) {
        Table dstTable = new Table(destTable);
        return addJoin(new FullJoin(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery fullJoin(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        Table dstTable = new Table(destTable, tableAlias);
        return addJoin(new FullJoin(this.baseTable.getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

}
