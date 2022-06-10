package SQLServer.QueryTests.JoinQueryTests.InnerJoins;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.SelectQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InnerJoinColumnTest {

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
        public void threeJoinedTablesWithAliases() {
                SelectQuery select = Squiggle.Select().from("table1", "tb1").select("column1")
                                .join("column1", join -> join.to("table2", "tb2").on("column2")).useJoinedTable()
                                .join("column2", join -> join.to("table3", "tb3").on("column3")).useJoinedTable()
                                .select("column3");
                assertEquals("SELECT tb1.column1, tb3.column3 FROM table1 tb1 INNER JOIN table2 tb2 ON tb1.column1 = tb2.column2 INNER JOIN table3 tb3 ON tb2.column2 = tb3.column3",
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
        public void innerJoinToTableWithAlias() {
                SelectQuery select = Squiggle.Select().from("tableA", "tba").select("columnA1")
                                .join("columnA1", join -> join.to("tableB", "tbb").on("columnB1"))
                                .useTable(0)
                                .join("columnA1", join -> join.to("tableC", "tbc").on("columnC1"));

                assertEquals("SELECT tba.columnA1 FROM tableA tba INNER JOIN tableB tbb ON tba.columnA1 = tbb.columnB1 INNER JOIN tableC tbc ON tba.columnA1 = tbc.columnC1",
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
        public void innerJoinFromOneTableToTwoDifferentTables() {
                SelectQuery select = Squiggle.Select().from("tableA").select("column1")
                                .innerJoin("columnA1", join -> join.to("tableB").on("columnB1"))
                                .useTable(0)
                                .join("columnA2", join -> join.to("tableC").on("columnC1"));

                assertEquals("SELECT tableA.column1 FROM tableA INNER JOIN tableB ON tableA.columnA1 = tableB.columnB1 INNER JOIN tableC ON tableA.columnA2 = tableC.columnC1",
                                select.toString());
        }

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
