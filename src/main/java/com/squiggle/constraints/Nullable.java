package com.squiggle.constraints;

import com.squiggle.output.Output;

public class Nullable extends Constraint {

    @Override
    public void write(Output out) {
        parser.nullable(out, this);
    }

}
