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
        this.parser.deleteQuery(out, this);
    }

    @Override
    public List<Table> getUsedTables() {
        // TODO Auto-generated method stub
        return null;
    }

    public void validate() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void validateMain() {
        super.validateMain();

        // This cant be a condition for an SQL Query since a delete query with no where
        // condition is a valid query.
        // TODO: Find a way to let the user know about this!!
        // if (this.criteria.size() == 0) {
        // throw new NoWhereClauseException();
        // }
    }

    public DeleteQuery from(Table table) {
        this.baseTable = table;
        return this;
    }

    public DeleteQuery from(String tableName, String alias) {
        this.baseTable = new Table(tableName, alias);
        return this;
    }

    public DeleteQuery from(String tableName) {
        this.baseTable = new Table(tableName);
        return this;
    }

    public Table getBaseTable() {
        return baseTable;
    }

    public DeleteQuery where(String columnName, Function<CriteriaBuilder, CriteriaBuilder> condition) {
        CriteriaBuilder criteriaBuilder = new CriteriaBuilder(new Column(this.baseTable, columnName));
        return condition.apply(criteriaBuilder).build().getClass() == NoCriteria.class ? this
                : this.addCriteria(condition.apply(criteriaBuilder).build());
    }

    public DeleteQuery addCriteria(Criteria criteria) {
        this.criteria.add(criteria);
        return this;
    }

}
