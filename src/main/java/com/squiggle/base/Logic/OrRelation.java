package com.squiggle.base.Logic;

import com.squiggle.output.Output;

public class OrRelation extends Expression {

    public OrRelation() {
        super();
    }

    @Override
    public void write(Output out) {
        out.space();
        out.print("OR");
        out.space();
    }

}
