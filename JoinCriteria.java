package com.systech.Squiggle;

import com.systech.Squiggle.output.Output;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 */
public class JoinCriteria extends Criteria {

    private Column source, dest;

    public JoinCriteria(Column source, Column dest) {
        this.source = source;
        this.dest = dest;
    }

    public Column getSource() {
        return source;
    }

    public Column getDest() {
        return dest;
    }

    public void write(Output out) {
        out.print(source)
            .print(" = ")
            .print(dest);
    }

}
