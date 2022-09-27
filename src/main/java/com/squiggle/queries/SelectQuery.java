package com.squiggle.queries;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.squiggle.base.AND;
import com.squiggle.base.Column;
import com.squiggle.base.Criteria;
import com.squiggle.base.NoCriteria;
import com.squiggle.base.Order;
import com.squiggle.base.Table;
import com.squiggle.base.Joins.FullJoin;
import com.squiggle.base.Joins.InnerJoin;
import com.squiggle.base.Joins.JoinCondition;
import com.squiggle.base.Joins.JoinCriteria;
import com.squiggle.base.Joins.LeftJoin;
import com.squiggle.base.Joins.OuterJoin;
import com.squiggle.base.Joins.RightJoin;
import com.squiggle.builders.CriteriaBuilder;
import com.squiggle.builders.JoinConditionBuilder;
import com.squiggle.builders.WhereConditionBuilder;
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
    protected List<Order> orders;
    protected List<JoinCriteria> joins;
    protected List<Column> groupBys;
    protected Integer limit;
    protected Integer offset;
    protected Table actualTable;

    public SelectQuery() {
        super();
        this.limit = -1;
        this.offset = -1;
        this.orders = new LinkedList<>();
        this.joins = new LinkedList<>();
        this.groupBys = new LinkedList<>();
        this.actualTable = null;

    }

    public Table getActualTable() {
        return this.actualTable;
    }

    public SelectQuery from(Table table) {
        this.baseTable = this.baseTable == null ? table : this.baseTable;
        this.actualTable = table;
        return this;
    }

    public SelectQuery from(String tableName, String alias) {
        return this.from(new Table(tableName, alias));
    }

    public SelectQuery from(String tableName) {
        return this.from(new Table(tableName));
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
        return this.select(getActualTable().getColumn(columnName));
    }

    public SelectQuery select(String columnName, String alias) {
        validate();
        return this.select(getActualTable().getColumn(columnName, alias));

    }

    public SelectQuery sum(String columName) {
        return this.select(getActualTable().getAggregatedColumn(columName, new Sum()));
    }

    public SelectQuery avg(String columName) {
        return this.average(columName);

    }

    public SelectQuery average(String columName) {
        return this.select(getActualTable().getAggregatedColumn(columName, new Average()));

    }

    public SelectQuery count(String columName) {
        return this.select(getActualTable().getAggregatedColumn(columName, new Count()));
    }

    public SelectQuery sum(String columName, String alias) {
        return this.select(getActualTable().getAggregatedColumn(columName, alias, new Sum()));
    }

    public SelectQuery avg(String columName, String alias) {
        return this.average(columName, alias);
    }

    public SelectQuery average(String columName, String alias) {
        return this.select(getActualTable().getAggregatedColumn(columName, alias, new Average()));

    }

    public SelectQuery count(String columName, String alias) {
        return this.select(getActualTable().getAggregatedColumn(columName, alias, new Count()));
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
        CriteriaBuilder criteriaBuilder = new CriteriaBuilder(new Column(getActualTable(), columnName));
        return condition.apply(criteriaBuilder).build().getClass() == NoCriteria.class ? this
                : this.addCriteria(condition.apply(criteriaBuilder).build());
    }

    public SelectQuery and(String columnName, Function<WhereConditionBuilder, WhereConditionBuilder> condition) {
        WhereConditionBuilder builder = new WhereConditionBuilder(new Column(getActualTable(), columnName));
        return this.addCriteria(new AND(condition.apply(builder).build()));
    }

    public SelectQuery or(String string, Object object) {
        return null;
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
        return this.groupBy(getActualTable().getColumn(columnName));
    }

    public SelectQuery groupBy(String columnName, String alias) {
        validate();
        return this.groupBy(getActualTable().getColumn(columnName, alias));

    }

    public SelectQuery order(Order order) {
        this.orders.add(order);
        return this;
    }

    public SelectQuery order(String columnName) {
        return this.order(columnName, true);
    }

    public SelectQuery order(String columnName, Boolean ascending) {
        Order order = new Order(getActualTable().getColumn(columnName), ascending);
        return this.order(order);

    }

    public SelectQuery order(String columnName, String tableName, Boolean orderDirection) {
        Order order = new Order(new Table(tableName).getColumn(columnName), orderDirection);
        return this.order(order);

    }

    /**
     * Syntax sugar for order(Order).
     */
    public SelectQuery order(Table table, String columnname, boolean ascending) {
        return SelectQuery.this.order(new Order(table.getColumn(columnname), ascending));
    }

    public SelectQuery order(Integer column) {
        return this.order(column, true);
    }

    public SelectQuery order(Integer columnIndex, Boolean ascending) {
        return this.order(new Order(columnIndex, ascending));
    }

    public SelectQuery removeOrder(Order order) {
        this.orders.remove(order);
        return this;
    }

    public List<Order> listOrder() {
        // Collections.reverse(order);
        return orders;
    }

    public Boolean hasOrder() {
        return !orders.isEmpty();
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
    public List<Table> getUsedTables(Boolean includeJoins) {

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
                if (joinCriteria.getSource() != null)
                    allTables.add(joinCriteria.getSource().getTable());
                if (joinCriteria.getDest() != null)
                    allTables.add(joinCriteria.getDest().getTable());
            }
        }

        if (includeJoins) {
            for (JoinCriteria join : this.getJoins()) {
                allTables.add(join.getJoinCondition().getTable());
            }
        }

        // Order columns depends on the ones been added previously. Theres no need to
        // add them again.

        // for (Order order : this.listOrder()) {
        // allTables.add(order.getColumn().getTable());
        // }
        LinkedList<Table> linkedList = new LinkedList<>(allTables);
        // Collections.reverse(linkedList);
        return linkedList;
    }

    public List<Table> getUsedTables() {
        return getUsedTables(false);
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

    public SelectQuery join(String columnName, Function<JoinConditionBuilder, JoinConditionBuilder> condition) {
        return innerJoin(columnName, condition);
    }

    // TODO provide a way to implement joins, left join, right join, inner join,
    // outer join

    public SelectQuery leftJoin(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return addJoin(new LeftJoin(srcTable.getColumn(srcColumnname), destTable.getColumn(destColumnname)))
                .from(destTable);
    }

    public SelectQuery leftJoin(String srcColumnname, String destTable, String destColumnname) {
        Table dstTable = new Table(destTable);
        return addJoin(new LeftJoin(getActualTable().getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery leftJoin(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        Table dstTable = new Table(destTable, tableAlias);
        return addJoin(new LeftJoin(getActualTable().getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery leftJoin(String srcColumnName,
            Function<JoinConditionBuilder, JoinConditionBuilder> condition) {
        JoinConditionBuilder joinConditionBuilder = new JoinConditionBuilder()
                .from(getActualTable().getColumn(srcColumnName));
        JoinCondition join = condition.apply(joinConditionBuilder).build();
        Table dstTable = join.getTable();
        return addJoin(new LeftJoin(getActualTable().getColumn(srcColumnName), join)).from(dstTable);
    }

    public SelectQuery rightJoin(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return addJoin(new RightJoin(srcTable.getColumn(srcColumnname), destTable.getColumn(destColumnname)))
                .from(destTable);
    }

    public SelectQuery rightJoin(String srcColumnname, String destTable, String destColumnname) {
        Table dstTable = new Table(destTable);
        return addJoin(new RightJoin(getActualTable().getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery rightJoin(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        Table dstTable = new Table(destTable, tableAlias);
        return addJoin(new RightJoin(getActualTable().getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery rightJoin(String srcColumnName,
            Function<JoinConditionBuilder, JoinConditionBuilder> condition) {
        JoinConditionBuilder joinConditionBuilder = new JoinConditionBuilder()
                .from(getActualTable().getColumn(srcColumnName));
        JoinCondition join = condition.apply(joinConditionBuilder).build();
        Table dstTable = join.getTable();
        return addJoin(new RightJoin(getActualTable().getColumn(srcColumnName), join)).from(dstTable);
    }

    public SelectQuery outerJoin(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return addJoin(new OuterJoin(srcTable.getColumn(srcColumnname), destTable.getColumn(destColumnname)))
                .from(destTable);
    }

    public SelectQuery outerJoin(String srcColumnname, String destTable, String destColumnname) {
        Table dstTable = new Table(destTable);
        return addJoin(new OuterJoin(getActualTable().getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery outerJoin(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        Table dstTable = new Table(destTable, tableAlias);
        return addJoin(new OuterJoin(getActualTable().getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery outerJoin(String srcColumnName,
            Function<JoinConditionBuilder, JoinConditionBuilder> condition) {
        JoinConditionBuilder joinConditionBuilder = new JoinConditionBuilder()
                .from(getActualTable().getColumn(srcColumnName));
        JoinCondition join = condition.apply(joinConditionBuilder).build();
        Table dstTable = join.getTable();
        return addJoin(new OuterJoin(getActualTable().getColumn(srcColumnName), join)).from(dstTable);
    }

    public SelectQuery innerJoin(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return addJoin(new InnerJoin(srcTable.getColumn(srcColumnname), destTable.getColumn(destColumnname)))
                .from(destTable);
    }

    public SelectQuery innerJoin(String srcColumnname, String destTable, String destColumnname) {
        Table dstTable = new Table(destTable);
        return addJoin(new InnerJoin(getActualTable().getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery innerJoin(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        Table dstTable = new Table(destTable, tableAlias);
        return addJoin(new InnerJoin(getActualTable().getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery innerJoin(String srcColumnName,
            Function<JoinConditionBuilder, JoinConditionBuilder> condition) {
        JoinConditionBuilder joinConditionBuilder = new JoinConditionBuilder()
                .from(getActualTable().getColumn(srcColumnName));
        JoinCondition join = condition.apply(joinConditionBuilder).build();
        Table dstTable = join.getTable();
        return addJoin(new InnerJoin(getActualTable().getColumn(srcColumnName), join)).from(dstTable);
    }

    public SelectQuery fullJoin(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        return addJoin(new FullJoin(srcTable.getColumn(srcColumnname), destTable.getColumn(destColumnname)))
                .from(destTable);
    }

    public SelectQuery fullJoin(String srcColumnname, String destTable, String destColumnname) {
        Table dstTable = new Table(destTable);
        return addJoin(new FullJoin(getActualTable().getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery fullJoin(String srcColumnname, String destTable, String tableAlias, String destColumnname) {
        Table dstTable = new Table(destTable, tableAlias);
        return addJoin(new FullJoin(getActualTable().getColumn(srcColumnname), dstTable.getColumn(destColumnname)))
                .from(dstTable);
    }

    public SelectQuery fullJoin(String srcColumnName,
            Function<JoinConditionBuilder, JoinConditionBuilder> condition) {
        JoinConditionBuilder joinConditionBuilder = new JoinConditionBuilder()
                .from(getActualTable().getColumn(srcColumnName));
        JoinCondition join = condition.apply(joinConditionBuilder).build();
        Table dstTable = join.getTable();
        return addJoin(new FullJoin(getActualTable().getColumn(srcColumnName), join)).from(dstTable);
    }

    /*
     * FIXME
     * This comes as a solution to change the joining table being used. Other
     * alternative it to change the method signature of the different join methods
     * but it would break the whole parser. Maybe take into account for future
     * implementations.
     * 
     * 
     * Solution made. Get all tables and search by the ones being used if any
     * exists, else throw exception.
     */

    private JoinCriteria getLastJoinCriteria() {
        return getJoins().get(getJoins().size() - 1);
    }

    public SelectQuery useJoinedTable() {
        Table lastJoinedTable = getLastJoinCriteria().getJoinCondition().getTable();
        this.from(lastJoinedTable);
        return this;
    }

    public SelectQuery useTable(String nameOrAlias) {
        Table selectedTable = getUsedTables(true).stream().filter(t -> t.matches(nameOrAlias)).findFirst()
                .orElseThrow(() -> new NoTableException("No table found with name or alias: " + nameOrAlias));
        return this.from(selectedTable);
    }

    public SelectQuery useTable(Integer tableIndex) {
        Table selectedTable = getUsedTables().get(tableIndex);
        return this.from(selectedTable);
    }

    public SelectQuery limit(int i) {
        this.limit = i;
        return this;
    }

    public Boolean withLimit() {
        return this.limit > 0;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public SelectQuery offset(int i) {
        validateOffset();
        this.offset = i;
        return this;
    }

    protected void validateOffset() {
        this.parser.validateOffset(this);
    }

    public Boolean withOffset() {
        return this.offset >= 0;
    }

    public Integer getOffset() {
        return this.offset;
    }

}
