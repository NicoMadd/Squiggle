package com.squiggle.base;

import com.squiggle.output.Output;
import com.squiggle.output.Outputable;
import com.squiggle.output.ToStringer;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 */
public abstract class Criteria implements Outputable {

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
