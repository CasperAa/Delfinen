package Members;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

//@Amanda

class MemberTest {


    @Test
    void isValidBirthdate() {

        //Arrange
        Member member = new Member(null,null,null,null,null,
                null,null,null,null,false);

        //Act

        //Opfylder alle krav
        boolean trueResult1 = member.isValidBirthdate("03042000");

        //For kort (længde=7)
        boolean falseResult1 = member.isValidBirthdate("0304200");

        //For lang (længde=9)
        boolean falseResult2 = member.isValidBirthdate("030420000");

        //Indeholder et bogstav
        boolean falseResult3 = member.isValidBirthdate("0304200o");

        //Dato er for høj (over 31)
        boolean falseResult4 = member.isValidBirthdate("32042000");

        //Måned er for høj (over 12)
        boolean falseResult5 = member.isValidBirthdate("03132000");

        //Dato er 00
        boolean falseResult6 = member.isValidBirthdate("00042000");

        //Måned er 00
        boolean falseResult7 = member.isValidBirthdate("03002000");

        //Årstal er for højt (alderen er under 6)
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("ddMMyyyy");
        String date = LocalDateTime.now().minusYears(5).format(formatTime);
        boolean falseResult8 = member.isValidBirthdate(date);

        //Årstal er for lavt (alderen er over 140)
        date = LocalDateTime.now().minusYears(141).format(formatTime);
        boolean falseResult9 = member.isValidBirthdate(date);

        //Alderen opfylder kravene (alderen er 15)
        date = LocalDateTime.now().minusYears(15).format(formatTime);
        boolean trueResult2= member.isValidBirthdate(date);

        //Assert
        assertTrue(trueResult1);
        assertFalse(falseResult1);
        assertFalse(falseResult2);
        assertFalse(falseResult3);
        assertFalse(falseResult4);
        assertFalse(falseResult5);
        assertFalse(falseResult6);
        assertFalse(falseResult7);
        assertFalse(falseResult8);
        assertFalse(falseResult9);
        assertTrue(trueResult2);

    }

    @Test
    void isValidPhoneNo() {

        //Arrange
        Member member = new Member(null,null,null,null,null,
                null,null,null,null,false);

        //Act


        //Opfylder alle krav
        boolean trueResult1 = member.isValidPhoneNo("70121416");

        //For kort (længde=7)
        boolean falseResult1 = member.isValidPhoneNo("7012141");

        //For lang (længde=9)
        boolean falseResult2 = member.isValidPhoneNo("701214166");

        //Indeholder et bogstav
        boolean falseResult3 = member.isValidPhoneNo("7012141n");

        //Assert
        assertTrue(trueResult1);
        assertFalse(falseResult1);
        assertFalse(falseResult2);
        assertFalse(falseResult3);

    }


    @Test

    void isValidEmail() {
        //Arrange
        Member member = new Member(null,null,null,null,null,
                null,null,null,null,false);

        //Act


        //Opfylder alle krav
        boolean trueResult1 = member.isValidEmail("eksempel@eksempel.dk");

        //Indeholder ikke @
        boolean falseResult1 = member.isValidEmail("eksempeleksempel.dk");

        //Indeholder ikke .dk/.com/.net/.co.uk/.gov
        boolean falseResult2 = member.isValidEmail("eksempel@eksempel");

        //Assert
        assertTrue(trueResult1);
        assertFalse(falseResult1);
        assertFalse(falseResult2);
    }

