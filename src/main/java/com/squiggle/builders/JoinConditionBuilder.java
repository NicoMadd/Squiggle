package com.squiggle.builders;

import com.squiggle.base.Column;
import com.squiggle.base.Table;
import com.squiggle.base.Joins.JoinCondition;

public class JoinConditionBuilder {

    private LogicBuilder logicBuilder;
    private Table fromTable;
    private Table toTable;
    private Boolean to;
    private Boolean and;

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
        this.to = true;
        return this;
    }

    public JoinConditionBuilder on(String column) {
        return to ? on(toTable.getColumn(column)) : on(fromTable.getColumn(column));
    }

    public JoinConditionBuilder on(Column column) {
        if (to) {
            logicBuilder.equals(column);
            this.to = false;
        } else {
            if (and) {
                System.out.println("and: " + column);

                logicBuilder.and(column);
                and = false;
            } else {
                logicBuilder.that(column);
            }
        }

        return this;
    }

    public JoinConditionBuilder and(String column) {
        return this.and(this.fromTable.getColumn(column));
    }

    public JoinConditionBuilder and(Column column) {
        this.and = true;
        return this.on(column);
    }

    public JoinCondition build() {
        return new JoinCondition(this.logicBuilder, this.toTable);
    }
}
