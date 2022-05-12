package SQLServer.QueryTests.JoinQueryTests.InnerJoins;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import com.squiggle.Squiggle;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.SelectQuery;
import com.squiggle.types.values.BooleanTypeValue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Inner Joins Values Test")
public class InnerJoinValuesTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new SqlServerParser());
        }

        @Test
        @DisplayName("Inner Join With One String Value")
        public void innerJoinWithColumnAndStringValue() {
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").innerJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is("stringValue"));

                assertEquals("SELECT tableA.column1 FROM tableA INNER JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = 'stringValue'",
                                select.toString());
        }

        @Test
        @DisplayName("Inner Join With One Integer Value")
        public void innerJoinWithColumnAndIntegerValue() {
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").innerJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is(1));

                assertEquals("SELECT tableA.column1 FROM tableA INNER JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = 1",
                                select.toString());
        }

        @Test
        @DisplayName("Inner Join With One Float Value")
        public void innerJoinWithColumnAndFloatValue() {
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").innerJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is(1.1f));

                assertEquals("SELECT tableA.column1 FROM tableA INNER JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = 1.100000",
                                select.toString());
        }

        @Test
        @DisplayName("Inner Join With One Double Value")
        public void innerJoinWithColumnAndDoubleValue() {
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").innerJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is(1.1));

                assertEquals("SELECT tableA.column1 FROM tableA INNER JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = 1.1",
                                select.toString());
        }

        @Test
        @DisplayName("Inner Join With One Boolean Value as text")
        public void innerJoinWithColumnAndBooleanValueAsText() {
                BooleanTypeValue.asText();
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").innerJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is(true));

                assertEquals("SELECT tableA.column1 FROM tableA INNER JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = true",
                                select.toString());
        }

        @Test
        @DisplayName("Inner Join With One Boolean Value as integer")
        public void innerJoinWithColumnAndBooleanValueAsInt() {
                BooleanTypeValue.asInt();
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").innerJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is(true));

                assertEquals("SELECT tableA.column1 FROM tableA INNER JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = 1",
                                select.toString());
        }

        @Test
        @DisplayName("Inner Join With One Date Value")
        public void innerJoinWithColumnAndDateValue() {
                Date date = new Date();
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").innerJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is(date.toString()));

                assertEquals("SELECT tableA.column1 FROM tableA INNER JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = '"
                                + date + "'", select.toString());
        }

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
