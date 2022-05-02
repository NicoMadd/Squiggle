package SQLServer.DBTest.Inserts;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.squiggle.Squiggle;
import com.squiggle.queries.InsertQuery;
import com.squiggle.queries.SelectQuery;
import com.squiggle.queries.TableQueries.CreateTableQuery;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import utils.DBProperties;
import utils.SQLExecutor;

@Disabled
public class DBInsertStress {
        /**
         *
         */
        private final String tableName = "testTable";
        Properties properties = DBProperties.getInstance();
        String database = properties.getProperty("jdbc.database");
        SQLExecutor sqlExecutor = new SQLExecutor(
                        properties.getProperty("jdbc.domain"),
                        properties.getProperty("jdbc.port"),
                        properties.getProperty("jdbc.username"),
                        properties.getProperty("jdbc.password"),
                        database);

        @BeforeEach
        public void createTable() {
                System.out.println("creating table");
                // create table with PK auto - increment and varchar column

                CreateTableQuery createTableQuery = Squiggle.CreateTable(tableName)
                                .column("column1").integer().pk().autoIncrement().define()
                                .column("column2").varchar(255).define();

                if (!sqlExecutor.tableExists(database, tableName))
                        sqlExecutor.execute(createTableQuery.toString());
                else
                        System.out.println("Table already exists");

                assertTrue(sqlExecutor.getTables(database).contains(tableName));

        }

        @Test
        public void insertStress100() {

                // insert 100 rows
                InsertQuery insertQuery = Squiggle.Insert().into(tableName).to("column2");
                for (int i = 0; i < 100; i++) {
                        insertQuery.value("value" + i);
                }
                sqlExecutor.execute(insertQuery.toString());

                SelectQuery selectQuery = Squiggle.Select().from(tableName).count("column1", "count");
                ResultSet rs = sqlExecutor.query(selectQuery.toString());
                Integer count = 0;
                try {
                        assertTrue(rs.next());
                        count = rs.getInt("count");

                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        System.out.println("count: " + count);
                        assertTrue(count == 100);
                }

        }

        @Test
        public void insertStress1000() {

                // insert 1000 rows
                InsertQuery insertQuery = Squiggle.Insert().into(tableName).to("column2");
                for (int i = 0; i < 1000; i++) {
                        insertQuery.value("value" + i);
                }
                sqlExecutor.execute(insertQuery.toString());

                SelectQuery selectQuery = Squiggle.Select().from(tableName).count("column1");
                ResultSet rs = sqlExecutor.query(selectQuery.toString());
                Integer count = 0;
                try {
                        assertTrue(rs.next());
                        count = rs.getInt(1);

                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        System.out.println("count: " + count);
                        assertTrue(count == 1000);
                }
        }

        // TODO split inserts in more than one query in order to build queries with more
        // than 1000 rows
        @Test
        @Disabled
        public void insertStress1000000() {

                // insert 100 rows
                InsertQuery insertQuery = Squiggle.Insert().into(tableName).to("column2");
                for (int i = 0; i < 1000000; i++) {
                        insertQuery.value("value" + i);
                }
                sqlExecutor.execute(insertQuery.toString());

                SelectQuery selectQuery = Squiggle.Select().from(tableName).count("column1");
                ResultSet rs = sqlExecutor.query(selectQuery.toString());
                Integer count = 0;
                try {
                        assertTrue(rs.next());
                        count = rs.getInt(1);

                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        System.out.println("count: " + count);
                        assertTrue(count == 1000000);
                }
        }

        @AfterEach
        public void dropTable() {
                System.out.println("dropping table");

                sqlExecutor.execute(Squiggle.DropTable(tableName).toString());
                assertTrue(!sqlExecutor.getTables(database).contains(tableName));
        }

}
