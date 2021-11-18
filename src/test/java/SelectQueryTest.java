
import static org.junit.Assert.*;

import org.junit.Test;

import com.squiggle.Squiggle;
import com.squiggle.queries.SelectQuery;

public class SelectQueryTest {

    @Test
    public void noTableError() {
        SelectQuery select = Squiggle.Select();
        Exception thrown = assertThrows(IllegalStateException.class, () -> select.toString());
        assertTrue(thrown.getMessage().contains("Cannot make query without table"));
    }

    @Test
    public void noSelectError() {
        SelectQuery select = Squiggle.Select().from("table");
        Exception thrown = assertThrows(IllegalStateException.class, () -> select.toString());
        assertTrue(thrown.getMessage().contains("Cannot make query without related column"));
    }

    @Test
    public void rightNumberOfColumns() {
        SelectQuery select = Squiggle.Select().from("table").select("column");
        assertEquals(select.listColumns().size(), 1);
        assertEquals(select.listColumns().get(0).toString(), "table.column");
    }

    @Test
    public void correctSelectFromQuery() {
        SelectQuery select = Squiggle.Select().from("table").select("column");
        assertEquals(select.toString(true), "SELECT table.column FROM table");
    }

}
