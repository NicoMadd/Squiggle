package SQLServer.QueryTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import com.squiggle.Squiggle;
import com.squiggle.exceptions.NoColumnsException;
import com.squiggle.exceptions.NoTableException;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.SelectQuery;

public class JoinsQueryTest {

    @BeforeAll
    public static void setUp() {
        Squiggle.setParser(new SqlServerParser());
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
        SelectQuery select = Squiggle.Select().from("table1").select("column1").join("column1", "table2", "column2")
                .select("column2");
        assertEquals(
                "SELECT table1.column1, table2.column2 FROM table1 JOIN table2 ON table1.column1 = table2.column2",
                select.toString());
    }

    @Test
    public void simpleLeftJoin() {
        SelectQuery select = Squiggle.Select().from("table1").select("column1").leftJoin("column1", "table2", "column2")
                .select("column2");
        assertEquals(
                "SELECT table1.column1, table2.column2 FROM table1 LEFT JOIN table2 ON table1.column1 = table2.column2",
                select.toString());
    }

    @Test
    public void simpleRightJoin() {
        SelectQuery select = Squiggle.Select().from("table1").select("column1")
                .rightJoin("column1", "table2", "column2")
                .select("column2");
        assertEquals(
                "SELECT table1.column1, table2.column2 FROM table1 RIGHT JOIN table2 ON table1.column1 = table2.column2",
                select.toString());
    }

    @Test
    public void simpleFullJoin() {
        SelectQuery select = Squiggle.Select().from("table1").select("column1")
                .fullJoin("column1", "table2", "column2")
                .select("column2");
        assertEquals(
                "SELECT table1.column1, table2.column2 FROM table1 FULL JOIN table2 ON table1.column1 = table2.column2",
                select.toString());
    }

    @Test
    public void simpleOuterJoin() {
        SelectQuery select = Squiggle.Select().from("table1").select("column1")
                .outerJoin("column1", "table2", "column2")
                .select("column2");
        assertEquals(
                "SELECT table1.column1, table2.column2 FROM table1 OUTER JOIN table2 ON table1.column1 = table2.column2",
                select.toString());
    }

    @Test
    public void leftJoinWithRightJoin() {
        SelectQuery select = Squiggle.Select().from("table1").select("column1")
                .leftJoin("column1", "table2", "column2")
                .rightJoin("column1", "table3", "column3")
                .select("column");
        assertEquals(
                "SELECT table1.column1, table2.column2, table3.column3 FROM table1 LEFT JOIN table2 ON table1.column1 = table2.column2 RIGHT JOIN table3 ON table1.column1 = table3.column3",
                select.toString());
    }

    @AfterAll
    public static void tearDown() {
        Squiggle.setParser(null);
    }
}
