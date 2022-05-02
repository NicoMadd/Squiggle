package SQLServer.QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.squiggle.Squiggle;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.CreateDatabaseQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CreateDatabaseTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new SqlServerParser());
        }

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

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }

}
