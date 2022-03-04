
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.SplittableRandom;

import com.squiggle.Squiggle;
import com.squiggle.exceptions.NoColumnsException;
import com.squiggle.queries.CreateTableQuery;

import org.junit.jupiter.api.Test;

//TODO split this into multiple tests

public class CreateTableTest {

        SplittableRandom sr = new SplittableRandom().split();

        @Test
        public void noColumnsError() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table");
                Exception thrown = assertThrows(NoColumnsException.class, () -> createTableQuery.toString());
                assertTrue(thrown.getMessage().contains("Cannot create table without columns"));
        }

        @Test
        public void definesVarcharColumnDefault() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table").column("column1").varchar().define();
                assertEquals(
                                "CREATE TABLE table (column1 varchar(255))",
                                createTableQuery.toString());
        }

        @Test
        public void definesVarcharColumnNLength() {
                int n = sr.nextInt(1, 255);
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table").column("column1").varchar(n).define();
                assertEquals(
                                "CREATE TABLE table (column1 varchar(" + n + "))",
                                createTableQuery.toString());
        }

        @Test
        public void definesIntColumn() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table").column("column1").integer().define();
                assertEquals(
                                "CREATE TABLE table (column1 int)",
                                createTableQuery.toString());
        }

        @Test
        public void definesFloatColumn() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table").column("column1").floatingPoint()
                                .define();
                assertEquals(
                                "CREATE TABLE table (column1 float)",
                                createTableQuery.toString());
        }

        @Test
        public void definesDateColumn() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table").column("column1").date().define();
                assertEquals(
                                "CREATE TABLE table (column1 date)",
                                createTableQuery.toString());
        }

        @Test
        public void definesNIntegerColumns() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                                .column("column1").integer().define()
                                .column("column2").integer().define()
                                .column("column3").integer().define();
                assertEquals(
                                "CREATE TABLE table (column1 int, column2 int, column3 int)",
                                createTableQuery.toString());
        }

        @Test
        public void definesNDateColumns() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                                .column("column1").date().define()
                                .column("column2").date().define()
                                .column("column3").date().define();
                assertEquals(
                                "CREATE TABLE table (column1 date, column2 date, column3 date)",
                                createTableQuery.toString());
        }

        @Test
        public void definesNFloatColumns() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                                .column("column1").floatingPoint().define()
                                .column("column2").floatingPoint().define()
                                .column("column3").floatingPoint().define();
                assertEquals(
                                "CREATE TABLE table (column1 float, column2 float, column3 float)",
                                createTableQuery.toString());
        }

        @Test
        public void definesNVarcharColumns() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                                .column("column1").varchar().define()
                                .column("column2").varchar().define()
                                .column("column3").varchar().define();
                assertEquals(
                                "CREATE TABLE table (column1 varchar(255), column2 varchar(255), column3 varchar(255))",
                                createTableQuery.toString());
        }

        @Test
        public void definesNVarcharNColumns() {
                int n1 = sr.nextInt(1, 255);
                int n2 = sr.nextInt(1, 255);
                int n3 = sr.nextInt(1, 255);
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                                .column("column1").varchar(n1).define()
                                .column("column2").varchar(n2).define()
                                .column("column3").varchar(n3).define();
                assertEquals(
                                "CREATE TABLE table (column1 varchar(" + n1 + "), column2 varchar(" + n2
                                                + "), column3 varchar(" + n3 + "))",
                                createTableQuery.toString());
        }

        // TODO test table with type but no definition

        @Test
        public void tableColumnWithTypeButNoDefinition() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("table")
                                .column("column1").varchar();
                Exception thrown = assertThrows(NoColumnsException.class, () -> createTableQuery.toString());
                assertTrue(thrown.getMessage().contains("Cannot create table without columns"));
        }

}
