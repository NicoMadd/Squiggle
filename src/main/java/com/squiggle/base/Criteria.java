package com.squiggle.base;

import com.squiggle.output.Output;
import com.squiggle.output.ToStringer;
import com.squiggle.parsers.Parserable;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 */
public abstract class Criteria extends Parserable {

    protected String separator = "AND";

    public abstract void write(Output out);

    public String toString() {
        return ToStringer.toString(this);
    }

    public String getSeparator() {
        return separator;
    }

}
