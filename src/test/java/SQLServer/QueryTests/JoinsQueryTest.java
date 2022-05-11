package SQLServer.QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.SelectQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JoinsQueryTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new SqlServerParser());
        }

        @Test
        public void simpleJoinWithWhere() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1").from("table2")
                                .where("column1", c -> c.link("table1", "table2", "column2"))
                                .select("column2");
                assertEquals("SELECT table1.column1, table2.column2 FROM table1, table2 WHERE table1.column1 = table2.column2",
                                select.toString());
        }

        @Test
        public void simpleInnerJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .join("column1", "table2", "column2")
                                .select("column2");
                assertEquals(
                                "SELECT table1.column1, table2.column2 FROM table1 INNER JOIN table2 ON table1.column1 = table2.column2",
                                select.toString());
        }

        @Test
        public void threeJoinedTables() {
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

        @Test
        public void innerJoinConditionBuilder() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").join("column1", join -> join.to("table2").on("column2"));
                assertEquals("SELECT table1.column1 FROM table1 INNER JOIN table2 ON table1.column1 = table2.column2",
                                select.toString());

        }

        @Test
        public void commonInnerJoinTwoEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").join("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B"));

                assertEquals("SELECT table1.column1 FROM table1 INNER JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B",
                                select.toString());
        }

        @Test
        public void innerJoinTwoEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").innerJoin("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B"));

                assertEquals("SELECT table1.column1 FROM table1 INNER JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B",
                                select.toString());
        }

        @Test
        public void leftJoinTwoEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").leftJoin("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B"));

                assertEquals("SELECT table1.column1 FROM table1 LEFT JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B",
                                select.toString());
        }

        @Test
        public void rightJoinTwoEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").rightJoin("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B"));

                assertEquals("SELECT table1.column1 FROM table1 RIGHT JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B",
                                select.toString());
        }

        @Test
        public void fullJoinTwoEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").fullJoin("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B"));

                assertEquals("SELECT table1.column1 FROM table1 FULL JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B",
                                select.toString());
        }

        @Test
        public void outerJoinTwoEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").outerJoin("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B"));

                assertEquals("SELECT table1.column1 FROM table1 OUTER JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B",
                                select.toString());
        }

        @Test
        public void commonInnerJoinThreeEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").join("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B")
                                                                .and("column1C").on("column2C"));

                assertEquals("SELECT table1.column1 FROM table1 INNER JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B AND table1.column1C = table2.column2C",
                                select.toString());
        }

        @Test
        public void innerJoinThreeEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").innerJoin("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B")
                                                                .and("column1C").on("column2C"));

                assertEquals("SELECT table1.column1 FROM table1 INNER JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B AND table1.column1C = table2.column2C",
                                select.toString());
        }

        @Test
        public void leftJoinThreeEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").leftJoin("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B")
                                                                .and("column1C").on("column2C"));

                assertEquals("SELECT table1.column1 FROM table1 LEFT JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B AND table1.column1C = table2.column2C",
                                select.toString());
        }

        @Test
        public void rightJoinThreeEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").rightJoin("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B")
                                                                .and("column1C").on("column2C"));

                assertEquals("SELECT table1.column1 FROM table1 RIGHT JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B AND table1.column1C = table2.column2C",
                                select.toString());
        }

        @Test
        public void fullJoinThreeEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").fullJoin("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B")
                                                                .and("column1C").on("column2C"));

                assertEquals("SELECT table1.column1 FROM table1 FULL JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B AND table1.column1C = table2.column2C",
                                select.toString());
        }

        @Test
        public void outerJoinThreeEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").outerJoin("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B")
                                                                .and("column1C").on("column2C"));

                assertEquals("SELECT table1.column1 FROM table1 OUTER JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B AND table1.column1C = table2.column2C",
                                select.toString());
        }

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