    @Test
    void isValidStartDate() {

        //Arrange
        Member member = new Member(null,null,null,null,null,
                null,null,null,null,false);


        //Opfylder alle krav
        boolean trueResult1 = member.isValidStartDate("10022020");

        //For kort (længde=7)
        boolean falseResult1 = member.isValidStartDate("1002202");

        //For lang (længde=9)
        boolean falseResult2 = member.isValidStartDate("100220200");

        //Indeholder et bogstav
        boolean falseResult3 = member.isValidStartDate("1002202o");

        //Dato er for høj (over 31)
        boolean falseResult4 = member.isValidStartDate("32022020");

        //Måned er for høj (over 12)
        boolean falseResult5 = member.isValidStartDate("10132020");

        //Dato er 00
        boolean falseResult6 = member.isValidStartDate("00022020");

        //Måned er 00
        boolean falseResult7 = member.isValidStartDate("10002020");

        //Årstal er for lavt (under 2005)
        boolean falseResult8 = member.isValidStartDate("10022004");

        //Assert
        assertTrue(trueResult1);
        assertFalse(falseResult1);
        assertFalse(falseResult2);
        assertFalse(falseResult3);
        assertFalse(falseResult4);
        assertFalse(falseResult5);
        assertFalse(falseResult6);
        assertFalse(falseResult7);
        assertFalse(falseResult8);
    }

    @Test
    void isNumeric() {
        //Arrange
        Member member = new Member(null,null,null,null,null,
                null,null,null,null,false);

        //Act


        //Opfylder alle krav
        boolean trueResult1 = member.isNumeric("1234567890");

        //Indeholder et bogstav
        boolean falseResult1 = member.isNumeric("123456789o");

        //Assert
        assertTrue(trueResult1);
        assertFalse(falseResult1);
    }

    @Test

    void isValidName() {
        //Arrange
        Member member = new Member(null,null,null,null,null,
                null,null,null,null,false);

        //Act


        //Opfylder alle krav (og indeholder alle typer tilladte tegn)
        boolean trueResult1 = member.isValidName("Anne-Mette Åse B. Andersen");

        //Indeholder tal
        boolean falseResult1 = member.isValidName("Frederik d. 6.");

        //Indeholder specialtegn
        boolean falseResult2 = member.isValidName("eksempel@eksempel.dk");

        //Assert
        assertTrue(trueResult1);
        assertFalse(falseResult1);
        assertFalse(falseResult2);
    }

    @Test

    void alreadyExistsInFile() throws FileNotFoundException {

        //Arrange
        Member member = new Member(null, null, null, null, null,
                null, null, null, null, false);

        //Act

        File testFile = new File("src/Unit-Test/Members/TestFile");

        //Denne String eksisterer i filen på det givne index
        boolean trueResult1 = member.alreadyExistsInFile("0001", testFile, 1);

        //Denne String eksisterer ikke i filen på det givne index
        boolean falseResult1 = member.alreadyExistsInFile("0003", testFile, 1);


        //Assert
        assertTrue(trueResult1);
        assertFalse(falseResult1);
    }

    @Test

    void decideMemberType() {
        //Arrange
        Member member = new Member(null,null,null,null,null,
                null,null,null,null,false);

        //Act

        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("ddMMyyyy");

        //Giver junior som output (alder = 17 år)
        String birthdate = LocalDateTime.now().minusYears(17).format(formatTime);
        String juniorResult = member.decideMemberType(birthdate);

        //Giver senior som output (alder = 18 år)
        birthdate = LocalDateTime.now().minusYears(18).format(formatTime);
        String seniorResult = member.decideMemberType(birthdate);


        //Assert
        assertEquals("junior", juniorResult);
        assertEquals("senior", seniorResult);
    }

    @Test

    void decideIDNumber() {
        //Arrange
        Member member = new Member(null,null,null,null,null,
                null,null,null,null,false);

        //Act
        File testFile = new File("src/Unit-Test/Members/TestFile");
        File testFileEmpty = new File("src/Unit-Test/Members/TestFileEmpty");

        //String-output er 0003, da det højeste ID i filen er 0002
        String result1 = member.decideIDNumber(testFile);

        //String-output er 0001, da der ikke findes noget ID i filen
        String result2 = member.decideIDNumber(testFileEmpty);

        //Assert
        assertEquals("0003", result1);
        assertEquals("0001", result2);
    }

}