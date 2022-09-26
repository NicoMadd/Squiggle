package SQLServer.QueryTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import com.squiggle.Squiggle;
import com.squiggle.base.Criteria;
import com.squiggle.exceptions.NoColumnsException;
import com.squiggle.exceptions.NoTableException;
import com.squiggle.parsers.OrderByNeededException;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.SelectQuery;
import com.squiggle.types.values.BooleanTypeValue;

public class WhereConditionTest {

    @BeforeAll
    public static void setUp() {
        Squiggle.setParser(new SqlServerParser());
    }

    @Test
    public void simpleAndCondition() {
        SelectQuery select = Squiggle.Select().from("table").select("column1")
                .where("column", col -> col.equals("value1"))
                .where("column", col -> col.equals("value2"));
        assertEquals("SELECT table.column1 FROM table WHERE table.column = 'value1' AND table.column = 'value2'",
                select.toString());
    }

    @Test
    public void simpleOrCondition() {
        SelectQuery select = Squiggle.Select().from("table").select("column1")
                .where("column", col -> col.equals("value1"))
                .or("column", col -> col.equals("value2"));
        assertEquals("SELECT table.column1 FROM table WHERE table.column = 'value1' OR table.column = 'value2'",
                select.toString());
    }

    @AfterAll
    public static void tearDown() {
        Squiggle.setParser(null);
    }
}
