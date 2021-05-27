package Results;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@Casper
class ResultsTest {

    @Test
    void addResult() {

        //Arrange
        Result result = new Result(0, null, null, null, null, null);

        //Act

            //Correct Date
            boolean trueResult1 = result.dateFormatValidator("03/04/2020");

            //Wrong symbol
            boolean falseResult1 = result.dateFormatValidator("03/04:2020");

            //Year doesn't correlate with it set four characters format
            boolean falseResult2 = result.dateFormatValidator("03/04/20223");

            //Day above max day
            boolean falseResult3 = result.dateFormatValidator("32/04/2020");

            //month above 12
            boolean falseResult4 = result.dateFormatValidator("03/13/2020");

        //Assert
        assertTrue(trueResult1);
        assertFalse(falseResult1);
        assertFalse(falseResult2);
        assertFalse(falseResult3);
        assertFalse(falseResult4);


    }
}