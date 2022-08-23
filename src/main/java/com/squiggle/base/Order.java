package com.squiggle.base;

import com.squiggle.output.Output;
import com.squiggle.output.ToStringer;
import com.squiggle.parsers.Parserable;

/**
 * ORDER BY clause. See SelectQuery.addOrder(Order).
 * 
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author <a href="https://github.com/NicoMadd">Nicolas Madeo</a>
 */
public class Order extends Parserable {

    public static final boolean ASCENDING = true;
    public static final boolean DESCENDING = false;

    private Column column;
    private Integer index;
    private boolean ascending;

    /**
     * @param column    Column to order by.
     * @param ascending Order.ASCENDING or Order.DESCENDING
     */
    public Order(Column column, boolean ascending) {
        this.column = column;
        this.ascending = ascending;
    }

    public Order(Integer column, boolean ascending) {
        if (column <= 0)
            throw new IllegalArgumentException("Index must be greater than 0");
        this.index = column;
        this.ascending = ascending;
    }

    public Column getColumn() {
        return column;
    }

    public String toString() {
        return ToStringer.toString(this);
    }

    public void write(Output out) {
        if (this.column != null)
            column.write(out);
        else
            out.print(this.index);
        if (!ascending) {
            out.print(" DESC");
        }
    }

}
