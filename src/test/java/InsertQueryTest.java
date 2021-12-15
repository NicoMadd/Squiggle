
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.squiggle.Squiggle;
import com.squiggle.queries.InsertQuery;

public class InsertQueryTest {

        @Test
        public void noTableError() {
                InsertQuery insert = Squiggle.Insert();
                Exception thrown = assertThrows(IllegalStateException.class, () -> insert.toString());
                assertTrue(thrown.getMessage().contains("Cannot make query without table"));
        }

        @Test
        public void happyCase() {
                Squiggle.Insert().into("table").to("column").value("value");
                Squiggle.Insert().into("table").to("column1").to("column2").value("value1").value("value2").value(2)
                                .value(3.0f);
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

}
