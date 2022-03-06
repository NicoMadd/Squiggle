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
    private boolean hasAlias() {
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
        out.print(getName());
        if (hasAlias()) {
            out.print(' ');
            out.print(getAlias());
        }
    }

    public String toString() {
        return ToStringer.toString(this);
    }

}
