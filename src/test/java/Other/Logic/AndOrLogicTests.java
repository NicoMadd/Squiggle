package Other.Logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.builders.LogicBuilder;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.types.values.BooleanTypeValue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AndOrLogicTests {

        @Test
        public void tripleStringAndWithOr() {
                LogicBuilder lg = new LogicBuilder().that("arg1").equals("arg2").and("arg3").equals("arg4").or("arg5")
                                .equals("arg6");
                assertEquals("'arg1' = 'arg2' AND 'arg3' = 'arg4' OR 'arg5' = 'arg6'", lg.toString());
        }

        @Test
        public void tripleIntegerAndWithOr() {
                LogicBuilder lg = new LogicBuilder().that(1).equals(1).and(2).equals(2).or(3).equals(3);
                assertEquals("1 = 1 AND 2 = 2 OR 3 = 3", lg.toString());
        }

        @Test
        public void tripleDoubleAndWithOr() {
                LogicBuilder lg = new LogicBuilder().that(1.0).equals(1.0).and(2.0).equals(2.0).or(3.0).equals(3.0);
                assertEquals("1.0 = 1.0 AND 2.0 = 2.0 OR 3.0 = 3.0", lg.toString());
        }

        @Test
        public void tripleBooleanAndWithOr() {
                LogicBuilder lg = new LogicBuilder().that(true).equals(true).and(false).equals(false).or(true)
                                .equals(true);
                assertEquals("1 = 1 AND 0 = 0 OR 1 = 1", lg.toString());
        }

        @Test
        public void tripleBooleanAndWithOrAsText() {
                BooleanTypeValue.setAsText(true);
                LogicBuilder lg = new LogicBuilder().that(true).equals(true).and(false).equals(false).or(true)
                                .equals(true);
                assertEquals("true = true AND false = false OR true = true", lg.toString());
                BooleanTypeValue.setAsText(false);
        }

        @Test
        public void tripleFloatAndWithOr() {
                LogicBuilder lg = new LogicBuilder().that(1.0f).equals(1.0f).and(2.0f).equals(2.0f).or(3.0f)
                                .equals(3.0f);
                assertEquals("1.000000 = 1.000000 AND 2.000000 = 2.000000 OR 3.000000 = 3.000000", lg.toString());
        }

}
