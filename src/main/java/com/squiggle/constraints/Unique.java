package com.squiggle.constraints;

import com.squiggle.output.Output;

public class Unique extends Constraint {

    @Override
    public void write(Output out) {
        out.print("UNIQUE");
    }

}
