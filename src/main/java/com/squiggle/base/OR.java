package com.squiggle.base;

public class OR extends ComposableCriteria {

    public OR(Criteria... criterias) {
        super("OR", criterias);

    }

}
