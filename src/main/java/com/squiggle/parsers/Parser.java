package com.squiggle.parsers;

import com.squiggle.output.Output;
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

}
