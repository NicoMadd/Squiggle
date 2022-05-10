package Other.Logic.Outputables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.builders.LogicBuilder;
import com.squiggle.output.Output;
import com.squiggle.output.Outputable;

import org.junit.jupiter.api.Test;

class OutputableStub implements Outputable {

        String stubStr;

        public OutputableStub(String stubStr) {
                this.stubStr = stubStr;
        }

        @Override
        public void write(Output out) {
                out.print(stubStr);
        }

}

public class OutputableLogicTests {

        @Test
        public void simpleOutputableInequality() {
                LogicBuilder lg = new LogicBuilder().that(new OutputableStub("arg1")).not(new OutputableStub("arg2"));
                assertEquals("arg1 != arg2", lg.toString());

        }

        @Test
        public void simpleOutputableEquality() {
                LogicBuilder lg = new LogicBuilder().that(new OutputableStub("arg1"))
                                .equals(new OutputableStub("arg2"));
                assertEquals("arg1 = arg2", lg.toString());
        }

        @Test
        public void simpleOutputableOr() {
                LogicBuilder lg = new LogicBuilder().that(new OutputableStub("arg1")).equals(new OutputableStub("arg2"))
                                .or(new OutputableStub("arg3")).equals(new OutputableStub("arg4"));
                assertEquals("arg1 = arg2 OR arg3 = arg4", lg.toString());

        }

        @Test
        public void simpleOutputableAnd() {
                LogicBuilder lg = new LogicBuilder().that(new OutputableStub("arg1"))
                                .equals(new OutputableStub("arg2")).and(new OutputableStub("arg3"))
                                .equals(new OutputableStub("arg4"));
                assertEquals("arg1 = arg2 AND arg3 = arg4", lg.toString());
        }

        @Test
        public void complexOutputableAnd() {
                LogicBuilder lg = new LogicBuilder().that(new OutputableStub("arg1")).equals(new OutputableStub("arg2"))
                                .and(new OutputableStub("arg3")).not(new OutputableStub("arg4"));
                assertEquals("arg1 = arg2 AND arg3 != arg4", lg.toString());
        }

        @Test
        public void multipleOutputableAnd() {
                LogicBuilder lg = new LogicBuilder().that(new OutputableStub("arg1")).equals(new OutputableStub("arg2"))
                                .and(new OutputableStub("arg3")).not(new OutputableStub("arg4"))
                                .and(new OutputableStub("arg5")).equals(new OutputableStub("arg6"));
                assertEquals("arg1 = arg2 AND arg3 != arg4 AND arg5 = arg6", lg.toString());
        }

}
