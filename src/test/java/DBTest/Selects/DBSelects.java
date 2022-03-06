package DBTest.Selects;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.squiggle.Squiggle;
import com.squiggle.queries.InsertQuery;
import com.squiggle.queries.SelectQuery;
import com.squiggle.queries.TableQueries.CreateTableQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import utils.DBProperties;
import utils.SQLExecutor;

// @Disabled
public class DBSelects {

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
                                .column("column2").varchar(255).define()
                                .column("column3").integer().define();

                if (!sqlExecutor.tableExists(database, tableName))
                        sqlExecutor.execute(createTableQuery.toString());
                else
                        System.out.println("Table already exists");

                assertTrue(sqlExecutor.getTables(database).contains(tableName));

                InsertQuery insertQuery = Squiggle.Insert().into(tableName).to("column2").to("column3");
                for (int i = 0; i < 100; i++) {

                        switch (i % 4) {
                                case 0:
                                        insertQuery.value("type1").value(1);
                                        break;
                                case 1:
                                        insertQuery.value("type2").value(1);
                                        break;
                                case 2:
                                        insertQuery.value("type3").value(1);
                                        break;
                                case 3:
                                        insertQuery.value("type4").value(1);
                                        break;
                        }
                }
                sqlExecutor.execute(insertQuery.toString());

        }

        @Test
        public void selectGroupBy() {
                SelectQuery selectQuery = Squiggle.Select().from(tableName).select("column2", "type")
                                .sum("column3", "Sum").groupBy("column2");
                ResultSet rs = sqlExecutor.query(selectQuery.toString());

                try {
                        while (rs.next()) {
                                String type = rs.getString("type");
                                Integer sum = rs.getInt("sum");
                                System.out.println(type + ": " + sum);
                                assertTrue(sum == 25);
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

        }

        @AfterEach
        @Disabled
        public void dropTable() {
                System.out.println("dropping table");

                sqlExecutor.execute(Squiggle.DropTable(tableName).toString());
                assertTrue(!sqlExecutor.getTables(database).contains(tableName));
        }

}
