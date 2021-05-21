package Members;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

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

        //Årstal er for højt (over 2015)
        boolean falseResult8 = member.isValidBirthdate("03042016");

        //Årstal er for lavt (under 1900)
        boolean falseResult9 = member.isValidBirthdate("03041899");

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

    }

    @Test
    void isValidPhoneNo() {
        //telephoneNo.length() != 8 || !isNumeric(telephoneNo)

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

        //Indeholder ikke .com/.net/.co.uk/.gov
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

        //Årstal er for højt (over 2021)
        boolean falseResult8 = member.isValidStartDate("10022022");

        //Årstal er for lavt (under 2005)
        boolean falseResult9 = member.isValidStartDate("10022004");

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
        Member member = new Member(null,null,null,null,null,
                null,null,null,null,false);

        //Act

        File membersFile = new File("src/Files/MembersList");

        //True
        boolean trueResult1 = member.alreadyExistsInFile("70121416", membersFile, 6);

        //False
        boolean falseResult1 = member.alreadyExistsInFile("12345678", membersFile, 6);


        //Assert
        assertTrue(trueResult1);
        assertFalse(falseResult1);
    }


}