package com.squiggle.parsers;

import com.squiggle.Squiggle;

public abstract class Parserable {

    protected Parser parser;

    public Parserable(Parser parser) {
        this.parser = parser;
    }

    public Parserable() {
        this.parser = Squiggle.getParser();
    }
}
