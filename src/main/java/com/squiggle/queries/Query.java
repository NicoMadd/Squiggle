package com.squiggle.queries;

import java.util.*;
import java.util.function.Function;

import com.squiggle.Squiggle;
import com.squiggle.base.*;
import com.squiggle.exceptions.NoColumnsException;
import com.squiggle.exceptions.NoTableException;
import com.squiggle.output.*;
import com.squiggle.parsers.Parser;

public abstract class Query implements Outputable {

    public static final int indentSize = 4;

    protected Parser parser;
    protected Table baseTable;
    protected List<Column> columns;
    protected List<Criteria> criteria;

    public Query(Parser parser) {
        this.baseTable = null;
        this.columns = new LinkedList<>();
        this.criteria = new LinkedList<>();
        this.parser = parser;
    }

    public Query() {
        this.baseTable = null;
        this.columns = new LinkedList<>();
        this.criteria = new LinkedList<>();
        this.parser = Squiggle.getParser();
    }

    public String toString() {
        validate();
        validateMain();
        return ToStringer.toString(this);
    }

    // public String toString(Boolean indent) {
    // validate();
    // return ToStringer.toString(this);

    // }

    protected abstract void validate();

    protected void validateMain() {
        if (this.baseTable == null) {
            throw new NoTableException("Cannot make query without table");
        }
        if (this.columns.size() == 0) {
            throw new NoColumnsException("Cannot make query without related column");
        }
    }

    public Table getBaseTable() {
        return baseTable;
    }

    public List<Column> listColumns() {
        return Collections.unmodifiableList(columns);
    }

    public abstract List<Table> getUsedTables();

    public Query where(String columnName, Function<CriteriaBuilder, CriteriaBuilder> condition) {
        CriteriaBuilder criteriaBuilder = new CriteriaBuilder(new Column(this.baseTable, columnName));
        return condition.apply(criteriaBuilder).build().getClass() == NoCriteria.class ? this
                : this.addCriteria(condition.apply(criteriaBuilder).build());
    }

    private Query addCriteria(Criteria criteria) {
        this.criteria.add(criteria);
        return this;
    }

    // private Query removeCriteria(Criteria criteria) {
    // this.criteria.remove(criteria);
    // return this;
    // }

    public List<Criteria> listCriteria() {
        return Collections.unmodifiableList(criteria);
    }

}
