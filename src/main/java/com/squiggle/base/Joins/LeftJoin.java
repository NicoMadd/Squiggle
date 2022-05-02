package com.squiggle.base.Joins;

import com.squiggle.base.Column;

public class LeftJoin extends JoinCriteria {

    public LeftJoin(Column source, Column dest) {
        super(source, dest, JoinType.LEFT);
    }

}
