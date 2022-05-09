package com.squiggle.builders;

import com.squiggle.base.Column;
import com.squiggle.base.Table;
import com.squiggle.base.Joins.JoinCondition;

public class JoinConditionBuilder {

    private LogicBuilder logicBuilder;
    private Table table;

    public JoinConditionBuilder() {
        this.logicBuilder = new LogicBuilder();
    }

    public JoinConditionBuilder on(String column) {
        validateOn();
        this.logicBuilder.that(table.getColumn(column));
        return this;
    }

    private void validateOn() {
        if(table == null) {
            throw new IllegalStateException("Table must be set before calling on");
        }
    }

    public JoinConditionBuilder equals(String column, String table) {
        this.logicBuilder.equals(new Column(new Table(table), column));
        return this;
    }

    // public JoinConditionBuilder o

    public JoinCondition build(Column source) {
        
        return new JoinCondition(logicBuilder,table);
    }

    public JoinConditionBuilder from(String table) {
        this.table = new Table(table);
        return this;
    }

}
