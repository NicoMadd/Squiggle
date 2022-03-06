package com.squiggle.queries.TableQueries;

import com.squiggle.base.Table;
import com.squiggle.output.Output;

public class DropTableQuery extends TableQuery {

    public DropTableQuery(String tableName) {
        super(tableName);
    }

    public Table getTable() {
        return baseTable;
    }

    @Override
    public void write(Output out) {
        validate();
        this.parser.dropTableQuery(out, this);
    }

    @Override
    public void validate() {
        if (this.baseTable.getName() == null) {
            throw new IllegalArgumentException("Cannot drop table without a name");
        }

    }

}
