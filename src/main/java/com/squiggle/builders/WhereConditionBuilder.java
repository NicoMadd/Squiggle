package com.squiggle.builders;

import com.squiggle.base.Column;
import com.squiggle.base.Subquery;
import com.squiggle.base.Table;
import com.squiggle.base.Joins.JoinCondition;

public class WhereConditionBuilder {

    private LogicBuilder logicBuilder;
    private Table fromTable;
    private Table toTable;
    private Table actualTable;

    public WhereConditionBuilder() {
        this.logicBuilder = new LogicBuilder();
    }

    public WhereConditionBuilder from(Column column) {
        this.fromTable = column.getTable();
        this.actualTable = this.fromTable;
        this.logicBuilder.that(column);
        return this;
    }

    public WhereConditionBuilder to(String table) {
        return to(new Table(table));
    }

    public WhereConditionBuilder to(String table, String tableAlias) {
        return to(new Table(table, tableAlias));
    }

    private WhereConditionBuilder to(Table table) {
        // System.out.println(table.toString());
        this.toTable = table;
        return this;
    }

    public WhereConditionBuilder on(String column) {
        return on(this.toTable.getColumn(column));
    }

    private WhereConditionBuilder on(Column column) {
        this.logicBuilder.equals(column);
        return this;
    }

    public WhereConditionBuilder and(String column) {
        return this.and(this.actualTable.getColumn(column));
    }

    private WhereConditionBuilder and(Column column) {
        this.logicBuilder.and(column);
        return this;
    }

    public WhereConditionBuilder is(String strValue) {
        this.logicBuilder.equals(strValue);
        return this;
    }

    public WhereConditionBuilder is(Integer intValue) {
        this.logicBuilder.equals(intValue);
        return this;
    }

    public WhereConditionBuilder is(Float floatValue) {
        this.logicBuilder.equals(floatValue);
        return this;
    }

    public WhereConditionBuilder is(Double doubleValue) {
        this.logicBuilder.equals(doubleValue);
        return this;
    }

    public WhereConditionBuilder is(Boolean boolValue) {
        this.logicBuilder.equals(boolValue);
        return this;
    }

    public WhereConditionBuilder is(Object column) {
        this.logicBuilder.equals(column);
        return this;
    }

    public WhereConditionBuilder switchTable() {
        if (this.toTable == null) {
            throw new IllegalStateException("toTable is null");
        }
        this.actualTable = this.toTable;
        return this;
    }

    public JoinCondition build() {
        return new JoinCondition(this.logicBuilder, this.toTable);
    }

    public WhereConditionBuilder toSub(String subquery) {
        return to(new Subquery(subquery));
    }

    public WhereConditionBuilder toSub(String subquery, String alias) {
        return to(new Subquery(subquery, alias));
    }

}
