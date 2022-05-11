package MySQL.QueryTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import com.squiggle.Squiggle;
import com.squiggle.parsers.MySQLParser;
import com.squiggle.queries.SelectQuery;

public class GroupBySelectQueryTest {

    @BeforeAll
    public static void setUp() {
        Squiggle.setParser(new MySQLParser());
    }

    @Test
    public void simpleGroupBy() {
        SelectQuery select = Squiggle.Select().from("table").select("*").groupBy("column1");
        assertEquals("SELECT table.* FROM table GROUP BY table.column1", select.toString());
    }

    @AfterAll
    public static void tearDown() {
        Squiggle.setParser(null);
    }
}
