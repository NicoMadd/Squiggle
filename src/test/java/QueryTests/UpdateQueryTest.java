package QueryTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.SplittableRandom;

import com.squiggle.Squiggle;
import com.squiggle.exceptions.NoColumnsException;
import com.squiggle.exceptions.NoTableException;
import com.squiggle.queries.UpdateQuery;

public class UpdateQueryTest {

    SplittableRandom sr = new SplittableRandom().split();

    @Test
    public void noTableError() {
        UpdateQuery update = Squiggle.Update();
        Exception thrown = assertThrows(NoTableException.class, () -> update.toString());
        assertTrue(thrown.getMessage().contains("Cannot make query without table"));
    }

    @Test
    public void noColumnError() {
        UpdateQuery update = Squiggle.Update().table("table");
        Exception thrown = assertThrows(NoColumnsException.class, () -> update.toString());
        assertTrue(thrown.getMessage().contains("Cannot make query without related column"));
    }

    @Test
    public void updateOneColumnQuery() {
        UpdateQuery update = Squiggle.Update().table("table").set("column").to("value");
        assertEquals("UPDATE table SET table.column='value'", update.toString());
    }

    @Test
    public void updateTwoColumnQuery() {
        Integer value = sr.nextInt(0, 100000);
        Integer value2 = sr.nextInt(0, 100000);
        Integer value3 = sr.nextInt(0, 100000);
        UpdateQuery update = Squiggle.Update().table("table")
                .set("column").to("value")
                .set("column2").to(value)
                .set("column3").to(value2)
                .set("column4").to(value3);
        assertEquals("UPDATE table SET table.column='value', table.column2=" + value + ", table.column3="
                + value2 + ", table.column4=" + value3, update.toString());
    }

    @Test
    public void updateWhereQuery() {
        Integer value = sr.nextInt(0, 100000);
        Integer value2 = sr.nextInt(0, 100000);
        Integer value3 = sr.nextInt(0, 100000);
        Integer value4 = sr.nextInt(0, 100000);
        UpdateQuery update = Squiggle.Update().table("table")
                .set("column").to("value")
                .set("column2").to(value)
                .set("column3").to(value2)
                .set("column4").to(value3)
                .where("whereColumn", c -> c.equals(value4));
        assertEquals("UPDATE table SET table.column='value', table.column2=" + value + ", table.column3="
                + value2 + ", table.column4=" + value3 + " WHERE table.whereColumn = " + value4, update.toString());
    }

    @Test
    public void updateWithColumnWithSpaces() {
        UpdateQuery update = Squiggle.Update().table("table").set("column with spaces").to("value");
        assertEquals("UPDATE table SET table.\"column with spaces\"='value'", update.toString());
    }

}
