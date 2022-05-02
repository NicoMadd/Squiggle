package MySQL.QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.squiggle.Squiggle;
import com.squiggle.parsers.MySQLParser;
import com.squiggle.queries.DropDatabaseQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DropDatabaseTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new MySQLParser());
        }

        @Test
        public void simpleDropDatabase() {
                DropDatabaseQuery dropDatabaseQuery = Squiggle.DropDatabase("database");
                assertEquals("DROP DATABASE database", dropDatabaseQuery.toString());
        }

        @Test
        public void dropDatabaseWithNullName() {
                DropDatabaseQuery createDatabaseQuery = Squiggle.DropDatabase(null);
                Exception thrown = assertThrows(IllegalArgumentException.class, () -> createDatabaseQuery.toString());
                assertTrue(thrown.getMessage().contains("Cannot drop database without a name"));
        }

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
