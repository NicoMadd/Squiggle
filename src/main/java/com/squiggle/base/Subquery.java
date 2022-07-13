package com.squiggle.base;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author <a href="https://github.com/NicoMadd">Nicolas Madeo</a>
 */
public class Subquery extends Table {

    public Subquery(String name) {
        super("(" + name + ")", "t");
    }

    public Subquery(String name, String alias) {
        super("(" + name + ")", alias);
    }

}
