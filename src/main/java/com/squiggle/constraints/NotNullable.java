package com.squiggle.constraints;

import com.squiggle.output.Output;

public class NotNullable extends Constraint {

    @Override
    public void write(Output out) {
        out.print("NOT NULL");
    }

}
