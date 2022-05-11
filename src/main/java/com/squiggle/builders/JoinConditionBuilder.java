package com.squiggle.builders;

import com.squiggle.base.Column;
import com.squiggle.base.Table;
import com.squiggle.base.Joins.JoinCondition;

public class JoinConditionBuilder {

    private LogicBuilder logicBuilder;
    private Table fromTable;
    private Table toTable;

    public JoinConditionBuilder() {
        this.logicBuilder = new LogicBuilder();
    }

    public JoinConditionBuilder from(Column column) {
        this.fromTable = column.getTable();
        this.logicBuilder.that(column);
        return this;
    }

    public JoinConditionBuilder to(String table) {
        return to(new Table(table));
    }

    public JoinConditionBuilder to(Table table) {
        this.toTable = table;
        return this;
    }

    public JoinConditionBuilder on(String column) {
        return on(this.toTable.getColumn(column));
    }

    public JoinConditionBuilder on(Column column) {
        this.logicBuilder.equals(column);
        return this;
    }

    public JoinConditionBuilder and(String column) {
        return this.and(this.fromTable.getColumn(column));
    }

    public JoinConditionBuilder and(Column column) {
        this.logicBuilder.and(column);
        return this;
    }

    public JoinCondition build() {
        return new JoinCondition(this.logicBuilder, this.toTable);
    }
}
