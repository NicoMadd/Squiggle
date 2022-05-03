package com.squiggle.base.Logic;

import com.squiggle.output.Output;

public class AndRelation extends Expression {

    public AndRelation() {
        super();
    }

    @Override
    public void write(Output out) {
        out.space();
        out.print("AND");
        out.space();

    }

}
