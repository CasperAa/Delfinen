package Members;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

//@Amanda
public class Members {
    private ArrayList<Integer> IDListe;

    public void AddMember(String name, String birthdate, String memberStatus, String memberGroup, String memberType,
                          String telephoneNo, String email, String startDate) {
        try {
            int ID;

            File membersFile = new File("Files/MembersList");
            Scanner sc = new Scanner(membersFile);

            IDListe = new ArrayList<Integer>();
            //Skipper metadata linjen
            sc.nextLine();

            //while loop for alle linjer
            while (sc.hasNext()) {

                String currentMember = sc.nextLine();

                String [] lineAsArray = currentMember.split(";");
                int currentID = Integer.parseInt(lineAsArray[1].trim());

                IDListe.add(currentID);
            }


            FileWriter fw = new FileWriter("Files/MembersList",true);   //Filen bliver ikke overwrited.
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
