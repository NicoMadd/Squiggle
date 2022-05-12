package SQLServer.QueryTests.JoinQueryTests.LeftJoins;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.SelectQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LeftJoinQueryTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new SqlServerParser());
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
        public void leftJoinWithInnerJoin() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .leftJoin("column1", "table2", "column2")
                                .innerJoin("column2", "table3", "column3")
                                .select("column3");
                assertEquals(
                                "SELECT table1.column1, table3.column3 FROM table1 LEFT JOIN table2 ON table1.column1 = table2.column2 INNER JOIN table3 ON table2.column2 = table3.column3",
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
        public void leftJoinTwoEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").leftJoin("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B"));

                assertEquals("SELECT table1.column1 FROM table1 LEFT JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B",
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

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
