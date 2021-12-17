
import static org.junit.Assert.*;

import org.junit.Test;

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

}
