
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.queries.CreateTableQuery;

import org.junit.jupiter.api.Test;

public class CreateTableConstraintTest {

    @Test
    public void defineSimplePrimaryKey() {
        CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                .column("column1").varchar().pk().define();
        assertEquals(
                "CREATE TABLE table (column1 varchar(255) PRIMARY KEY)",
                createTableQuery.toString());
    }

    @Test
    public void defineNullableColumn() {
        CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                .column("column1").varchar().nullable().define();
        assertEquals(
                "CREATE TABLE table (column1 varchar(255) NULL)",
                createTableQuery.toString());
    }

    @Test
    public void defineNotNullableColumn() {
        CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                .column("column1").varchar().notNullable().define();
        assertEquals(
                "CREATE TABLE table (column1 varchar(255) NOT NULL)",
                createTableQuery.toString());
    }

    @Test
    public void defineUniqueColumn() {
        CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                .column("column1").varchar().unique().define();
        assertEquals(
                "CREATE TABLE table (column1 varchar(255) UNIQUE)",
                createTableQuery.toString());
    }
}
