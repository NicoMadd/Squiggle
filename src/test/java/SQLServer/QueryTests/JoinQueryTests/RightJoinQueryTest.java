package SQLServer.QueryTests.JoinQueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.SelectQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RightJoinQueryTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new SqlServerParser());
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
        public void rightJoinTwoEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").rightJoin("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B"));

                assertEquals("SELECT table1.column1 FROM table1 RIGHT JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B",
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

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
