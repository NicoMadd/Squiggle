package com.squiggle.base.Joins;

import com.squiggle.base.Column;

public class OuterJoin extends JoinCriteria {

    public OuterJoin(Column source, Column dest) {
        super(source, dest, JoinType.OUTER);
    }

    public OuterJoin(Column column, JoinCondition joinCondition) {
        super(column, JoinType.OUTER, joinCondition);
    }

}
