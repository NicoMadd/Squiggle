
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.squiggle.Squiggle;
import com.squiggle.queries.InsertQuery;

public class InsertQueryTest {

    @Test
    public void noTableError() {
        InsertQuery insert = Squiggle.Insert();
        Exception thrown = assertThrows(IllegalStateException.class, () -> insert.toString());
        assertTrue(thrown.getMessage().contains("Cannot make query without table"));
    }

    @Test
    public void happyCase() {
        Squiggle.Insert().into("table").to("column").value("value");
        Squiggle.Insert().into("table").to("column1").to("column2").value("value1").value("value2").value(2)
                .value(3.0f);
    }

    @Test
    public void endRowTest() {
        InsertQuery endRow = Squiggle.Insert().into("table").to("column1").to("column2").value("value1")
                .value("value2").endRow();
        assertEquals("INSERT INTO table (column1, column2) VALUES ('value1'), (NULL, NULL), (NULL, NULL)",
                endRow.toString(true));
    }

}
