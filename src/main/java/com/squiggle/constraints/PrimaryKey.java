package com.squiggle.constraints;

import com.squiggle.output.Output;

public class PrimaryKey extends Constraint {

    @Override
    public void write(Output out) {
        parser.primaryKey(out, this);
    }

}
