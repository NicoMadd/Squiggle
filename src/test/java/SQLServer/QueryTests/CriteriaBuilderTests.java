package SQLServer.QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.base.Column;
import com.squiggle.base.Table;
import com.squiggle.builders.CriteriaBuilder;
import com.squiggle.parsers.SqlServerParser;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CriteriaBuilderTests {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new SqlServerParser());
        }

        @Test
        public void testCriteriaBuilder() {
                String string = new CriteriaBuilder(new Column(new Table("table"), "column")).and(2).build().toString();
                assertEquals("table.column = 2", string);
        }

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
