package com.squiggle.base.Transactables;

import com.squiggle.Squiggle;
import com.squiggle.output.Output;
import com.squiggle.parsers.Parser;

public class Rollback implements Transactable {

    Parser parser;

    public Rollback(Parser parser) {
        this.parser = parser;
    }

    public Rollback() {
        this.parser = Squiggle.getParser();
    }

    @Override
    public void write(Output out) {
        this.parser.rollback(out, this);
    }

}
