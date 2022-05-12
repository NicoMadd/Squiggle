package SQLServer.QueryTests.JoinQueryTests.InnerJoins;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.SelectQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InnerJoinQueryTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new SqlServerParser());
        }

        @Test
        @DisplayName("Inner Join with a Left Join using the same table before the ON condition on each JOIN")
        public void innerJoinWithLeftJoinSameTable() {
                SelectQuery select = Squiggle.Select().from("table1").select("column1")
                                .join("column1", join -> join.to("table2").on("column2"))
                                .leftJoin("column2", join -> join.to("table3").on("column3"));
                assertEquals("SELECT table1.column1 FROM table1 INNER JOIN table2 ON table1.column1 = table2.column2 LEFT JOIN table3 ON table1.column2 = table3.column3",
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
        public void fullJoinTwoEqualityCondition() {
                SelectQuery select = Squiggle.Select().from("table1")
                                .select("column1").fullJoin("column1A",
                                                join -> join.to("table2").on("column2A")
                                                                .and("column1B").on("column2B"));

                assertEquals("SELECT table1.column1 FROM table1 FULL JOIN table2 ON table1.column1A = table2.column2A AND table1.column1B = table2.column2B",
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

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
