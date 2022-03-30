package QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squiggle.Squiggle;
import com.squiggle.queries.TryCatchQuery;

import org.junit.jupiter.api.Test;

public class TryCatchQueryTest {

        @Test
        public void simpleTryCatch(){
                TryCatchQuery tcQuery = Squiggle.tryCatchQuery()
                                        .tryInsert(query -> query.into("table")
                                        .to("column1").to("column2")
                                        .value(2).value(2));
        }
}
