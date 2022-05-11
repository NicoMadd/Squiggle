package MySQL.QueryTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import com.squiggle.Squiggle;
import com.squiggle.base.MatchCriteria;
import com.squiggle.exceptions.NoTableException;
import com.squiggle.parsers.MySQLParser;
import com.squiggle.queries.DeleteQuery;

public class DeleteQueryTest {

    @BeforeAll
    public static void setUp() {
        Squiggle.setParser(new MySQLParser());
    }

    @Test
    public void noTableError() {
        DeleteQuery delete = Squiggle.Delete();
        Exception thrown = assertThrows(NoTableException.class, () -> delete.toString());
        assertTrue(thrown.getMessage().contains("Cannot make query without table"));
    }

    // @Test
    // public void noWhereClause() {
    // DeleteQuery delete = Squiggle.Delete().from("table");
    // Exception thrown = assertThrows(NoWhereClauseException.class, () ->
    // delete.toString());
    // assertTrue(thrown.getMessage().contains("Cannot make query without where
    // clause"));
    // }

    @Test
    public void simpleDelete() {
        DeleteQuery delete = Squiggle.Delete().from("table").where("column", column -> column.equals("value"));
        assertEquals("DELETE FROM table WHERE table.column = 'value'", delete.toString());
    }

    @Test
    public void multipleConditionDelete() {
        DeleteQuery delete = Squiggle.Delete().from("table").where("column1", column -> column.equals("value1"))
                .where("column2", column -> column.equals("value2"));
        assertEquals("DELETE FROM table WHERE table.column1 = 'value1' AND table.column2 = 'value2'",
                delete.toString());
    }

    @Test
    public void stressMultipleConditionDelete() {
        Date date = new Date();
        DeleteQuery delete = Squiggle.Delete().from("table").where("column1", column -> column.equals("value1"))
                .where("column2", column -> column.equals("value2"))
                .where("column3", column -> column.equals("value3"))
                .where("column4", column -> column.equals(23))
                .where("column5", column -> column.equals(4.5f))
                .where("column6", column -> column.equals(date));

        assertEquals(
                "DELETE FROM table WHERE table.column1 = 'value1' AND table.column2 = 'value2' AND table.column3 = 'value3' AND table.column4 = 23 AND table.column5 = 4.5 AND table.column6 = '"
                        + MatchCriteria.getDateFormat().format(date) + "'",
                delete.toString());
    }

    @Test
    public void deleteWithColumnWithSpaces() {
        DeleteQuery delete = Squiggle.Delete().from("table").where("column with spaces",
                column -> column.equals("value"));
        assertEquals("DELETE FROM table WHERE table.\"column with spaces\" = 'value'", delete.toString());
    }

    @AfterAll
    public static void tearDown() {
        Squiggle.setParser(null);
    }
}
