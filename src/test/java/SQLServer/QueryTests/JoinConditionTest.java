package SQLServer.QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import com.squiggle.Squiggle;
import com.squiggle.base.Column;
import com.squiggle.base.Table;
import com.squiggle.base.Joins.JoinCondition;
import com.squiggle.builders.JoinConditionBuilder;
import com.squiggle.output.ToStringer;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.SelectQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JoinConditionTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new SqlServerParser());
        }

        @Test
        public void simpleColumnEquality() {
                Table table = new Table("table1");
                Column column1 = table.getColumn("column1");
                JoinConditionBuilder jcb = new JoinConditionBuilder().from(column1).to("table2").on("column2");
                JoinCondition joinCondition = jcb.build();

                assertEquals("table1.column1 = table2.column2", ToStringer.toString(joinCondition));

        }

        @Test
        public void twoColumnEquality() {
                Table table = new Table("table1");
                Column column1 = table.getColumn("column1");
                JoinConditionBuilder jcb = new JoinConditionBuilder().from(column1)
                                .to("table2").on("column2")
                                .and("column3").on("column4");
                JoinCondition joinCondition = jcb.build();

                assertEquals("table1.column1 = table2.column2 AND table1.column3 = table2.column4",
                                ToStringer.toString(joinCondition));

        }

        @Test
        public void threeColumnEquality() {
                Table table = new Table("table1");
                Column column1 = table.getColumn("column1");
                JoinConditionBuilder jcb = new JoinConditionBuilder().from(column1)
                                .to("table2").on("column2")
                                .and("column3").on("column4")
                                .and("column5").on("column6");
                JoinCondition joinCondition = jcb.build();

                assertEquals("table1.column1 = table2.column2 AND table1.column3 = table2.column4 AND table1.column5 = table2.column6",
                                ToStringer.toString(joinCondition));

        }

        @Test
        public void switchTablesJoinCondition() {
                Integer val2 = new Random().nextInt(1000000);
                Integer val2bis = new Random().nextInt(1000000);
                SelectQuery select = Squiggle.Select().from("tableA", "tbA").select("colA1").join("colA1",
                                join -> join.to("tableB", "tbB").on("colB1").switchTable().and("colB2")
                                                .is(val2)
                                                .and("colB3")
                                                .is("value1").and("colB4").is("value4"))
                                .join("colA1",
                                                join -> join.to("tableC", "tbC").on("colC1").switchTable()
                                                                .and("colC2").is(val2bis)
                                                                .and("colC3").is("value1").and("colC4").is("value4"));

                assertEquals("SELECT tbA.colA1 FROM tableA tbA INNER JOIN tableB tbB ON tbA.colA1 = tbB.colB1 AND tbB.colB2 = "
                                + val2
                                + " AND tbB.colB3 = 'value1' AND tbB.colB4 = 'value4' INNER JOIN tableC tbC ON tbA.colA1 = tbC.colC1 AND tbC.colC2 = "
                                + val2bis + " AND tbC.colC3 = 'value1' AND tbC.colC4 = 'value4'",
                                select.toString());

        }

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
