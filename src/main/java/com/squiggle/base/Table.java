package com.squiggle.base;

import com.squiggle.output.Output;
import com.squiggle.output.Outputable;
import com.squiggle.output.ToStringer;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 */
public class Table implements Outputable {

    private String name;
    private String alias;

    public Table(String name) {
        this.name = name;
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
