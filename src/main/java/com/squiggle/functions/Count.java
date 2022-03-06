package com.squiggle.functions;

import com.squiggle.base.AggregatedColumn;
import com.squiggle.output.Output;

public class Count extends SQLFunction {

    @Override
    public void write(Output out, AggregatedColumn aggregatedColumn) {
        parser.count(out, aggregatedColumn);
    }

}
