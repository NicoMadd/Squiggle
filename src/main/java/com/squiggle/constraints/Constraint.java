package com.squiggle.constraints;

import com.squiggle.Squiggle;
import com.squiggle.output.Outputable;
import com.squiggle.parsers.Parser;

public abstract class Constraint implements Outputable {

    protected Parser parser;

    public Constraint(Parser parser) {
        this.parser = parser;
    }

    public Constraint() {
        this.parser = Squiggle.getParser();
    }

}
