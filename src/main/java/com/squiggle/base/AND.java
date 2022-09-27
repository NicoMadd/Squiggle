package com.squiggle.base;

import com.squiggle.output.Output;

public class AND extends ComposableCriteria {

    public AND(Criteria... criterias) {
        super("AND", criterias);
    }

}
