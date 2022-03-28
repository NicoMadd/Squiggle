package QueryTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import com.squiggle.Squiggle;
import com.squiggle.exceptions.NoColumnsException;
import com.squiggle.exceptions.NoTableException;
import com.squiggle.queries.SelectQuery;

public class SelectQueryTest {

    @Test
    public void noTableError() {
        SelectQuery select = Squiggle.Select();
        Exception thrown = assertThrows(NoTableException.class, () -> select.toString());
        assertTrue(thrown.getMessage().contains("Cannot select column without table"));
    }

    @Test
    public void noSelectError() {
        SelectQuery select = Squiggle.Select().from("table");
        Exception thrown = assertThrows(NoColumnsException.class, () -> select.toString());
        assertTrue(thrown.getMessage().contains("Cannot make query without related column"));
    }

    @Test
    public void rightNumberOfColumns() {
        SelectQuery select = Squiggle.Select().from("table").select("column");
        assertEquals(select.listColumns().size(), 1);
        assertEquals(select.listColumns().get(0).toString(), "table.column");
    }

    @Test
    public void oneTableOneColumnSelect() {
        SelectQuery select = Squiggle.Select().from("table").select("column");
        assertEquals("SELECT table.column FROM table", select.toString());
    }

    @Test
    public void oneTableTwoColumnsSelect() {
        SelectQuery select = Squiggle.Select().from("table").select("column1").select("column2");
        assertEquals("SELECT table.column1, table.column2 FROM table", select.toString());
    }

    @Test
    public void twoTablesOneColumnSelect() {
        SelectQuery select = Squiggle.Select().from("table1").select("column1").from("table2").select("column2");
        assertEquals("SELECT table1.column1, table2.column2 FROM table1, table2", select.toString());
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
    public void simpleSelectColumnAs() {
        SelectQuery select = Squiggle.Select().from("table").select("column", "alias");
        assertEquals("SELECT table.column AS alias FROM table", select.toString());
    }

    @Test
    public void complexSelectColumnAs() {
        SelectQuery select = Squiggle.Select().from("table").select("column", "alias").select("column2", "alias2")
                .select("column3", "alias3");
        assertEquals("SELECT table.column AS alias, table.column2 AS alias2, table.column3 AS alias3 FROM table",
                select.toString());
    }

    @Test
    public void simpleStringWhere() {
        SelectQuery select = Squiggle.Select().from("table").select("*").where("column", c -> c.equals("value"));
        assertEquals("SELECT table.* FROM table WHERE table.column = 'value'", select.toString());
    }

    @Test
    public void simpleIntWhere() {
        SelectQuery select = Squiggle.Select().from("table").select("*").where("column", c -> c.equals(1));
        assertEquals("SELECT table.* FROM table WHERE table.column = 1", select.toString());
    }

    @Test
    public void multiWhereColumn() {
        SelectQuery select = Squiggle.Select().from("table").select("*").where("column1", c -> c.equals(1))
                .where("column2", c -> c.equals(2));
        assertEquals("SELECT table.* FROM table WHERE table.column1 = 1 AND table.column2 = 2", select.toString());
    }

    @Test
    public void selectWithColumnWithSpaces() {
        SelectQuery select = Squiggle.Select().from("table").select("Column 1", "alias");
        assertEquals("SELECT table.\"Column 1\" AS alias FROM table", select.toString());
    }
}
