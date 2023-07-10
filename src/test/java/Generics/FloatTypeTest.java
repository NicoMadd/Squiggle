package Generics;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

import com.squiggle.Squiggle;
import com.squiggle.output.Output;
import com.squiggle.parsers.SqlServerParser;
import com.squiggle.queries.SelectQuery;
import com.squiggle.types.values.FloatTypeValue;

public class FloatTypeTest {

    @BeforeAll
    public static void setUp() {
        Squiggle.setParser(new SqlServerParser());
    }

    @Test
    public void dotSeparatedFloat() {
        FloatTypeValue.setAsDotSeparated();
        FloatTypeValue value = new FloatTypeValue(1.234f);

        Output output = new Output("");
        value.write(output);
        
        System.out.println(output.toString());
        assertEquals("1.234", output.toString());
    }

    @Test
    public void commaSeparatedFloat() {
        FloatTypeValue.setAsCommaSeparated();
        FloatTypeValue value = new FloatTypeValue(1.234f);
        Output output = new Output("");
        value.write(output);
        assertEquals("1,234", output.toString());
    }

    @AfterAll
    public static void tearDown() {
        Squiggle.setParser(null);
    }

}
