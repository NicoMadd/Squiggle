package com.squiggle.queries.TableQueries;

import com.squiggle.base.Table;
import com.squiggle.interfaces.Validatable;
import com.squiggle.output.Output;
import com.squiggle.output.ToStringer;
import com.squiggle.parsers.Parserable;

public class TableQuery extends Parserable implements Validatable {

    protected Table baseTable;

    public TableQuery(String tableName) {
        super();
        this.baseTable = new Table(tableName);
    }

    @Override
    public void validate() {
        if (this.baseTable.getName() == null) {
            throw new IllegalArgumentException("Base table cannot be null");
        }
    }

    public String toString() {
        validate();
        return ToStringer.toString(this);
    }

    @Override
    public void write(Output out) {
        // TODO Auto-generated method stub

    }

}
