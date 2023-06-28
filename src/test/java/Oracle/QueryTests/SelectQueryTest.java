package Oracle.QueryTests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import com.squiggle.Squiggle;
import com.squiggle.exceptions.NoColumnsException;
import com.squiggle.exceptions.NoTableException;
import com.squiggle.parsers.OrderByNeededException;
import com.squiggle.parsers.OracleParser;
import com.squiggle.queries.SelectQuery;
import com.squiggle.types.values.BooleanTypeValue;

public class SelectQueryTest {

    @BeforeAll
    public static void setUp() {
        Squiggle.setParser(new OracleParser());
    }

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
                "SELECT table1.column1, table2.column2 FROM table1 INNER JOIN table2 ON table1.column1 = table2.column2",
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
        SelectQuery select = Squiggle.Select().from("table").select("*")
                .where("column", c -> c.equals("value"));
        assertEquals("SELECT table.* FROM table WHERE table.column = 'value'", select.toString());
    }

    @Test
    public void simpleIntWhere() {
        SelectQuery select = Squiggle.Select().from("table").select("*")
                .where("column", c -> c.equals(1));
        assertEquals("SELECT table.* FROM table WHERE table.column = 1", select.toString());
    }

    @Test
    public void simpleBooleanWhereAsInt() {
        BooleanTypeValue.asInt();
        SelectQuery select = Squiggle.Select().from("table").select("*")
                .where("column", c -> c.equals(true));
        assertEquals("SELECT table.* FROM table WHERE table.column = 1", select.toString());
    }

    @Test
    public void simpleBooleanWhereAsText() {
        BooleanTypeValue.asText();
        SelectQuery select = Squiggle.Select().from("table").select("*")
                .where("column", c -> c.equals(true));
        assertEquals("SELECT table.* FROM table WHERE table.column = true", select.toString());
    }

    @Test
    public void multiWhereColumn() {
        SelectQuery select = Squiggle.Select().from("table").select("*").where("column1", c -> c.equals(1))
                .where("column2", c -> c.equals(2));
        assertEquals("SELECT table.* FROM table WHERE table.column1 = 1 AND table.column2 = 2", select.toString());
    }

    @Test
    public void whereIsNullValue() {
        SelectQuery select = Squiggle.Select().from("table").select("*")
                .where("column", c -> c.isNull());
        assertEquals("SELECT table.* FROM table WHERE table.column IS NULL", select.toString());
    }

    @Test
    public void whereIsNotNullValue() {
        SelectQuery select = Squiggle.Select().from("table").select("*")
                .where("column", c -> c.isNotNull());
        assertEquals("SELECT table.* FROM table WHERE table.column IS NOT NULL", select.toString());
    }

    @Test
    public void selectWithColumnWithSpaces() {
        SelectQuery select = Squiggle.Select().from("table").select("Column 1", "alias");
        assertEquals("SELECT table.\"Column 1\" AS alias FROM table", select.toString());
    }

    @Test
    public void selectWithLimit() {
        Integer limit = new Random().nextInt(100);
        SelectQuery select = Squiggle.Select().from("table").select("*").limit(limit);
        assertEquals("SELECT table.* FROM table LIMIT " + limit, select.toString());
    }

    @Test
    public void selectWithOffsetError() {
        Integer offset = new Random().nextInt(100);
        SelectQuery select = Squiggle.Select().from("table").select("*");
        Exception thrown = assertThrows(OrderByNeededException.class, () -> select.offset(offset).toString());
        assertTrue(thrown.getMessage().contains("Offset is only allowed with order by"));
    }

    @Test
    public void selectWithOffset() {
        Integer offset = new Random().nextInt(100);
        SelectQuery select = Squiggle.Select().from("table").select("*").order("col1", true).offset(offset);
        assertEquals("SELECT table.* FROM table ORDER BY table.col1 OFFSET " + offset, select.toString());
    }

    @Test
    public void selectWithLimitAndOffset() {
        Integer limit = new Random().nextInt(10000);
        Integer offset = new Random().nextInt(10000);
        SelectQuery select = Squiggle.Select().from("table").select("*").order("col1").limit(limit).offset(offset);
        assertEquals(
                "SELECT table.* FROM table ORDER BY table.col1 OFFSET " + offset + " ROWS FETCH NEXT " + limit
                        + " ROWS ONLY",
                select.toString());
    }

    @Test
    public void selectWithOneAscOrder() {
        SelectQuery select = Squiggle.Select().from("table").select("*").order("col1", true);
        assertEquals("SELECT table.* FROM table ORDER BY table.col1", select.toString());
    }

    @Test
    public void selectWithOneDescOrder() {
        SelectQuery select = Squiggle.Select().from("table").select("*").order("col1", false);
        assertEquals("SELECT table.* FROM table ORDER BY table.col1 DESC", select.toString());
    }

    @Test
    public void selectWithTwoOrders() {
        SelectQuery select = Squiggle.Select().from("table").select("*").order("col1", true).order("col2", false);
        assertEquals("SELECT table.* FROM table ORDER BY table.col1, table.col2 DESC", select.toString());
    }

    @Test
    public void selectWithOrderWithIndexError() {
        String query = Squiggle.Select().from("table").select("column")
                .order(1).order(2, false).order("col3", false)
                .toString();
        assertEquals(query, "SELECT table.column FROM table ORDER BY 1, 2 DESC, table.col3 DESC");
    }

    @Test
    public void selectWithOrderZeroIndexError() {
        SelectQuery select = Squiggle.Select().from("table").select("column").select("column2");
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> select.order(0));
        assertTrue(throwable.getMessage().contains("Index must be greater than 0"));

    }

    @Test
    public void selectWithOrderWithIndex() {
        SelectQuery select = Squiggle.Select().from("table").select("column").select("column2").order(1);
        assertEquals("SELECT table.column, table.column2 FROM table ORDER BY 1", select.toString());
    }

    @AfterAll
    public static void tearDown() {
        Squiggle.setParser(null);
    }
}
