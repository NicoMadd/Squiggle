package DBTest.Database;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Properties;

import com.squiggle.Squiggle;
import com.squiggle.queries.CreateDatabaseQuery;

import org.junit.jupiter.api.Test;

import utils.DBProperties;
import utils.SQLExecutor;

// @Disabled
public class DBCreateDatabaseTest {

        Properties properties = DBProperties.getInstance();
        SQLExecutor sqlExecutor = new SQLExecutor(
                        properties.getProperty("jdbc.domain"),
                        properties.getProperty("jdbc.port"),
                        properties.getProperty("jdbc.username"),
                        properties.getProperty("jdbc.password"));

        @Test
        public void createDatabase() {
                CreateDatabaseQuery createDatabaseQuery = Squiggle.CreateDatabase("Squiggle");
                sqlExecutor.execute(createDatabaseQuery.toString());
                assertTrue(sqlExecutor.getDatabases().contains("Squiggle"));
        }

}
