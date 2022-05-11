package Other.Logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.builders.LogicBuilder;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.types.values.BooleanTypeValue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AndLogicTests {

        @Test
        public void simpleStringStringAnd() {
                LogicBuilder lg = new LogicBuilder().that("arg1").equals("arg2").and("arg3").equals("arg4");
                assertEquals("'arg1' = 'arg2' AND 'arg3' = 'arg4'", lg.toString());

        }

        @Test
        public void simpleIntegerIntegerAnd() {
                LogicBuilder lg = new LogicBuilder().that(1).equals(1).and(2).equals(2);
                assertEquals("1 = 1 AND 2 = 2", lg.toString());
        }

        @Test
        public void simpleDoubleDoubleAnd() {
                LogicBuilder lg = new LogicBuilder().that(1.0).equals(1.0).and(2.0).equals(2.0);
                assertEquals("1.0 = 1.0 AND 2.0 = 2.0", lg.toString());
        }

        @Test
        public void simpleBooleanBooleanAnd() {
                LogicBuilder lg = new LogicBuilder().that(true).equals(true).and(false).equals(false);
                assertEquals("1 = 1 AND 0 = 0", lg.toString());
        }

        @Test
        public void simpleBooleanBooleanAsTextAnd() {
                BooleanTypeValue.setAsText(true);
                LogicBuilder lg = new LogicBuilder().that(true).equals(true).and(false).equals(false);
                assertEquals("true = true AND false = false", lg.toString());
                BooleanTypeValue.setAsText(false);

        }

        @Test
        public void simpleFloatFloatAnd() {
                LogicBuilder lg = new LogicBuilder().that(1.0f).equals(1.0f).and(2.0f).equals(2.0f);
                assertEquals("1.000000 = 1.000000 AND 2.000000 = 2.000000", lg.toString());
        }

        @Test
        public void tripleStringAnd() {
                LogicBuilder lg = new LogicBuilder().that("arg1").equals("arg2").and("arg3").equals("arg4").and("arg5")
                                .equals("arg6");
                assertEquals("'arg1' = 'arg2' AND 'arg3' = 'arg4' AND 'arg5' = 'arg6'", lg.toString());
        }

        @Test
        public void tripleIntegerAnd() {
                LogicBuilder lg = new LogicBuilder().that(1).equals(1).and(2).equals(2).and(3).equals(3);
                assertEquals("1 = 1 AND 2 = 2 AND 3 = 3", lg.toString());
        }

        @Test
        public void tripleDoubleAnd() {
                LogicBuilder lg = new LogicBuilder().that(1.0).equals(1.0).and(2.0).equals(2.0).and(3.0).equals(3.0);
                assertEquals("1.0 = 1.0 AND 2.0 = 2.0 AND 3.0 = 3.0", lg.toString());
        }

        @Test
        public void tripleBooleanAnd() {
                LogicBuilder lg = new LogicBuilder().that(true).equals(true).and(false).equals(false).and(true)
                                .equals(true);
                assertEquals("1 = 1 AND 0 = 0 AND 1 = 1", lg.toString());
        }

        @Test
        public void tripleBooleanAsTextAnd() {
                BooleanTypeValue.setAsText(true);
                LogicBuilder lg = new LogicBuilder().that(true).equals(true).and(false).equals(false).and(true)
                                .equals(true);
                assertEquals("true = true AND false = false AND true = true", lg.toString());
                BooleanTypeValue.setAsText(false);
        }

        @Test
        public void tripleFloatAnd() {
                LogicBuilder lg = new LogicBuilder().that(1.0f).equals(1.0f).and(2.0f).equals(2.0f).and(3.0f)
                                .equals(3.0f);
                assertEquals("1.000000 = 1.000000 AND 2.000000 = 2.000000 AND 3.000000 = 3.000000", lg.toString());
        }

}
