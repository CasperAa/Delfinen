package Members;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//@Amanda
public class Member {

    private final String name;
    private final String ID;
    private final String birthdate;
    private final String memberStatus;
    private final String memberGroup;
    private final String memberType;
    private final String telephoneNo;
    private final String email;
    private final String startDate;
    private final boolean hasPayed;

    //Constructor
    public Member(String name, String ID, String birthdate, String memberStatus, String memberGroup, String memberType,
                  String telephoneNo, String email, String startDate, boolean hasPayed) {
        this.name = name;
        this.ID = ID;
        this.birthdate = birthdate;
        this.memberStatus = memberStatus;
        this.memberGroup = memberGroup;
        this.memberType = memberType;
        this.telephoneNo = telephoneNo;
        this.email = email;
        this.startDate = startDate;
        this.hasPayed = hasPayed;

    }

    public static void memberReader() throws FileNotFoundException {
        File membersFile = new File("src/Files/MembersList");
        Scanner sc = new Scanner(membersFile);

        ArrayList<Member> memberList = new ArrayList<>();
        //Skipper metadatalinjen
        sc.nextLine();

        //While-loop, så alle linjer læses
        while (sc.hasNext()) {
            //En variabel, som indeholder den nuværende linje
            String currentPizza = sc.nextLine();

            String [] lineAsArray = currentPizza.split(";");

            String name = lineAsArray[0].trim();
            String ID = lineAsArray[1].trim();
            String birthdate = lineAsArray[2].trim();
            String memberStatus = lineAsArray[3].trim();
            String memberGroup = lineAsArray[4].trim();
            String memberType = lineAsArray[5].trim();
            String telephoneNo = lineAsArray[6].trim();
            String email = lineAsArray[7].trim();
            String startDate = lineAsArray[8].trim();
            boolean hasPayed = Boolean.parseBoolean(lineAsArray[9].trim());

            //En Pizza oprettes på baggrund af dataen fra den nuværende linje
            Member newMember = new Member(name, ID, birthdate, memberStatus, memberGroup, memberType,
                    telephoneNo, email, startDate, hasPayed);
            //Tilføjer pizzaen til menuen
            memberList.add(newMember);
        }

    }


    public static void AddingProcess() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Indtast medlemmets fulde navn:");
        String name = input.nextLine();
        System.out.println("Indtast medlemmets fødseldsdag (ddmmyyyy):");
        String birthdate = input.nextLine();
        System.out.println("Er medlemmet aktivt? 1: Ja 2: Nej");
        String memberStatus = null;
        switch (input.nextLine()) {
            case "1":
                memberStatus = "active";
                break;
            case "2":
                memberStatus = "passive";
            default:
                System.out.println("Input ikke forstået. Prøve igen.\nEr medlemmet aktivt? 1: Ja 2: Nej");
        }
        System.out.println("Hvad er medlemstypen? 1: Motionist 2: Konkurrencesvømmer");
        String memberGroup = null;
        switch (input.nextLine()) {
            case "1":
                memberGroup = "motonist";
                break;
            case "2":
                memberGroup = "konkurrence";
            default:
                System.out.println("Input ikke forstået. Prøve igen.\nHvad er medlemstypen? 1: Motionist 2: Konkurrencesvømmer");
        }
        System.out.println("Indtast medlemmets telefonnummer:");
        String telephoneNo = input.nextLine();

        System.out.println("Indtast medlemmets e-mail:");
        String email = input.nextLine();

        System.out.println("Sæt startdato til i dag? 1: Ja 2: Nej");
        String startDate = null;
        switch (input.nextLine()) {
            case "1":
                DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("ddMMyyyy");
                startDate = LocalDateTime.now().format(formatTime);
                break;
            case "2":
                System.out.println("Indtast startdato (ddMMyyyy)");
                startDate = input.nextLine();
            default:
                System.out.println("Input ikke forstået. Prøve igen.\nSæt startdato til i dag? 1: Ja 2: Nej");
        }
        System.out.println("Har medlemmet betalt kontingent? 1: Ja 2: Nej");
        boolean hasPayed = false;
        switch (input.nextLine()) {
            case "1":
                hasPayed = true;
                break;
            case "2":
                hasPayed = false;
            default:
                System.out.println("Input ikke forstået. Prøve igen.\nHar medlemmet betalt kontingent? 1: Ja 2: Nej");
        }
        MemberAdder(name, birthdate, memberStatus, memberGroup, telephoneNo, email, startDate, hasPayed);
    }


    private static ArrayList<Integer> IDListe;

    public static void MemberAdder(String name, String birthdate, String memberStatus, String memberGroup,
                                   String telephoneNo, String email, String startDate, boolean hasPayed) throws IOException {

        try {



            //Nedenstående bestemmer et ID-nummer, der er én højere end det hidtil højeste ID.
            int ID;

            File membersFile = new File("src/Files/MembersList");
            Scanner sc = new Scanner(membersFile);

            IDListe = new ArrayList<Integer>();
            //Skipper metadata linjen
            sc.nextLine();

            //while loop for alle linjer
            while (sc.hasNext()) {

                String currentMember = sc.nextLine();

                String[] lineAsArray = currentMember.split(";");
                int currentID = Integer.parseInt(lineAsArray[1].trim());

                IDListe.add(currentID);
            }
            String tempID = "000" + (Collections.max(IDListe) + 1);
            ID = Integer.parseInt(tempID.substring(tempID.length() - 4));
            //ID-metoden er slut

            //Nedenstående bestemmer, om medlemmet er junior eller senior
            DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("ddMMyyyy");
            String currentDate = LocalDateTime.now().format(formatTime);
            //Jeg laver den senere
            String memberType = "senior"; //Midlertidig værdi

            FileWriter fw = new FileWriter(membersFile, true);   //Filen bliver ikke overwritten.
            BufferedWriter bw = new BufferedWriter(fw);
            //PrintWriter pw = new PrintWriter(membersFile);

            bw.write("\n" + name + ";" + ID + ";" + birthdate + ";" + memberStatus + ";" + memberGroup + ";" + memberType +
                    ";" + telephoneNo + ";" + email + ";" + startDate + ";" + hasPayed);   //Medlemmet skal tilføjes til filen
            bw.close();     //Handlingen sker rent faktisk
            System.out.println("Medlem blev tilføjet");
        } catch (Exception e) {
            System.out.println("Der skete en fejl. Medlemmet blev ikke tilføjet.");
        }




    }


    public static void main(String[] args) throws IOException {
        AddingProcess();
    }

}
