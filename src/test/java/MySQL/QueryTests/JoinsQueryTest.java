package MySQL.QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.parsers.MySQLParser;
import com.squiggle.queries.SelectQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JoinsQueryTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new MySQLParser());
        }

        @Test
        public void twoJoinedTablesOneColumnWithWhereSelect() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1").from("table2")
                                .where("column1", c -> c.link("table1", "table2", "column2"))
                                .select("column2");
                assertEquals("SELECT table1.column1, table2.column2 FROM table1, table2 WHERE table1.column1 = table2.column2",
                                select.toString());
        }

        @Test
        public void twoJoinedTablesOneColumnSelect() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .join("column1", "table2", "column2")
                                .select("column2");
                assertEquals(
                                "SELECT table1.column1, table2.column2 FROM table1 INNER JOIN table2 ON table1.column1 = table2.column2",
                                select.toString());
        }

        @Test
        public void threeJoinedTablesTwoColumnSelect() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .join("column1", "table2", "column2")
                                .join("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 INNER JOIN table2 ON table1.column1 = table2.column2 INNER JOIN table3 ON table2.column2 = table3.column3",
                                select.toString());
        }

        @Test
        public void simpleLeftJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .leftJoin("column1", "table2", "column2");
                assertEquals(
                                "SELECT table1.column1 FROM table1 LEFT JOIN table2 ON table1.column1 = table2.column2",
                                select.toString());
        }

        @Test
        public void simpleRightJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .rightJoin("column1", "table2", "column2");
                assertEquals(
                                "SELECT table1.column1 FROM table1 RIGHT JOIN table2 ON table1.column1 = table2.column2",
                                select.toString());
        }

        @Test
        public void simpleFullJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .fullJoin("column1", "table2", "column2");
                assertEquals(
                                "SELECT table1.column1 FROM table1 FULL JOIN table2 ON table1.column1 = table2.column2",
                                select.toString());
        }

        @Test
        public void simpleOuterJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .outerJoin("column1", "table2", "column2");
                assertEquals(
                                "SELECT table1.column1 FROM table1 OUTER JOIN table2 ON table1.column1 = table2.column2",
                                select.toString());
        }

        @Test
        public void leftJoinWithRightJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .leftJoin("column1", "table2", "column2")
                                .rightJoin("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 LEFT JOIN table2 ON table1.column1 = table2.column2 RIGHT JOIN table3 ON table2.column2 = table3.column3",
                                select.toString());
        }

        @Test
        public void leftJoinWithFullJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .leftJoin("column1", "table2", "column2")
                                .fullJoin("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 LEFT JOIN table2 ON table1.column1 = table2.column2 FULL JOIN table3 ON table2.column2 = table3.column3",
                                select.toString());
        }

        @Test
        public void leftJoinWithOuterJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .leftJoin("column1", "table2", "column2")
                                .outerJoin("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 LEFT JOIN table2 ON table1.column1 = table2.column2 OUTER JOIN table3 ON table2.column2 = table3.column3",
                                select.toString());
        }

        @Test
        public void rightJoinWithLeftJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .rightJoin("column1", "table2", "column2")
                                .leftJoin("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 RIGHT JOIN table2 ON table1.column1 = table2.column2 LEFT JOIN table3 ON table2.column2 = table3.column3",
                                select.toString());
        }

        @Test
        public void rightJoinWithFullJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .rightJoin("column1", "table2", "column2")
                                .fullJoin("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 RIGHT JOIN table2 ON table1.column1 = table2.column2 FULL JOIN table3 ON table2.column2 = table3.column3",
                                select.toString());
        }

        @Test
        public void rightJoinWithOuterJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .rightJoin("column1", "table2", "column2")
                                .outerJoin("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 RIGHT JOIN table2 ON table1.column1 = table2.column2 OUTER JOIN table3 ON table2.column2 = table3.column3",
                                select.toString());
        }

        @Test
        public void fullJoinWithLeftJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .fullJoin("column1", "table2", "column2")
                                .leftJoin("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 FULL JOIN table2 ON table1.column1 = table2.column2 LEFT JOIN table3 ON table2.column2 = table3.column3",
                                select.toString());
        }

        @Test
        public void fullJoinWithRightJoin() {

                SelectQuery select = Squiggle.Select().from("table1").select("column1")

                                .fullJoin("column1", "table2", "column2")
                                .rightJoin("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 FULL JOIN table2 ON table1.column1 = table2.column2 RIGHT JOIN table3 ON table2.column2 = table3.column3",
                                select.toString());
        }

        @Test
        public void fullJoinWithOuterJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .fullJoin("column1", "table2", "column2")
                                .outerJoin("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 FULL JOIN table2 ON table1.column1 = table2.column2 OUTER JOIN table3 ON table2.column2 = table3.column3",
                                select.toString());
        }

        @Test
        public void outerJoinWithLeftJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .outerJoin("column1", "table2", "column2")
                                .leftJoin("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 OUTER JOIN table2 ON table1.column1 = table2.column2 LEFT JOIN table3 ON table2.column2 = table3.column3",
                                select.toString());
        }

        @Test
        public void outerJoinWithRightJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .outerJoin("column1", "table2", "column2")
                                .rightJoin("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 OUTER JOIN table2 ON table1.column1 = table2.column2 RIGHT JOIN table3 ON table2.column2 = table3.column3",
                                select.toString());
        }

        @Test
        public void outerJoinWithFullJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .outerJoin("column1", "table2", "column2")
                                .fullJoin("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 OUTER JOIN table2 ON table1.column1 = table2.column2 FULL JOIN table3 ON table2.column2 = table3.column3",
                                select.toString());
        }

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
