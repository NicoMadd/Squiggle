package com.squiggle.base.Joins;

import com.squiggle.base.Column;

public class RightJoin extends JoinCriteria {

    public RightJoin(Column source, Column dest) {
        super(source, dest, JoinType.RIGHT);
    }

}
