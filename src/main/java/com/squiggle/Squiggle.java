package com.squiggle;

import com.squiggle.parsers.Parser;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.*;

public class Squiggle {

    static Parser parser = new SqlServerParser();

    public static SelectQuery Select() {
        return new SelectQuery();
    }

    public static DeleteQuery Delete() {
        return new DeleteQuery();
    }

    public static UpdateQuery Update() {
        return new UpdateQuery();
    }

    public static InsertQuery Insert() {
        return new InsertQuery();
    }

    public static CreateTableQuery CreateTable(String tableName) {
        return new CreateTableQuery(tableName);
    }

    public static void setParser(Parser parser) {
        Squiggle.parser = parser;
    }

    public static Parser getParser() {
        return Squiggle.parser;
    }
}
