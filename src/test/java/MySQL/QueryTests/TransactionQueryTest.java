package MySQL.QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.parsers.MySQLParser;
import com.squiggle.queries.TransactionQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionQueryTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new MySQLParser());
        }

        // TODO ADD TEST COMMITTED AND ROLLBACK IN THE SAME.
        // TODO ADD NAMED TRANSACTIONS TO ROLLBACK IF CONDITION.
        @Test
        public void simpleEmptyTransaction() {
                TransactionQuery tsQuery = Squiggle.Transaction().commit();
                assertEquals("BEGIN TRANSACTION COMMIT TRANSACTION", tsQuery.toString());
        }

        @Test
        public void simpleInsertTransactionCommitted() {
                TransactionQuery tsQuery = Squiggle.Transaction()
                                .addInsert(query -> query.into("table").to("column1").value(2))
                                .commit();
                assertEquals("BEGIN TRANSACTION INSERT INTO table (column1) VALUES (2) COMMIT TRANSACTION",
                                tsQuery.toString());
        }

        @Test
        // TODO check space in =. why is it column1=2 and not column1 = 2?
        public void simpleUpdateTransactionCommitted() {
                TransactionQuery tsQuery = Squiggle.Transaction()
                                .addUpdate(query -> query.table("table").set("column1").to(2))
                                .commit();
                assertEquals("BEGIN TRANSACTION UPDATE table SET table.column1=2 COMMIT TRANSACTION",
                                tsQuery.toString());
        }

        @Test
        public void simpleDeleteTransactionCommitted() {
                TransactionQuery tsQuery = Squiggle.Transaction()
                                .addDelete(query -> query.from("table").where("column1", c -> c.equals(2)))
                                .commit();
                assertEquals("BEGIN TRANSACTION DELETE FROM table WHERE table.column1 = 2 COMMIT TRANSACTION",
                                tsQuery.toString());
        }

        @Test
        public void simpleSelectTransactionCommitted() {
                TransactionQuery tsQuery = Squiggle.Transaction()
                                .addSelect(query -> query.from("table").select("*").where("column1", c -> c.equals(2)))
                                .commit();
                assertEquals("BEGIN TRANSACTION SELECT table.* FROM table WHERE table.column1 = 2 COMMIT TRANSACTION",
                                tsQuery.toString());
        }

        @Test
        public void simpleSelectTransactionRolledBack() {
                TransactionQuery tsQuery = Squiggle.Transaction()
                                .addSelect(query -> query.from("table").select("*").where("column1", c -> c.equals(2)))
                                .rollback();
                assertEquals("BEGIN TRANSACTION SELECT table.* FROM table WHERE table.column1 = 2 ROLLBACK TRANSACTION",
                                tsQuery.toString());
        }

        @Test
        public void simpleInsertTransactionRolledBack() {
                TransactionQuery tsQuery = Squiggle.Transaction()
                                .addSelect(query -> query.from("table").select("*").where("column1", c -> c.equals(2)))
                                .rollback();
                assertEquals("BEGIN TRANSACTION SELECT table.* FROM table WHERE table.column1 = 2 ROLLBACK TRANSACTION",
                                tsQuery.toString());
        }

        @Test
        public void simpleDeleteTransactionRolledBack() {
                TransactionQuery tsQuery = Squiggle.Transaction()
                                .addDelete(query -> query.from("table").where("column1", c -> c.equals(2)))
                                .rollback();
                assertEquals("BEGIN TRANSACTION DELETE FROM table WHERE table.column1 = 2 ROLLBACK TRANSACTION",
                                tsQuery.toString());
        }

        @Test
        public void simpleUpdateTransactionRolledBack() {
                TransactionQuery tsQuery = Squiggle.Transaction()
                                .addUpdate(query -> query.table("table").set("column1").to(2))
                                .rollback();
                assertEquals("BEGIN TRANSACTION UPDATE table SET table.column1=2 ROLLBACK TRANSACTION",
                                tsQuery.toString());
        }

        @Test
        public void composedTransactionCommitted() {
                String table = "table";
                TransactionQuery tsQuery = Squiggle.Transaction()
                                .addInsert(query -> query.into(table).to("column1").value(2))
                                .addSelect(query -> query.from(table).select("*").where("column1", c -> c.equals(2)))
                                .addDelete(query -> query.from(table).where("column1", c -> c.equals(2)))
                                .addUpdate(query -> query.table(table).set("column1").to(2))
                                .commit();
                assertEquals(
                                "BEGIN TRANSACTION INSERT INTO table (column1) VALUES (2) SELECT table.* FROM table WHERE table.column1 = 2 DELETE FROM table WHERE table.column1 = 2 UPDATE table SET table.column1=2 COMMIT TRANSACTION",
                                tsQuery.toString());
        }

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }

}
