package Members;

import java.io.File;
import java.io.PrintWriter;

//@Amanda
public class AddMember {
    public void AddMember() {
        try {
            File membersFile = new File("Files/Members");
            PrintWriter out = new PrintWriter(membersFile);

            out.println();

            out.close();
            System.out.println("Medlem blev tilføjet");
        }
        catch (Exception e){
            System.out.println("Der skete en fejl. Medlemmet blev ikke tilføjet.");
        }


    }
}
