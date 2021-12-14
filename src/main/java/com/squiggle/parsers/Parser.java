package com.squiggle.parsers;

import com.squiggle.output.Output;
import com.squiggle.queries.InsertQuery;
import com.squiggle.queries.SelectQuery;

public abstract class Parser {

    public Parser() {

    }

    public abstract void selectQuery(SelectQuery selectQuery, Output out);

    public abstract void insertQuery(InsertQuery insertQuery, Output out);

}
