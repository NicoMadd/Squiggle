package Other.Logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.builders.LogicBuilder;
import com.squiggle.types.values.BooleanTypeValue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class OrLogicTests {

    @AfterEach
    public void tearDown() {
        BooleanTypeValue.setAsText(false);
    }

    @Test
    public void simpleStringStringOr() {
        LogicBuilder lg = new LogicBuilder().that("arg1").equals("arg2").or("arg3").equals("arg4");
        assertEquals("'arg1' = 'arg2' OR 'arg3' = 'arg4'", lg.toString());
    }

    @Test
    public void simpleStringIntegerOr() {
        LogicBuilder lg = new LogicBuilder().that("arg1").equals(1).or(2).equals(3);
        assertEquals("'arg1' = 1 OR 2 = 3", lg.toString());
    }

    @Test
    public void simpleStringDoubleOr() {
        LogicBuilder lg = new LogicBuilder().that("arg1").equals(1.0).or(2.0).equals(3.0);
        assertEquals("'arg1' = 1.0 OR 2.0 = 3.0", lg.toString());
    }

    @Test
    public void simpleStringBooleanOr() {
        LogicBuilder lg = new LogicBuilder().that("arg1").equals(true).or(false).equals(true);
        assertEquals("'arg1' = 1 OR 0 = 1", lg.toString());
    }

    @Test
    public void simpleStringBooleanOrAsText() {
        BooleanTypeValue.setAsText(true);
        LogicBuilder lg = new LogicBuilder().that("arg1").equals(true).or(false).equals(true);
        assertEquals("'arg1' = true OR false = true", lg.toString());
        BooleanTypeValue.setAsText(false);
    }

    @Test
    public void simpleStringFloatOr() {
        LogicBuilder lg = new LogicBuilder().that("arg1").equals(1.0f).or(2.0f).equals(3.0f);
        assertEquals("'arg1' = 1.000000 OR 2.000000 = 3.000000", lg.toString());
    }

}
