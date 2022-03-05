package com.squiggle.parsers;

import com.squiggle.constraints.ForeignKey;
import com.squiggle.constraints.NotNullable;
import com.squiggle.constraints.Nullable;
import com.squiggle.constraints.PrimaryKey;
import com.squiggle.constraints.Unique;
import com.squiggle.output.Output;
import com.squiggle.queries.CreateTableQuery;
import com.squiggle.queries.DeleteQuery;
import com.squiggle.queries.InsertQuery;
import com.squiggle.queries.SelectQuery;
import com.squiggle.queries.UpdateQuery;

public abstract class Parser {

    public Parser() {

    }

    public abstract void selectQuery(SelectQuery selectQuery, Output out);

    public abstract void insertQuery(InsertQuery insertQuery, Output out);

    public abstract void updateQuery(UpdateQuery updateQuery, Output out);

    public abstract void deleteQuery(Output out, DeleteQuery deleteQuery);

    public abstract void createTableQuery(Output out, CreateTableQuery createTableQuery);

    public abstract void foreignKey(Output out, ForeignKey foreignKey);

    public abstract void notNullable(Output out, NotNullable notNullable);

    public abstract void nullable(Output out, Nullable nullable);

    public abstract void primaryKey(Output out, PrimaryKey primaryKey);

    public abstract void unique(Output out, Unique unique);

}
