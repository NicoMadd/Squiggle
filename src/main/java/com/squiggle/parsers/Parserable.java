package com.squiggle.parsers;

import com.squiggle.Squiggle;
import com.squiggle.output.Outputable;

public abstract class Parserable implements Outputable {

    protected Parser parser;

    public Parserable(Parser parser) {
        this.parser = parser;
    }

    public Parserable() {
        this.parser = Squiggle.getParser();
    }

}
