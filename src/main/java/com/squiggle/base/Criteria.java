package com.squiggle.base;

import com.squiggle.output.Output;
import com.squiggle.output.ToStringer;
import com.squiggle.parsers.Parserable;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 */
public abstract class Criteria extends Parserable {

    public abstract void write(Output out);

    public String toString() {
        return ToStringer.toString(this);
    }

    protected String quote(String s) {
        if (s == null)
            return "null";
        StringBuffer str = new StringBuffer();
        str.append('\'');
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\\' || s.charAt(i) == '\"' || s.charAt(i) == '\'') {
                str.append('\\');
            }
            str.append(s.charAt(i));
        }
        str.append('\'');
        return str.toString();
    }

}
