package com.squiggle.base.Transactables;

import com.squiggle.Squiggle;
import com.squiggle.output.Output;
import com.squiggle.parsers.Parser;

public class Commit implements Transactable {

    Parser parser;

    public Commit(Parser parser) {
        this.parser = parser;
    }

    public Commit() {
        this.parser = Squiggle.getParser();

    }

    @Override
    public void write(Output out) {
        this.parser.commit(out, this);
    }

}
