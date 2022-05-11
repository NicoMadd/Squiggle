package SQLServer.DBTest.Database;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Properties;

import com.squiggle.queries.DropDatabaseQuery;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import utils.DBProperties;
import utils.SQLExecutor;

@Disabled
public class DBDropDatabaseTest {

        Properties properties = DBProperties.getInstance();
        SQLExecutor sqlExecutor = new SQLExecutor(
                        properties.getProperty("jdbc.domain"),
                        properties.getProperty("jdbc.port"),
                        properties.getProperty("jdbc.username"),
                        properties.getProperty("jdbc.password"));

        @Test
        void testDropDatabase() {
                DropDatabaseQuery dropDatabaseQuery = new DropDatabaseQuery("Squiggle");
                sqlExecutor.execute(dropDatabaseQuery.toString());
                assertTrue(!sqlExecutor.getDatabases().contains("Squiggle"));
        }
}
