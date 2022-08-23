package SQLServer.QueryTests.JoinQueryTests.LeftJoins;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import com.squiggle.Squiggle;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.SelectQuery;
import com.squiggle.types.values.BooleanTypeValue;
import com.squiggle.types.values.FloatTypeValue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Left Joins Values Test")
public class LeftJoinValuesTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new SqlServerParser());
        }

        @BeforeEach
        public void setUpEach() {
                FloatTypeValue.defaultFormat();
                BooleanTypeValue.asText();
        }

        @Test
        @DisplayName("Left Join With One String Value")
        public void leftJoinWithColumnAndStringValue() {
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").leftJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is("stringValue"));

                assertEquals("SELECT tableA.column1 FROM tableA LEFT JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = 'stringValue'",
                                select.toString());
        }

        @Test
        @DisplayName("Left Join With One Integer Value")
        public void leftJoinWithColumnAndIntegerValue() {
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").leftJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is(1));

                assertEquals("SELECT tableA.column1 FROM tableA LEFT JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = 1",
                                select.toString());
        }

        @Test
        @DisplayName("Left Join With One Float Value")
        public void leftJoinWithColumnAndFloatValue() {
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").leftJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is(1.1f));

                assertEquals("SELECT tableA.column1 FROM tableA LEFT JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = 1.1",
                                select.toString());
        }

        @Test
        @DisplayName("Left Join With One Double Value")
        public void leftJoinWithColumnAndDoubleValue() {
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").leftJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is(1.1));

                assertEquals("SELECT tableA.column1 FROM tableA LEFT JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = 1.1",
                                select.toString());
        }

        @Test
        @DisplayName("Left Join With One Boolean Value as text")
        public void leftJoinWithColumnAndBooleanValueAsText() {
                BooleanTypeValue.asText();
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").leftJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is(true));

                assertEquals("SELECT tableA.column1 FROM tableA LEFT JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = true",
                                select.toString());
        }

        @Test
        @DisplayName("Left Join With One Boolean Value as integer")
        public void leftJoinWithColumnAndBooleanValueAsInt() {
                BooleanTypeValue.asInt();
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").leftJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is(true));

                assertEquals("SELECT tableA.column1 FROM tableA LEFT JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = 1",
                                select.toString());
        }

        @Test
        @DisplayName("Left Join With One Date Value")
        public void leftJoinWithColumnAndDateValue() {
                Date date = new Date();
                SelectQuery select = Squiggle.Select().from("tableA")
                                .select("column1").leftJoin("columnA1",
                                                join -> join.to("tableB").on("columnB1")
                                                                .and("columnA2").is(date.toString()));

                assertEquals("SELECT tableA.column1 FROM tableA LEFT JOIN tableB ON tableA.columnA1 = tableB.columnB1 AND tableA.columnA2 = '"
                                + date + "'", select.toString());
        }

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
