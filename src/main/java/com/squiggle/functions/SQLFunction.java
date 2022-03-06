package com.squiggle.functions;

import com.squiggle.base.AggregatedColumn;
import com.squiggle.output.Output;
import com.squiggle.parsers.Parserable;

abstract public class SQLFunction extends Parserable {

    abstract public void write(Output out, AggregatedColumn aggregatedColumn);

    public void write(Output out) {
        throw new UnsupportedOperationException("This function is not supported in this context");
    };
}
