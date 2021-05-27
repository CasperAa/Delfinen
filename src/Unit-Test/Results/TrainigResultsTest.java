package Results;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@Casper
class TrainigResultsTest {

    @Test
    void addResult() {

        //Arrange
        Result result = new Result(0, null, null, null, null, null);

        //Act

            //Correct Date
            boolean trueResult1 = Result.dateFormatValidator("03/04/2020");

            //Wrong symbol
            boolean falseResult1 = Result.dateFormatValidator("03/04:2020");

            //Year is after current year)
            boolean falseResult2 = Result.dateFormatValidator("03/04/20223");

            //Day above max day
            boolean falseResult3 = Result.dateFormatValidator("45/04/2020");

            //month above 12
            boolean falseResult4 = Result.dateFormatValidator("03/16/2020");

        //Assert
        assertTrue(trueResult1);
        assertFalse(falseResult1);
        assertFalse(falseResult2);
        assertFalse(falseResult3);
        assertFalse(falseResult4);


    }
}