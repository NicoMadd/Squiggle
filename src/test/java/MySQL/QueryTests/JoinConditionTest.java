package MySQL.QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.base.Column;
import com.squiggle.base.Table;
import com.squiggle.base.Joins.JoinCondition;
import com.squiggle.builders.JoinConditionBuilder;
import com.squiggle.output.ToStringer;
import com.squiggle.parsers.MySQLParser;
import com.squiggle.parsers.SqlServerParser;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JoinConditionTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new MySQLParser());
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

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
