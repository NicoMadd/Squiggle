package Other.Logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.builders.LogicBuilder;
import com.squiggle.types.values.BooleanTypeValue;
import com.squiggle.types.values.FloatTypeValue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EqualityLogicTests {

        @AfterEach
        public void tearDown() {
                BooleanTypeValue.setAsText(false);

        }

        @BeforeAll
        public static void setup() {
                FloatTypeValue.setAsDotSeparated();
        }

        @Test
        public void simpleStringStringInequality() {
                LogicBuilder lg = new LogicBuilder().that("arg1").equals("arg2");
                assertEquals("'arg1' = 'arg2'", lg.toString());
        }

        @Test
        public void simpleStringIntegerInequality() {
                LogicBuilder lg = new LogicBuilder().that("arg1").equals(1);
                assertEquals("'arg1' = 1", lg.toString());
        }

        @Test
        public void simpleStringDoubleInequality() {
                LogicBuilder lg = new LogicBuilder().that("arg1").equals(1.0);
                assertEquals("'arg1' = 1.0", lg.toString());
        }

        @Test
        public void simpleStringBooleanInequality() {
                LogicBuilder lg = new LogicBuilder().that("arg1").equals(true);
                assertEquals("'arg1' = 1", lg.toString());

                lg = new LogicBuilder().that("arg1").equals(false);
                assertEquals("'arg1' = 0", lg.toString());
        }

        @Test
        public void simpleIntegerStringInequality() {
                LogicBuilder lg = new LogicBuilder().that(1).equals("arg2");
                assertEquals("1 = 'arg2'", lg.toString());
        }

        @Test
        public void simpleIntegerIntegerInequality() {
                LogicBuilder lg = new LogicBuilder().that(1).equals(1);
                assertEquals("1 = 1", lg.toString());
        }

        @Test
        public void simpleIntegerDoubleInequality() {
                LogicBuilder lg = new LogicBuilder().that(1).equals(1.0);
                assertEquals("1 = 1.0", lg.toString());
        }

        @Test
        public void simpleIntegerBooleanInequality() {
                LogicBuilder lg = new LogicBuilder().that(1).equals(true);
                assertEquals("1 = 1", lg.toString());

                lg = new LogicBuilder().that(1).equals(false);
                assertEquals("1 = 0", lg.toString());
        }

        @Test
        public void simpleDoubleStringInequality() {
                LogicBuilder lg = new LogicBuilder().that(1.0).equals("arg2");
                assertEquals("1.0 = 'arg2'", lg.toString());
        }

        @Test
        public void simpleDoubleIntegerInequality() {
                LogicBuilder lg = new LogicBuilder().that(1.0).equals(1);
                assertEquals("1.0 = 1", lg.toString());
        }

        @Test
        public void simpleDoubleDoubleInequality() {
                LogicBuilder lg = new LogicBuilder().that(1.0).equals(1.0);
                assertEquals("1.0 = 1.0", lg.toString());
        }

        @Test
        public void simpleDoubleBooleanInequality() {
                LogicBuilder lg = new LogicBuilder().that(1.0).equals(true);
                assertEquals("1.0 = 1", lg.toString());

                lg = new LogicBuilder().that(1.0).equals(false);
                assertEquals("1.0 = 0", lg.toString());
        }

        @Test
        public void simpleBooleanStringInequality() {
                LogicBuilder lg = new LogicBuilder().that(true).equals("arg2");
                assertEquals("1 = 'arg2'", lg.toString());
        }

        @Test
        public void simpleBooleanIntegerInequality() {
                LogicBuilder lg = new LogicBuilder().that(true).equals(1);
                assertEquals("1 = 1", lg.toString());
        }

        @Test
        public void simpleBooleanFloatInequality() {
                LogicBuilder lg = new LogicBuilder().that(true).equals(1.0);
                assertEquals("1 = 1.0", lg.toString());
        }

        @Test
        public void simpleBooleanBooleanInequality() {
                LogicBuilder lg = new LogicBuilder().that(true).equals(true);
                assertEquals("1 = 1", lg.toString());

                lg = new LogicBuilder().that(true).equals(false);
                assertEquals("1 = 0", lg.toString());
        }

        // Simple booleans as text

        @Test
        public void simpleBooleanAsTextStringInequality() {
                BooleanTypeValue.setAsText(true);
                LogicBuilder lg = new LogicBuilder().that(true).equals("arg2");
                assertEquals("true = 'arg2'", lg.toString());
                BooleanTypeValue.setAsText(false);
        }

        @Test
        public void simpleBooleanAsTextIntegerInequality() {
                BooleanTypeValue.setAsText(true);
                LogicBuilder lg = new LogicBuilder().that(true).equals(1);
                assertEquals("true = 1", lg.toString());
                BooleanTypeValue.setAsText(false);
        }

        @Test
        public void simpleBooleanAsTextFloatInequality() {
                BooleanTypeValue.setAsText(true);
                LogicBuilder lg = new LogicBuilder().that(true).equals(1.0);
                assertEquals("true = 1.0", lg.toString());
                BooleanTypeValue.setAsText(false);
        }

        @Test
        public void simpleBooleanAsTextBooleanInequality() {
                BooleanTypeValue.setAsText(true);
                LogicBuilder lg = new LogicBuilder().that(true).equals(true);
                assertEquals("true = true", lg.toString());
                BooleanTypeValue.setAsText(false);
        }

        // Specific float types

        @Test
        public void simpleFloatStringInequality() {
                FloatTypeValue.setFormat("0.000000");
                LogicBuilder lg = new LogicBuilder().that(1.0f).equals("arg2");
                assertEquals("1.000000 = 'arg2'", lg.toString());
        }

        @Test
        public void simpleFloatIntegerInequality() {
                FloatTypeValue.setFormat("0.000000");
                LogicBuilder lg = new LogicBuilder().that(1.0f).equals(1);
                assertEquals("1.000000 = 1", lg.toString());
        }

        @Test
        public void simpleFloatDoubleInequality() {
                FloatTypeValue.setFormat("0.000000");
                LogicBuilder lg = new LogicBuilder().that(1.0f).equals(1.0);
                assertEquals("1.000000 = 1.0", lg.toString());
        }

        @Test
        public void simpleFloatBooleanInequality() {
                FloatTypeValue.setFormat("0.000000");
                LogicBuilder lg = new LogicBuilder().that(1.0f).equals(true);
                assertEquals("1.000000 = 1", lg.toString());
        }

        @Test
        public void simpleFloatFloatInequality() {
                FloatTypeValue.setFormat("0.000000");
                LogicBuilder lg = new LogicBuilder().that(1.0f).equals(1.0f);
                assertEquals("1.000000 = 1.000000", lg.toString());
        }

}
