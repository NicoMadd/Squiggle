package com.systech.Squiggle;

import com.systech.Squiggle.output.Outputable;
import com.systech.Squiggle.output.Output;
import com.systech.Squiggle.output.ToStringer;

/**
 * ORDER BY clause. See SelectQuery.addOrder(Order).
 * 
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 */
public class Order implements Outputable {

    public static final boolean ASCENDING = true;
    public static final boolean DESCENDING = false;

    private Column column;
    private boolean ascending;

    /**
     * @param column    Column to order by.
     * @param ascending Order.ASCENDING or Order.DESCENDING
     */
    public Order(Column column, boolean ascending) {
        this.column = column;
        this.ascending = ascending;
    }

    public Column getColumn() {
        return column;
    }

    public String toString() {
        return ToStringer.toString(this);
    }

    public void write(Output out) {
        column.write(out);
        if (!ascending) {
            out.print(" DESC");
        }
    }

}
