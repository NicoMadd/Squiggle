package SQLServer.QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.squiggle.Squiggle;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.TableQueries.DropTableQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

//TODO split this into multiple tests

public class DropTableTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new SqlServerParser());
        }

        @Test
        public void simpleDropTable() {
                DropTableQuery dropTableQuery = Squiggle.DropTable("table");
                assertEquals("DROP TABLE table", dropTableQuery.toString());
        }

        @Test
        public void dropTableWithNullName() {
                DropTableQuery dropTableQuery = Squiggle.DropTable(null);
                Exception thrown = assertThrows(IllegalArgumentException.class, () -> dropTableQuery.toString());
                assertTrue(thrown.getMessage().contains("Cannot drop table without a name"));
        }

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }

}
