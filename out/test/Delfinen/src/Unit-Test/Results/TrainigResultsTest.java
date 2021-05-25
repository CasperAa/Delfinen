package Results;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@Casper
class TrainigResultsTest {

    @Test
    void addResultTraining() {

        //Arrange
        TrainingResults trainigResults = new TrainingResults(0, null, null, null, null, null);

        //Act

            //Correct Date
            boolean trueResult1 = trainigResults.dateValidation("03/04/2020");

            //Wrong symbol
            boolean falseResult1 = trainigResults.dateValidation("03/04:2020");

            //Year is after current year)
            boolean falseResult2 = trainigResults.dateValidation("03/04/20223");

            //Day above max day
            boolean falseResult3 = trainigResults.dateValidation("45/04/2020");

            //month above 12
            boolean falseResult4 = trainigResults.dateValidation("03/16/2020");

        //Assert
        assertTrue(trueResult1);
        assertFalse(falseResult1);
        assertFalse(falseResult2);
        assertFalse(falseResult3);
        assertFalse(falseResult4);


    }
}