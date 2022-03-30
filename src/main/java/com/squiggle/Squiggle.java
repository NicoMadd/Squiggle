package com.squiggle;

import com.squiggle.parsers.Parser;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.*;
import com.squiggle.queries.TableQueries.CreateTableQuery;
import com.squiggle.queries.TableQueries.DropTableQuery;

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

    public static DropTableQuery DropTable(String tableName) {
        return new DropTableQuery(tableName);
    }

    public static CreateDatabaseQuery CreateDatabase(String databaseName) {
        return new CreateDatabaseQuery(databaseName);
    }

    public static DropDatabaseQuery DropDatabase(String databaseName) {
        return new DropDatabaseQuery(databaseName);
    }

    public static TransactionQuery Transaction() {
        return new TransactionQuery();
    }

    public static TryCatchQuery tryCatchQuery() {
        return new TryCatchQuery();
    }

    public static void setParser(Parser parser) {
        Squiggle.parser = parser;
    }

    public static Parser getParser() {
        return Squiggle.parser;
    }

}
