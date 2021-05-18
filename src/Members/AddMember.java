package Members;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

//@Amanda
public class AddMember {
    public void AddMember(String name,String birthdate,String memberStatus,String memberGroup,String memberType,String telephoneNo, String email, String startDate) {
        String ID;


        try {
            File membersFile = new File("Files/Members");
            FileWriter fw = new FileWriter("Files/Members",true);   //Filen bliver ikke overwrited.
            PrintWriter pw = new PrintWriter(membersFile);

            pw.println(name + ";" + ID + ";" + birthdate + ";" + memberStatus + ";" + memberGroup + ";" + memberType +
                    ";" + telephoneNo + ";" + email + ";" + startDate);   //Medlemmet skal tilføjes til filen
            pw.flush();     //Kan ikke forkare, hvad der sker her
            pw.close();     //Handlongen sker rent faktisk
            System.out.println("Medlem blev tilføjet");
        }
        catch (Exception e){
            System.out.println("Der skete en fejl. Medlemmet blev ikke tilføjet.");
        }


    }
}
