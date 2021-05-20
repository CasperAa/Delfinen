package Members;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@Amanda

class MemberTest {

    @Test
    void addMemberToFile() {
    }

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
    }

    @Test
    void isHasPayed() {
    }

    @Test
    void isNumeric() {
    }
}