package com.systech.Squiggle;

import com.systech.Squiggle.output.Outputable;
import com.systech.Squiggle.output.Output;
import com.systech.Squiggle.output.ToStringer;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 */
public class Column implements Outputable {

    private String name;
    private Table table;

    public Column(Table table, String name) {
        this.table = table;
        this.name = name;
    }

    public Table getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return ToStringer.toString(this);
    }

    public void write(Output out) {
        out.print(getTable().getAlias()).print('.').print(getName());
    }

}
