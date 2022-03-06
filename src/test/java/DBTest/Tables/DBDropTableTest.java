package DBTest.Tables;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Properties;

import com.squiggle.Squiggle;
import com.squiggle.queries.TableQueries.CreateTableQuery;

import org.junit.jupiter.api.Test;

import utils.DBProperties;
import utils.SQLExecutor;

// @Disabled
public class DBDropTableTest {
        Properties properties = DBProperties.getInstance();
        String database = properties.getProperty("jdbc.database");

        SQLExecutor sqlExecutor = new SQLExecutor(
                        properties.getProperty("jdbc.domain"),
                        properties.getProperty("jdbc.port"),
                        properties.getProperty("jdbc.username"),
                        properties.getProperty("jdbc.password"),
                        database);

        @Test
        public void dropTable() {

                // create table with PK auto - increment and varchar column

                CreateTableQuery createTableQuery = Squiggle.CreateTable("testTable")
                                .column("column1").integer().pk().autoIncrement().define()
                                .column("column2").varchar(255).define();
                ;
                sqlExecutor.execute(createTableQuery.toString());

                assertTrue(sqlExecutor.getTables(database).contains("testTable"));

                // drop table

                sqlExecutor.execute(Squiggle.DropTable("testTable").toString());

                assertTrue(!sqlExecutor.getTables(database).contains("testTable"));

        }
}
