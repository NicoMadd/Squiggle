package com.squiggle.base.Joins;

import com.squiggle.base.Column;

public class InnerJoin extends JoinCriteria {

    public InnerJoin(Column source, Column dest) {
        super(source, dest, JoinType.INNER);
    }

}
