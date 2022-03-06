package QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.squiggle.Squiggle;
import com.squiggle.queries.DropDatabaseQuery;

import org.junit.jupiter.api.Test;

//TODO split this into multiple tests

public class DropDatabaseTest {

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

}
