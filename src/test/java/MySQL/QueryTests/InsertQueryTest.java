package MySQL.QueryTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import com.squiggle.Squiggle;
import com.squiggle.exceptions.NoTableException;
import com.squiggle.exceptions.NoValuesInsertedException;
import com.squiggle.parsers.MySQLParser;
import com.squiggle.queries.InsertQuery;
import com.squiggle.types.values.BooleanTypeValue;
import com.squiggle.types.values.FloatTypeValue;

public class InsertQueryTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new MySQLParser());
        }

        @BeforeEach
        public void setUpEach() {
                FloatTypeValue.defaultFormat();
                BooleanTypeValue.asText();
        }

        @Test
        public void noTableError() {
                InsertQuery insert = Squiggle.Insert();
                Exception thrown = assertThrows(NoTableException.class, () -> insert.toString());
                assertTrue(thrown.getMessage().contains("Cannot make query without table"));
        }

        @Test
        public void noValuesError() {
                InsertQuery insert = Squiggle.Insert().into("table");
                Exception thrown = assertThrows(NoValuesInsertedException.class, () -> insert.toString());
                assertTrue(thrown.getMessage().contains("Cannot make query without values"));
        }

        @Test
        public void twoValuesAndOneEndRow() {
                InsertQuery endRow = Squiggle.Insert().into("table").to("column1").to("column2").value("value1")
                                .value("value2").endRow();
                assertEquals("INSERT INTO table (column1, column2) VALUES ('value1', 'value2'), (NULL, NULL)",
                                endRow.toString());
        }

        @Test
        public void twoValuesAndTwoEndRows() {
                InsertQuery endRow = Squiggle.Insert().into("table").to("column1").to("column2").value("value1")
                                .value("value2").endRow().endRow();
                assertEquals("INSERT INTO table (column1, column2) VALUES ('value1', 'value2'), (NULL, NULL), (NULL, NULL)",
                                endRow.toString());
        }

        @Test
        public void twoValuesAndThreeEndRows() {
                InsertQuery endRow = Squiggle.Insert().into("table").to("column1").to("column2").value("value1")
                                .value("value2").endRow().endRow().endRow();
                assertEquals(
                                "INSERT INTO table (column1, column2) VALUES ('value1', 'value2'), (NULL, NULL), (NULL, NULL), (NULL, NULL)",
                                endRow.toString());
        }

        @Test
        public void threeEndRowsHalfComplete() {
                InsertQuery endRow = Squiggle.Insert().into("table").to("column1").to("column2").value("value1")
                                .endRow().value("value2").endRow().value("value3").endRow();
                assertEquals(
                                "INSERT INTO table (column1, column2) VALUES ('value1', NULL), ('value2', NULL), ('value3', NULL)",
                                endRow.toString());
        }

        @Test
        public void stringInsert() {
                InsertQuery insert = Squiggle.Insert().into("table").to("stringCol").value("value1");
                assertEquals(
                                "INSERT INTO table (stringCol) VALUES ('value1')",
                                insert.toString());
        }

        @Test
        public void dateInsert() {
                InsertQuery insert = Squiggle.Insert().into("table").to("dateCol").value(new Date());
                assertEquals(
                                "INSERT INTO table (dateCol) VALUES (" + new Date().toString() + ")",
                                insert.toString());
        }

        @Test
        public void floatInsert() {
                FloatTypeValue.setFormat("#.000000");
                InsertQuery insert = Squiggle.Insert().into("table").to("floatCol").value(3.0f);
                assertEquals(
                                "INSERT INTO table (floatCol) VALUES (3.000000)",
                                insert.toString());
        }

        @Test
        public void doubleInsert() {
                InsertQuery insert = Squiggle.Insert().into("table").to("doubleCol").value(3.0);
                assertEquals(
                                "INSERT INTO table (doubleCol) VALUES (3.0)",
                                insert.toString());
        }

        @Test
        public void boolInsert() {
                BooleanTypeValue.asInt();
                InsertQuery insert = Squiggle.Insert().into("table").to("boolCol").value(true);
                assertEquals(
                                "INSERT INTO table (boolCol) VALUES (1)",
                                insert.toString());
        }

        @Test
        public void insertWithColumnWithSpaces() {
                InsertQuery insert = Squiggle.Insert().into("table").to("column with spaces").value("value1");
                assertEquals(
                                "INSERT INTO table (\"column with spaces\") VALUES ('value1')",
                                insert.toString());
        }

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }

}
