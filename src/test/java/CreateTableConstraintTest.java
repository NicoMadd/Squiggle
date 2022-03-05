
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
        public void defineTwoColumnsAndOnePK() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                                .column("column1").varchar().pk().define()
                                .column("column2").varchar().define();
                assertEquals(
                                "CREATE TABLE table (column1 varchar(255) PRIMARY KEY, column2 varchar(255))",
                                createTableQuery.toString());
        }

        @Test
        public void compositePK() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                                .column("column1").varchar().pk().define()
                                .column("column2").varchar().pk().define();
                assertEquals(
                                "CREATE TABLE table (column1 varchar(255), column2 varchar(255), PRIMARY KEY (column1, column2))",
                                createTableQuery.toString());
        }

        @Test
        public void tripleCompositePK() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                                .column("column1").varchar(20).pk().define()
                                .column("column2").integer().pk().define()
                                .column("column3").date().pk().define();
                assertEquals(
                                "CREATE TABLE table (column1 varchar(20), column2 int, column3 date, PRIMARY KEY (column1, column2, column3))",
                                createTableQuery.toString());
        }

        @Test
        public void tripleCompositePKandOneFK() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                                .column("column1").varchar(20).pk().define()
                                .column("column2").integer().pk().define()
                                .column("column3").date().pk().define()
                                .column("column4").varchar().fk("table2", "foreignColumn").define();
                assertEquals(
                                "CREATE TABLE table (column1 varchar(20), column2 int, column3 date, column4 varchar(255) FOREIGN KEY REFERENCES table2(foreignColumn), PRIMARY KEY (column1, column2, column3))",
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

        @Test
        public void defineForeignKey() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                                .column("column1").varchar().fk("table2", "foreignColumn").define();
                assertEquals("CREATE TABLE table (column1 varchar(255) FOREIGN KEY REFERENCES table2(foreignColumn))",
                                createTableQuery.toString());
        }
}
