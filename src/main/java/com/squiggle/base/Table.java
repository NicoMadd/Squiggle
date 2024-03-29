package com.squiggle.base;

import com.squiggle.functions.SQLFunction;
import com.squiggle.output.Output;
import com.squiggle.output.ToStringer;
import com.squiggle.parsers.Parserable;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author <a href="https://github.com/NicoMadd">Nicolas Madeo</a>
 */
public class Table extends Parserable {

    private String name;
    private String alias;

    public Table(String name) {
        this.name = name;
        this.alias = null;
    }

    public Table(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    /**
     * Name of table
     */
    public String getName() {
        return name;
    }

    /**
     * Whether this table has an alias assigned.
     */
    public boolean hasAlias() {
        return alias != null;
    }

    /**
     * Short alias of table
     */
    public String getAlias() {
        return hasAlias() ? alias : name;
    }

    /**
     * Get a column for a particular table.
     */
    public Column getColumn(String columnName) {
        return new Column(this, columnName);
    }

    public Column getColumn(String columnName, String alias) {
        return new Column(this, columnName, alias);
    }

    public Column getAggregatedColumn(String columname, SQLFunction function) {
        return new AggregatedColumn(this, columname, function);
    }

    public Column getAggregatedColumn(String columname, String alias, SQLFunction function) {
        return new AggregatedColumn(this, columname, alias, function);
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Table))
            return false;
        return getAlias().equals(((Table) o).getAlias());
    }

    public void write(Output out) {
        this.parser.table(out, this);
    }

    public String toString() {
        return ToStringer.toString(this);
    }

    public Boolean matches(String nameOrAlias) {
        return nameOrAlias.equals(getName()) || nameOrAlias.equals(getAlias());
    }

    public boolean needsQuotes() {
        return false;
    }

}
