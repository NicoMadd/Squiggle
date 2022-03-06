package QueryTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import com.squiggle.Squiggle;
import com.squiggle.queries.SelectQuery;

public class GroupBySelectQueryTest {

    @Test
    public void simpleGroupBy() {
        SelectQuery select = Squiggle.Select().from("table").select("*").groupBy("column1");
        assertEquals("SELECT table.* FROM table GROUP BY table.column1", select.toString());
    }

}
