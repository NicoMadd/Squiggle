package SQLServer.QueryTests.JoinQueryTests.LeftJoins;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.SelectQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LeftJoinsComplexTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new SqlServerParser());
        }

        @Test
        @DisplayName("Left Join With 4 tables select column1 from Table A and column2 from Table D. Table A joins to Table B, Table B joins to Table C, Table B to Table D")
        public void case1() {
                SelectQuery select = Squiggle.Select().from("tableA").select("column1")
                                .leftJoin("column2", join -> join.to("tableB").on("columnB1")).useJoinedTable()
                                .leftJoin("column3", join -> join.to("tableC").on("columnC1")).useJoinedTable()
                                .leftJoin("column4", join -> join.to("tableD").on("columnD1")).useJoinedTable()
                                .select("column2");

                assertEquals("SELECT tableA.column1, tableD.column2 FROM tableA LEFT JOIN tableB ON tableA.column2 = tableB.columnB1 LEFT JOIN tableC ON tableB.column3 = tableC.columnC1 LEFT JOIN tableD ON tableC.column4 = tableD.columnD1",
                                select.toString());

        }

        @Test
        @DisplayName("Tables with Aliases. Left Join With 4 tables select column1 from Table A and column2 from Table D. Table A joins to Table B, Table B joins to Table C, Table B to Table D")
        public void case2() {
                SelectQuery select = Squiggle.Select().from("tableA", "tba").select("column1")
                                .leftJoin("column2", join -> join.to("tableB", "tbb").on("columnB1")).useJoinedTable()
                                .leftJoin("column3", join -> join.to("tableC", "tbc").on("columnC1")).useJoinedTable()
                                .leftJoin("column4", join -> join.to("tableD", "tbd").on("columnD1")).useJoinedTable()
                                .select("column2");
                assertEquals("SELECT tba.column1, tbd.column2 FROM tableA tba LEFT JOIN tableB tbb ON tba.column2 = tbb.columnB1 LEFT JOIN tableC tbc ON tbb.column3 = tbc.columnC1 LEFT JOIN tableD tbd ON tbc.column4 = tbd.columnD1",
                                select.toString());

        }

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
