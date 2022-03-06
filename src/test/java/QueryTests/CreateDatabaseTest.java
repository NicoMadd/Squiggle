package QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.squiggle.Squiggle;
import com.squiggle.queries.CreateDatabaseQuery;

import org.junit.jupiter.api.Test;

//TODO split this into multiple tests

public class CreateDatabaseTest {

        @Test
        public void simpleCreateDatabase() {
                CreateDatabaseQuery createDatabaseQuery = Squiggle.CreateDatabase("database");
                assertEquals("CREATE DATABASE database", createDatabaseQuery.toString());
        }

        @Test
        public void createDatabaseWithNullName() {
                CreateDatabaseQuery createDatabaseQuery = Squiggle.CreateDatabase(null);
                Exception thrown = assertThrows(IllegalArgumentException.class, () -> createDatabaseQuery.toString());
                assertTrue(thrown.getMessage().contains("Cannot create database without a name"));
        }

}
