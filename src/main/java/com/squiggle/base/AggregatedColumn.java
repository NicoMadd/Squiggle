package com.squiggle.base;

import com.squiggle.functions.SQLFunction;
import com.squiggle.output.Output;

/**
 * @author <a href="https://github.com/NicoMadd">Nicolas Madeo</a>
 */
public class AggregatedColumn extends Column {

    SQLFunction function;

    public AggregatedColumn(Table table, String name, SQLFunction function) {
        super(table, name);
        this.function = function;
    }

    public AggregatedColumn(Table table, String name, String alias, SQLFunction function) {
        super(table, name, alias);
        this.function = function;
    }

    public void write(Output out) {
        parser.aggregatedColumn(out, this);
    }

    public void mainWrite(Output out) {
        super.write(out);
    }

    public SQLFunction getFunction() {
        return function;
    }

}
