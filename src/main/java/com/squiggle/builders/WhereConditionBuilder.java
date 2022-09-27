package com.squiggle.builders;

import com.squiggle.base.Column;
import com.squiggle.base.Table;
import com.squiggle.base.WhereCondition;

public class WhereConditionBuilder {

    private LogicBuilder logicBuilder;
    private Table fromTable;
    private Table toTable;
    private Table actualTable;
    private Column column;

    public WhereConditionBuilder(Column column) {
        this.logicBuilder = new LogicBuilder();
        this.column = column;
    }

    public WhereConditionBuilder from(Column column) {
        this.fromTable = column.getTable();
        this.actualTable = this.fromTable;
        this.logicBuilder.that(column);
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

    public WhereConditionBuilder equals(String strValue) {
        this.logicBuilder.equals(strValue);
        return this;
    }

    public WhereConditionBuilder equals(Integer intValue) {
        this.logicBuilder.equals(intValue);
        return this;
    }

    public WhereConditionBuilder equals(Float floatValue) {
        this.logicBuilder.equals(floatValue);
        return this;
    }

    public WhereConditionBuilder equals(Double doubleValue) {
        this.logicBuilder.equals(doubleValue);
        return this;
    }

    public WhereConditionBuilder equals(Boolean boolValue) {
        this.logicBuilder.equals(boolValue);
        return this;
    }

    public WhereCondition build() {
        return new WhereCondition(this.logicBuilder, this.toTable);
    }
}
