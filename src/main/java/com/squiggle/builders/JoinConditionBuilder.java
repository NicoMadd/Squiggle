package com.squiggle.builders;

import com.squiggle.base.Column;
import com.squiggle.base.Table;
import com.squiggle.base.Joins.JoinCondition;

public class JoinConditionBuilder {

    private LogicBuilder logicBuilder;
    private Table fromTable;
    private Table toTable;
    private Table actualTable;

    public JoinConditionBuilder() {
        this.logicBuilder = new LogicBuilder();
    }

    public JoinConditionBuilder from(Column column) {
        this.fromTable = column.getTable();
        this.actualTable = this.fromTable;
        this.logicBuilder.that(column);
        return this;
    }

    public JoinConditionBuilder to(String table) {
        return to(new Table(table));
    }

    public JoinConditionBuilder to(String table, String tableAlias) {
        return to(new Table(table, tableAlias));
    }

    private JoinConditionBuilder to(Table table) {
        this.toTable = table;
        return this;
    }

    public JoinConditionBuilder on(String column) {
        return on(this.toTable.getColumn(column));
    }

    private JoinConditionBuilder on(Column column) {
        this.logicBuilder.equals(column);
        return this;
    }

    public JoinConditionBuilder and(String column) {
        return this.and(this.actualTable.getColumn(column));
    }

    private JoinConditionBuilder and(Column column) {
        this.logicBuilder.and(column);
        return this;
    }

    public JoinConditionBuilder is(String strValue) {
        this.logicBuilder.equals(strValue);
        return this;
    }

    public JoinConditionBuilder is(Integer intValue) {
        this.logicBuilder.equals(intValue);
        return this;
    }

    public JoinConditionBuilder is(Float floatValue) {
        this.logicBuilder.equals(floatValue);
        return this;
    }

    public JoinConditionBuilder is(Double doubleValue) {
        this.logicBuilder.equals(doubleValue);
        return this;
    }

    public JoinConditionBuilder is(Boolean boolValue) {
        this.logicBuilder.equals(boolValue);
        return this;
    }

    public JoinConditionBuilder is(Object column) {
        this.logicBuilder.equals(column);
        return this;
    }

    public JoinConditionBuilder switchTable() {
        if (this.toTable == null) {
            throw new IllegalStateException("toTable is null");
        }
        this.actualTable = this.toTable;
        return this;
    }

    public JoinCondition build() {
        return new JoinCondition(this.logicBuilder, this.toTable);
    }
}
