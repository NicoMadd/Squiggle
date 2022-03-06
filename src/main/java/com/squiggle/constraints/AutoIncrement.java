package com.squiggle.constraints;

import com.squiggle.output.Output;

public class AutoIncrement extends Constraint {

    private Integer start;
    private Integer increment;

    public AutoIncrement(Integer start, Integer increment) {
        super();
        this.start = start;
        this.increment = increment;
    }

    @Override
    public void write(Output out) {
        this.parser.autoIncrement(out, this);
    }

    public Integer getStart() {
        return start;
    }

    public Integer getIncrement() {
        return increment;
    }

}
