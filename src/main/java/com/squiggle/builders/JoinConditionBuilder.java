package com.squiggle.builders;

import com.squiggle.base.Column;
import com.squiggle.base.Table;
import com.squiggle.base.Joins.JoinCriteria;
import com.squiggle.queries.SelectQuery;

class JoinCondition {

}

public class JoinConditionBuilder {

    private LogicBuilder logicBuilder;

    public JoinConditionBuilder() {
        this.logicBuilder = new LogicBuilder();
    }

    public JoinConditionBuilder on(String column, String table) {
        this.logicBuilder.that(new Column(new Table(table), column));
        return this;
    }

    public JoinConditionBuilder equals(String column, String table) {
        this.logicBuilder.equals(new Column(new Table(table), column));
        return this;
    }

    // public JoinConditionBuilder o

    public JoinCondition build() {
        return null;
    }

}
