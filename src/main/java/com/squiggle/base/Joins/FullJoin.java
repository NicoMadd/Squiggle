package com.squiggle.base.Joins;

import com.squiggle.base.Column;

public class FullJoin extends JoinCriteria {

    public FullJoin(Column source, Column dest) {
        super(source, dest, JoinType.FULL);
    }

    public FullJoin(Column column, JoinCondition joinCondition) {
        super(column, JoinType.FULL, joinCondition);
    }

}
