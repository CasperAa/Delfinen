package Members;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@Amanda

class MemberTest {

    @Test
    void addMembersFromFileToArray() {
    }

    @Test
    void addAttributesToNewMemberAndAddMemberToFile() {
    }

    @Test
    void addMemberToFile() {
    }

    @Test
    void editMemberInfo() {
    }

    @Test
    void addName() {
        //Arrange

        //Act
        String test1 = Member.addName();
        String test2 = LetterToNumberEncryption.fromStringToNumber("Hello World");
        String test3 = LetterToNumberEncryption.fromStringToNumber("Det er Amanda");

        //Assert
        //Asserting expected against actual results

        //Legal age
        assertEquals("Legal age",legalAge);

        //Non legal age
        assertEquals("not legal",illegalAge);
        assertEquals("not legal",illegalAge2);

        //Illegal input
        assertEquals("Illegal",illegalInput);
    }

    @Test
    void addBirthdate() {
    }

    @Test
    void addActivityStatus() {
    }

    @Test
    void addMemberType() {
    }

    @Test
    void addPhoneNo() {
    }

    @Test
    void addEmail() {
    }

    @Test
    void addStartDate() {
    }

    @Test
    void addPaymentStatus() {
    }

    @Test
    void isHasPayed() {
    }

    @Test
    void isNumeric() {
    }
}