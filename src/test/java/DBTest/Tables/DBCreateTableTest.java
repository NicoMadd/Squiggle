package DBTest.Tables;

import java.util.Properties;

import com.squiggle.Squiggle;
import com.squiggle.queries.TableQueries.CreateTableQuery;

import org.junit.jupiter.api.Test;

import utils.DBProperties;
import utils.SQLExecutor;

// @Disabled
public class DBCreateTableTest {
        Properties properties = DBProperties.getInstance();
        SQLExecutor sqlExecutor = new SQLExecutor(
                        properties.getProperty("jdbc.domain"),
                        properties.getProperty("jdbc.port"),
                        properties.getProperty("jdbc.username"),
                        properties.getProperty("jdbc.password"),
                        properties.getProperty("jdbc.database"));

        @Test
        public void createTable() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("testTable").column("column1").varchar()
                                .define();
                sqlExecutor.execute(createTableQuery.toString());
        }

        @Test
        public void createTableWithPrimaryKey() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("testTable2").column("column1").varchar().pk()
                                .define();
                sqlExecutor.execute(createTableQuery.toString());
        }

        @Test
        public void createTableWithPrimaryKeyAndForeignKey() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("testTable3").column("column1").varchar().pk()
                                .define().column("column2").varchar().fk("testTable2", "column1").define();
                sqlExecutor.execute(createTableQuery.toString());
        }

        @Test
        public void createTableWithPrimaryKeyAndForeignKeyAndUnique() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("testTable4").column("column1").varchar().pk()
                                .define().column("column2").varchar().fk("testTable2", "column1").unique().define();
                sqlExecutor.execute(createTableQuery.toString());
        }

        @Test
        public void createTableWithPrimaryKeyAndForeignKeyAndUniqueAndNotNull() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("testTable5").column("column1").varchar().pk()
                                .define().column("column2").varchar().fk("testTable2", "column1").unique().notNullable()
                                .define();
                sqlExecutor.execute(createTableQuery.toString());
        }

        @Test
        public void createTableWithPrimaryKeyAndForeignKeyAndUniqueAndNullAndDefault() {
                CreateTableQuery createTableQuery = Squiggle.CreateTable("testTable6")
                                .column("column1").varchar().pk().foreignKey("testTable2", "column1").define()
                                .column("column2").varchar().unique().nullable().defaultValue("test").define();
                sqlExecutor.execute(createTableQuery.toString());
        }

}
