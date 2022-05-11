package SQLServer.QueryTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import com.squiggle.Squiggle;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.SelectQuery;

public class AggregatedSelectQueryTest {

    @BeforeAll
    public static void setUp() {
        Squiggle.setParser(new SqlServerParser());
    }

    @Test
    public void simpleSumQuery() {
        SelectQuery select = Squiggle.Select().from("table").sum("column1");
        assertEquals("SELECT SUM(table.column1) FROM table", select.toString());
    }

    @Test
    public void simpleSumQueryAsSum() {
        SelectQuery select = Squiggle.Select().from("table").sum("column1", "sum");
        assertEquals("SELECT SUM(table.column1) AS sum FROM table", select.toString());
    }

    @Test
    public void simpleAverageQuery() {
        SelectQuery select = Squiggle.Select().from("table").average("column1");
        assertEquals("SELECT AVG(table.column1) FROM table", select.toString());
    }

    @Test
    public void simpleAverageQueryAsAverage() {
        SelectQuery select = Squiggle.Select().from("table").average("column1", "average");
        assertEquals("SELECT AVG(table.column1) AS average FROM table", select.toString());
    }

    @Test
    public void simpleAverageQuerySugar() {
        SelectQuery select = Squiggle.Select().from("table").avg("column1");
        assertEquals("SELECT AVG(table.column1) FROM table", select.toString());
    }

    @Test
    public void compoundSumAndAvgAggregatedQuery() {
        SelectQuery select = Squiggle.Select().from("table").sum("column1").avg("column2");
        assertEquals("SELECT SUM(table.column1), AVG(table.column2) FROM table", select.toString());
    }

    @Test
    public void compoundSumAndAvgAggregatedQueryAsSumAndAverage() {
        SelectQuery select = Squiggle.Select().from("table").sum("column1", "Sum").avg("column2", "Average");
        assertEquals("SELECT SUM(table.column1) AS Sum, AVG(table.column2) AS Average FROM table", select.toString());
    }

    @Test
    public void simpleCountQuery() {
        SelectQuery select = Squiggle.Select().from("table").count("column1");
        assertEquals("SELECT COUNT(table.column1) FROM table", select.toString());
    }

    @Test
    public void simpleCountAsCountQuery() {
        SelectQuery select = Squiggle.Select().from("table").count("column1", "count");
        assertEquals("SELECT COUNT(table.column1) AS count FROM table", select.toString());
    }

    @Test
    public void compoundSumAndAvgAndCountAggregatedQuery() {
        SelectQuery select = Squiggle.Select().from("test").sum("column1").avg("column1").count("column1");
        assertEquals("SELECT SUM(test.column1), AVG(test.column1), COUNT(test.column1) FROM test",
                select.toString());
    }

    @Test
    public void compoundSumAndAvgAndCountAggregatedQueryAsSumAverageAndCount() {
        SelectQuery select = Squiggle.Select().from("test").sum("column1", "Sum").avg("column1", "Average")
                .count("column1", "Count");
        assertEquals(
                "SELECT SUM(test.column1) AS Sum, AVG(test.column1) AS Average, COUNT(test.column1) AS Count FROM test",
                select.toString());
    }

    @AfterAll
    public static void tearDown() {
        Squiggle.setParser(null);
    }

}
