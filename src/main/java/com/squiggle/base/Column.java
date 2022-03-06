package com.squiggle.base;

import com.squiggle.output.Output;
import com.squiggle.output.ToStringer;
import com.squiggle.parsers.Parserable;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author <a href="https://github.com/NicoMadd">Nicolas Madeo</a>
 */
public class Column extends Parserable {

    protected String name;
    protected Table table;
    protected Boolean writeWithTable;
    protected String alias;

    public Column(Table table, String name) {
        this.table = table;
        this.name = name;
        this.writeWithTable = true;
        this.alias = null;
    }

    public Column(Table table, String name, String alias) {
        this.table = table;
        this.name = name;
        this.writeWithTable = true;
        this.alias = alias;
    }

    public Column writeWithTable(Boolean writeWithTable) {
        this.writeWithTable = writeWithTable;
        return this;
    }

    public Table getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    public Boolean getWriteWithTable() {
        return writeWithTable;
    }

    public String toString() {
        return ToStringer.toString(this);
    }

    public void write(Output out) {
        this.parser.simpleColumn(out, this);

    }

    public Boolean hasAlias() {
        return alias != null;
    }

    public String getAlias() {
        return alias;
    }

}
