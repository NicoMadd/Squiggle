package Other.Logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.builders.LogicBuilder;
import com.squiggle.types.values.BooleanTypeValue;
import com.squiggle.types.values.FloatTypeValue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class InequalityLogicTests {

        @AfterEach
        public void tearDown() {
                FloatTypeValue.defaultFormat();
                BooleanTypeValue.setAsText(false);

        }

        @Test
        public void simpleStringStringEquality() {
                LogicBuilder lg = new LogicBuilder().that("arg1").not("arg2");
                assertEquals("'arg1' != 'arg2'", lg.toString());
        }

        @Test
        public void simpleStringIntegerEquality() {
                LogicBuilder lg = new LogicBuilder().that("arg1").not(1);
                assertEquals("'arg1' != 1", lg.toString());
        }

        @Test
        public void simpleStringDoubleEquality() {
                LogicBuilder lg = new LogicBuilder().that("arg1").not(1.0);
                assertEquals("'arg1' != 1.0", lg.toString());
        }

        @Test
        public void simpleStringBooleanEquality() {
                LogicBuilder lg = new LogicBuilder().that("arg1").not(true);
                assertEquals("'arg1' != 1", lg.toString());

                lg = new LogicBuilder().that("arg1").not(false);
                assertEquals("'arg1' != 0", lg.toString());
        }

        @Test
        public void simpleIntegerStringEquality() {
                LogicBuilder lg = new LogicBuilder().that(1).not("arg2");
                assertEquals("1 != 'arg2'", lg.toString());
        }

        @Test
        public void simpleIntegerIntegerEquality() {
                LogicBuilder lg = new LogicBuilder().that(1).not(1);
                assertEquals("1 != 1", lg.toString());
        }

        @Test
        public void simpleIntegerDoubleEquality() {
                LogicBuilder lg = new LogicBuilder().that(1).not(1.0);
                assertEquals("1 != 1.0", lg.toString());
        }

        @Test
        public void simpleIntegerBooleanEquality() {
                LogicBuilder lg = new LogicBuilder().that(1).not(true);
                assertEquals("1 != 1", lg.toString());

                lg = new LogicBuilder().that(1).not(false);
                assertEquals("1 != 0", lg.toString());
        }

        @Test
        public void simpleDoubleStringEquality() {
                LogicBuilder lg = new LogicBuilder().that(1.0).not("arg2");
                assertEquals("1.0 != 'arg2'", lg.toString());
        }

        @Test
        public void simpleDoubleIntegerEquality() {
                LogicBuilder lg = new LogicBuilder().that(1.0).not(1);
                assertEquals("1.0 != 1", lg.toString());
        }

        @Test
        public void simpleDoubleDoubleEquality() {
                LogicBuilder lg = new LogicBuilder().that(1.0).not(1.0);
                assertEquals("1.0 != 1.0", lg.toString());
        }

        @Test
        public void simpleDoubleBooleanEquality() {
                LogicBuilder lg = new LogicBuilder().that(1.0).not(true);
                assertEquals("1.0 != 1", lg.toString());

                lg = new LogicBuilder().that(1.0).not(false);
                assertEquals("1.0 != 0", lg.toString());
        }

        @Test
        public void simpleBooleanStringEquality() {
                LogicBuilder lg = new LogicBuilder().that(true).not("arg2");
                assertEquals("1 != 'arg2'", lg.toString());
        }

        @Test
        public void simpleBooleanIntegerEquality() {
                BooleanTypeValue.asInt();
                LogicBuilder lg = new LogicBuilder().that(true).not(1);
                assertEquals("1 != 1", lg.toString());
        }

        @Test
        public void simpleBooleanFloatEquality() {
                FloatTypeValue.setFormat("0.000000");
                LogicBuilder lg = new LogicBuilder().that(true).not(1.0f);
                assertEquals("1 != 1.000000", lg.toString());
        }

        @Test
        public void simpleBooleanBooleanEquality() {
                LogicBuilder lg = new LogicBuilder().that(true).not(true);
                assertEquals("1 != 1", lg.toString());

                lg = new LogicBuilder().that(true).not(false);
                assertEquals("1 != 0", lg.toString());
        }

        // Simple booleans as text

        @Test
        public void simpleBooleanAsTextStringEquality() {
                BooleanTypeValue.setAsText(true);
                LogicBuilder lg = new LogicBuilder().that(true).not("arg2");
                assertEquals("true != 'arg2'", lg.toString());
                BooleanTypeValue.setAsText(false);
        }

        @Test
        public void simpleBooleanAsTextIntegerEquality() {
                BooleanTypeValue.setAsText(true);
                LogicBuilder lg = new LogicBuilder().that(true).not(1);
                assertEquals("true != 1", lg.toString());
                BooleanTypeValue.setAsText(false);
        }

        @Test
        public void simpleBooleanAsTextFloatEquality() {
                BooleanTypeValue.setAsText(true);
                LogicBuilder lg = new LogicBuilder().that(true).not(1.0);
                assertEquals("true != 1.0", lg.toString());
                BooleanTypeValue.setAsText(false);
        }

        @Test
        public void simpleBooleanAsTextBooleanEquality() {
                BooleanTypeValue.setAsText(true);
                LogicBuilder lg = new LogicBuilder().that(true).not(true);
                assertEquals("true != true", lg.toString());
                BooleanTypeValue.setAsText(false);
        }

        // Specific float types

        @Test
        public void simpleFloatStringEquality() {
                FloatTypeValue.setFormat("0.000000");
                LogicBuilder lg = new LogicBuilder().that(1.0f).not("arg2");
                assertEquals("1.000000 != 'arg2'", lg.toString());
        }

        @Test
        public void simpleFloatIntegerEquality() {
                FloatTypeValue.setFormat("0.000000");
                LogicBuilder lg = new LogicBuilder().that(1.0f).not(1);
                assertEquals("1.000000 != 1", lg.toString());
        }

        @Test
        public void simpleFloatDoubleEquality() {
                FloatTypeValue.setFormat("0.000000");
                LogicBuilder lg = new LogicBuilder().that(1.0f).not(1.0);
                assertEquals("1.000000 != 1.0", lg.toString());
        }

        @Test
        public void simpleFloatBooleanEquality() {
                FloatTypeValue.setFormat("0.000000");
                LogicBuilder lg = new LogicBuilder().that(1.0f).not(true);
                assertEquals("1.000000 != 1", lg.toString());
        }

        @Test
        public void simpleFloatFloatEquality() {
                FloatTypeValue.setFormat("0.000000");
                LogicBuilder lg = new LogicBuilder().that(1.0f).not(1.0f);
                assertEquals("1.000000 != 1.000000", lg.toString());
        }

}
