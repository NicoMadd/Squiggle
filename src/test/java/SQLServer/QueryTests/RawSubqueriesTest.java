package SQLServer.QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

public class RawSubqueriesTest {

        @BeforeAll
        public static void setUp() {
                Squiggle.setParser(new SqlServerParser());
        }

        @Test
        public void selectFromRawQuery() {

                String sql = "select * from table t";

                SelectQuery select = Squiggle.Select()
                                .from("table", "tblm").select("*")
                                .join("ESTADO", j -> j.to("TB_LISTAS_MATCHES_ESTADOS", "tbme").on("estado")).select("*")
                                .useTable(0)
                                .join("IDCASO", j -> j.to("TB_LISTAS_MATCHES_ANALISIS", "tblma").on("IDCASO")
                                                .and("ROWNUMBER").on("UI").and("INTERFACE").on("INTERFACE").and("FECHA")
                                                .on("FECHA"))
                                .select("*")
                                .useTable(0)
                                .leftJoin("IDCASO",
                                                j -> j.toSub(sql).on("IDCASO").and("UI")
                                                                .on("UI"))
                                .select("column111")
                                .useTable(0);

                System.out.println(select.toString());
        }

        @AfterAll
        public static void tearDown() {
                Squiggle.setParser(null);
        }
}
