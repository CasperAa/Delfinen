package Members;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static Results.Results.generateCsvFile;
import static com.sun.org.apache.xml.internal.utils.XMLCharacterRecognizer.isWhiteSpace;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;


//@Amanda
public class Member {

    protected String name;
    protected final String ID;
    protected String birthdate;
    protected String memberStatus;
    protected String memberGroup;
    protected String memberType;
    protected String telephoneNo;
    protected String email;
    protected final String startDate;
    protected boolean hasPayed;
    static ArrayList<Member> memberList = new ArrayList<Member>();
    static ArrayList<Trainer> trainerList = new ArrayList<Trainer>();
    static ArrayList<Member> currentTrainerList = new ArrayList<Member>();

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

    public static void readMembersFromFileAndAddToArray() throws FileNotFoundException {
        File membersFile = new File("src/Files/MembersList");
        Scanner sc = new Scanner(membersFile);


        //Skipper metadatalinjen
        sc.nextLine();


        //While-loop, så alle linjer læses
        while (sc.hasNext()) {

            //En variabel, som indeholder den nuværende linje
            String currentMember = sc.nextLine();

            String[] lineAsArray = currentMember.split(";");

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

            if (memberGroup.equalsIgnoreCase("motionist")) {
                Member newMember = new Member(name, ID, birthdate, memberStatus, memberGroup, memberType,
                        telephoneNo, email, startDate, hasPayed);
                memberList.add(newMember);
            } else if (memberGroup.equalsIgnoreCase("konkurrence")) {
                CompetitionMember newMember = new CompetitionMember(name, ID, birthdate, memberStatus, memberGroup,
                        memberType, telephoneNo, email, startDate, hasPayed);
                memberList.add(newMember);
            } else {
                System.out.println("Fejl i tilføjelse af medlem.");
            }
        }
    }

    public static void readTrainersFromFileAndAddToArray() throws FileNotFoundException {
        File membersFile = new File("src/Files/TrainerList");
        Scanner sc = new Scanner(membersFile);


        //Skipper metadatalinjen
        sc.nextLine();


        //While-loop, så alle linjer læses
        while (sc.hasNext()) {

            //En variabel, som indeholder den nuværende linje
            String currentMember = sc.nextLine();

            String[] lineAsArray = currentMember.split(";");

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
            String discipline = lineAsArray[10].trim();

            Trainer newMember = new Trainer(name, ID, birthdate, memberStatus, memberGroup,
                    memberType, telephoneNo, email, startDate, hasPayed, discipline);
            trainerList.add(newMember);
        }
    }


    public static void writeNewMember() throws IOException {
        Scanner sc = new Scanner(System.in);

        String name = addName();

        String birthdate = addBirthdate();

        String memberStatus = addActivityStatus();

        String memberGroup = addMemberType();

        String telephoneNo = addPhoneNo();

        String email = addEmail();

        String startDate = addStartDate();

        boolean hasPayed = addPaymentStatus();

        addMemberToFile(name, birthdate, memberStatus, memberGroup, telephoneNo, email, startDate, hasPayed);
    }


    private static ArrayList<Integer> IDListe;
    private static ArrayList<Integer> IDListeTrainer;

    public static void addMemberToFile(String name, String birthdate, String memberStatus, String memberGroup,
                                       String telephoneNo, String email, String startDate, boolean hasPayed) throws IOException {

        try {
            //Nedenstående tildeler et ID-nummer, der er én højere end det hidtil højeste ID.
            String ID;

            File membersFile = new File("src/Files/MembersList");
            Scanner sc = new Scanner(membersFile);

            IDListe = new ArrayList<Integer>();
            //Skipper metadata linjen
            sc.nextLine();

            //while loop for alle linjer
            while (sc.hasNext()) {

                String currentMember = sc.nextLine();

                String[] lineAsArray = currentMember.split(";");
                int currentID = parseInt(lineAsArray[1].trim());

                IDListe.add(currentID);
            }

            if (IDListe.size() != 0) {
                String tempID = "000" + (Collections.max(IDListe) + 1);
                ID = tempID.substring(tempID.length() - 4);
            } else {
                ID = "0001";
            }
            //ID-metoden er slut

            //Nedenstående afgør, om medlemmet er junior eller senior

            String memberType = null;

            int year = parseInt(birthdate.substring(birthdate.length() - 4));
            int month = parseInt(birthdate.substring(2, 4));
            int date = parseInt(birthdate.substring(0, 2));
            Period period = Period.between(LocalDate.of(year, month, date), LocalDate.now());
            int age = period.getYears();
            if (age >= 18) {
                memberType = "senior";
            } else {
                memberType = "junior";
            }

            //Filen redigeres
            FileWriter fw = new FileWriter(membersFile, true);   //Filen bliver ikke overwritten.
            BufferedWriter bw = new BufferedWriter(fw);
            //PrintWriter pw = new PrintWriter(membersFile);

            bw.write("\n" + name + ";" + ID + ";" + birthdate + ";" + memberStatus + ";" + memberGroup + ";"
                    + memberType +
                    ";" + telephoneNo + ";" + email + ";" + startDate + ";" + hasPayed);   //Medlemmet skal tilføjes til filen
            bw.close();     //Handlingen sker rent faktisk
            System.out.println("Medlem blev tilføjet");
        } catch (Exception e) {
            System.out.println("Der skete en fejl. Medlemmet blev ikke tilføjet.");
        }

    }

    public static void writeNewTrainer() throws IOException {
        Scanner sc = new Scanner(System.in);

        String name = addName();

        String birthdate = addBirthdate();

        String memberStatus = addActivityStatus();

        String memberType = addMemberType();

        String telephoneNo = addPhoneNo();

        String email = addEmail();

        String startDate = addStartDate();

        String discipline = addDiscipline();


        addTrainerToFile(name, birthdate, memberStatus, memberType, telephoneNo, email, startDate, discipline);
    }


    public static void addTrainerToFile(String name, String birthdate, String memberStatus, String memberType,
                                        String telephoneNo, String email, String startDate, String discipline) throws IOException {

        try {
            //Nedenstående tildeler et ID-nummer, der er én højere end det hidtil højeste ID.
            String ID;

            File trainersFile = new File("src/Files/TrainerList");
            Scanner sc = new Scanner(trainersFile);

            IDListeTrainer = new ArrayList<Integer>();
            //Skipper metadata linjen
            sc.nextLine();

            //while loop for alle linjer
            while (sc.hasNext()) {

                String currentTrainer = sc.nextLine();

                String[] lineAsArray = currentTrainer.split(";");
                int currentID = parseInt(lineAsArray[1].trim());

                IDListeTrainer.add(currentID);
            }
            if (IDListeTrainer.size() != 0) {
                String tempID = "000" + (Collections.max(IDListeTrainer) + 1);
                ID = tempID.substring(tempID.length() - 4);
            } else {
                ID = "0001";
            }
            //ID-metoden er slut

            //Filen redigeres
            FileWriter fw = new FileWriter(trainersFile, true);   //Filen bliver ikke overwritten.
            BufferedWriter bw = new BufferedWriter(fw);
            //PrintWriter pw = new PrintWriter(membersFile);

            bw.write("\n" + name + ";" + ID + ";" + birthdate + ";" + memberStatus + ";" + "trainer" + ";"
                    + memberType +
                    ";" + telephoneNo + ";" + email + ";" + startDate + ";" + false + ";" + discipline);   //Træneren skal tilføjes til filen
            bw.close();     //Handlingen sker rent faktisk
            System.out.println("Medlem blev tilføjet");
        } catch (Exception e) {
            System.out.println("Der skete en fejl. Medlemmet blev ikke tilføjet.");
        }

    }

    public static String addDiscipline(){
        Scanner input = new Scanner(System.in);
        System.out.println("Hvilken disciplin skal tilknyttes træneren? 1: Butterfly 2: Crawl 3: Rygcrawl 4: Brystsvømning");
        String discipline = null;
        String userInput = input.nextLine();
        boolean end = false;
        while (!end) {
            switch (userInput) {
                case "1":
                    discipline = "butterfly";
                    end = true;
                    break;
                case "2":
                    discipline = "crawl";
                    end = true;
                    break;
                case "3":
                    discipline = "rygcrawl";
                    end = true;
                    break;
                case "4":
                    discipline = "brystsvømning";
                    end = true;
                    break;
                default:
                    System.out.println("Input ikke forstået. Prøv igen.\nHvilken disciplin skal tilknyttes træneren? 1: Butterfly 2: Crawl 3: Rygcrawl 4: Brystsvømning");
                    userInput = input.nextLine();
                    break;
            }
        }
        return discipline;
    }


    public static void editMemberInfo() throws IOException {
        readMembersFromFileAndAddToArray();
        Scanner input = new Scanner(System.in);
        Member memberToEdit = null;
        System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
        String userInput = input.nextLine();
        boolean matchFound = false;
        int lineNumber = 0;
        boolean end = false;
        while (!end) {

            switch (userInput) {
                case "1":
                    System.out.println("Hvad er ID-nummeret på det medlem, du vil redigere?");
                    String inputID = input.nextLine();
                    int lineCounter = 0;

                    for (Member currentMember : memberList) {
                        String currentID = currentMember.getID();
                        if (currentID.equals(inputID)) {
                            System.out.println("Vil du ændre følgende medlem?\n" + currentMember.getID() + ", " +
                                    currentMember.getName() + "\n1: Ja 2: Nej");
                            userInput = input.nextLine();
                            switch (userInput) {
                                case "1":
                                    memberToEdit = currentMember;
                                    lineNumber = lineCounter;
                                    end = true;
                                    break;
                                case "2":
                                    System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                                    break;
                            }
                            matchFound = true;
                        }
                        lineCounter++;
                    }
                    if (!matchFound) {
                        System.out.println("Der blev ikke fundet et match for ID'et. Prøv igen.");
                    }
                    break;
                case "2":
                    System.out.println("Hvad er navnet på det medlem, du vil redigere?");
                    String inputName = input.nextLine();
                    lineCounter = 0;
                    for (Member currentMember : memberList) {
                        if (currentMember.getName().toLowerCase().contains(inputName.toLowerCase())) {
                            System.out.println("Vil du ændre følgende medlem?\n\n---ID: " + currentMember.getID() + "; Navn: " +
                                    currentMember.getName() + "---\n\n1: Ja 2: Nej");
                            userInput = input.nextLine();
                            switch (userInput) {
                                case "1":
                                    memberToEdit = currentMember;
                                    lineNumber = lineCounter;
                                    end = true;
                                    break;
                                case "2":
                                    System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                                    break;
                            }
                            matchFound = true;
                        }
                        lineCounter++;
                    }
                    if (!matchFound) {
                        System.out.println("Der blev ikke fundet et match for navnet. Prøv igen.");
                    }
                    break;
                default:
                    System.out.println("Input ikke forstået. Prøv igen.\nVil du søge efter ID eller navn? 1: ID 2: Navn");
                    userInput = input.nextLine();
                    break;
            }
        }

        String name = memberToEdit.name;
        String ID = memberToEdit.ID;
        String birthdate = memberToEdit.birthdate;
        String memberStatus = memberToEdit.memberStatus;
        String memberGroup = memberToEdit.memberGroup;
        String memberType = memberToEdit.memberType;
        String telephoneNo = memberToEdit.telephoneNo;
        String email = memberToEdit.email;
        String startDate = memberToEdit.startDate;
        boolean hasPayed = memberToEdit.hasPayed;

        input = new Scanner(System.in);
        end = false;
        while (!end) {
            System.out.println("Hvad vil du ændre?\n1: Navn\n2: Fødselsdato\n3: Medlemsstatus\n4: Medlemsgruppe" +
                    "\n5: Telefonnummer\n6: e-mail\n7: Startdato\n8: Betalingsstatus");
            userInput = input.nextLine();
            switch (userInput) {
                case "1":
                    name = addName();
                    break;
                case "2":
                    birthdate = addBirthdate();
                    break;
                case "3":
                    memberStatus = addActivityStatus();
                    break;
                case "4":
                    memberGroup = addMemberType();
                    break;
                case "5":
                    telephoneNo = addPhoneNo();
                    break;
                case "6":
                    email = addEmail();
                    break;
                case "7":
                    startDate = addStartDate();
                    break;
                case "8":
                    hasPayed = addPaymentStatus();
                    break;
                default:
                    System.out.println("Input ikke forstået.");
                    break;
            }

            System.out.println("Er du færdig med at redigere medlemmer? 1: Ja 2: Nej");
            String userInput2 = input.nextLine();
            boolean end2 = false;
            while (!end2) {
                switch (userInput2) {
                    case "1":
                        end = true;
                        end2 = true;
                        break;
                    case "2":
                        end2 = true;
                        break;
                    default:
                }
            }
        }
        Member updatedMember = new Member(name, ID, birthdate, memberStatus, memberGroup, memberType,
                telephoneNo, email, startDate, hasPayed);
        memberList.set(lineNumber, updatedMember);


        File membersFile = new File("src/Files/MembersList");
        FileWriter fw = new FileWriter(membersFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(membersFile);
        pw.write("name;ID;birthdate;memberStatus;memberGroup;memberType;telephoneNo;email;startDate;hasPayed");
        for (Member currentMember : memberList) {
            pw.write("\n" + currentMember.name + ";" + currentMember.ID + ";" + currentMember.birthdate + ";" +
                    currentMember.memberStatus + ";" + currentMember.memberGroup + ";" + currentMember.memberType +
                    ";" + currentMember.telephoneNo + ";" + currentMember.email + ";" + currentMember.startDate + ";"
                    + currentMember.hasPayed);   //Medlemmet skal tilføjes til filen
        }
        pw.close();

    }

    public static void editTrainerInfo() throws IOException {
        readTrainersFromFileAndAddToArray();
        Scanner input = new Scanner(System.in);
        Trainer memberToEdit = null;
        System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
        String userInput = input.nextLine();
        boolean matchFound = false;
        int lineNumber = 0;
        boolean end = false;
        while (!end) {

            switch (userInput) {
                case "1":
                    System.out.println("Hvad er ID-nummeret på den træner, du vil redigere?");
                    String inputID = input.nextLine();
                    int lineCounter = 0;

                    for (Trainer currentMember : trainerList) {
                        String currentID = currentMember.getID();
                        if (currentID.equals(inputID)) {
                            System.out.println("Vil du ændre følgende træner?\n" + currentMember.getID() + ", " +
                                    currentMember.getName() + "\n1: Ja 2: Nej");
                            userInput = input.nextLine();
                            switch (userInput) {
                                case "1":
                                    memberToEdit = currentMember;
                                    lineNumber = lineCounter;
                                    end = true;
                                    break;
                                case "2":
                                    System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                                    break;
                            }
                            matchFound = true;
                        }
                        lineCounter++;
                    }
                    if (!matchFound) {
                        System.out.println("Der blev ikke fundet et match for ID'et. Prøv igen.");
                    }
                    break;
                case "2":
                    System.out.println("Hvad er navnet på den træner, du vil redigere?");
                    String inputName = input.nextLine();
                    lineCounter = 0;
                    for (Trainer currentMember : trainerList) {
                        if (currentMember.getName().toLowerCase().contains(inputName.toLowerCase())) {
                            System.out.println("Vil du ændre følgende træner?\n\n---ID: " + currentMember.getID() + "; Navn: " +
                                    currentMember.getName() + "---\n\n1: Ja 2: Nej");
                            userInput = input.nextLine();
                            switch (userInput) {
                                case "1":
                                    memberToEdit = currentMember;
                                    lineNumber = lineCounter;
                                    end = true;
                                    break;
                                case "2":
                                    System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                                    break;
                            }
                            matchFound = true;
                        }
                        lineCounter++;
                    }
                    if (!matchFound) {
                        System.out.println("Der blev ikke fundet et match for navnet. Prøv igen.");
                    }
                    break;
                default:
                    System.out.println("Input ikke forstået. Prøv igen.\nVil du søge efter ID eller navn? 1: ID 2: Navn");
                    userInput = input.nextLine();
                    break;
            }
        }

        String name = memberToEdit.name;
        String ID = memberToEdit.ID;
        String birthdate = memberToEdit.birthdate;
        String memberStatus = memberToEdit.memberStatus;
        String telephoneNo = memberToEdit.telephoneNo;
        String email = memberToEdit.email;
        String startDate = memberToEdit.startDate;
        String discipline = memberToEdit.discipline;

        input = new Scanner(System.in);
        end = false;
        while (!end) {
            System.out.println("Hvad vil du ændre?\n1: Navn\n2: Fødselsdato\n3: Aktivitetsstatus" +
                    "\n4: Telefonnummer\n5: e-mail\n6: Startdato\n7: Disciplin");
            userInput = input.nextLine();
            switch (userInput) {
                case "1":
                    name = addName();
                    break;
                case "2":
                    birthdate = addBirthdate();
                    break;
                case "3":
                    memberStatus = addActivityStatus();
                    break;
                case "4":
                    telephoneNo = addPhoneNo();
                    break;
                case "5":
                    email = addEmail();
                    break;
                case "6":
                    startDate = addStartDate();
                    break;
                case "7":
                    discipline = addDiscipline();
                    break;
                default:
                    System.out.println("Input ikke forstået.");
                    break;
            }

            System.out.println("Er du færdig med at redigere træneren? 1: Ja 2: Nej");
            String userInput2 = input.nextLine();
            boolean end2 = false;
            while (!end2) {
                switch (userInput2) {
                    case "1":
                        end = true;
                        end2 = true;
                        break;
                    case "2":
                        end2 = true;
                        break;
                    default:
                }
            }
        }
        Trainer updatedTrainer = new Trainer(name, ID, birthdate, memberStatus, "trainer", "trainer",
                telephoneNo, email, startDate, false, discipline);
        trainerList.set(lineNumber, updatedTrainer);


        File trainersFile = new File("src/Files/TrainerList");
        FileWriter fw = new FileWriter(trainersFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(trainersFile);
        pw.write("name;ID;birthdate;memberStatus;memberGroup;memberType;telephoneNo;email;startDate;hasPayed");
        for (Member currentMember : trainerList) {
            pw.write("\n" + currentMember.name + ";" + currentMember.ID + ";" + currentMember.birthdate + ";" +
                    currentMember.memberStatus + ";" + currentMember.memberGroup + ";" + currentMember.memberType +
                    ";" + currentMember.telephoneNo + ";" + currentMember.email + ";" + currentMember.startDate + ";"
                    + currentMember.hasPayed);   //Medlemmet skal tilføjes til filen
        }
        pw.close();

    }


    public static void startNewSeason() throws IOException {
        readMembersFromFileAndAddToArray();
        Scanner input = new Scanner(System.in);
        Member memberToEdit = null;
        System.out.println("Er du sikker på, at du vil starte en ny sæson (dette vil resette alle medlemmers " +
                "betalingsstatus samt opdatere medlemstypen.\n1: Ja 2: Nej");
        String userInput = input.nextLine();
        int lineNumber = 0;
        boolean end = false;
        while (!end) {

            switch (userInput) {
                case "1":
                    for (Member currentMember : memberList) {
                        currentMember.setHasPayed(false); //Sætter betalingsstatusen til ikke-betalt

                        //Ændrer senior/junior-status, hvis dette er aktuelt
                        int year = parseInt(currentMember.birthdate.substring(currentMember.birthdate.length() - 4));
                        int month = parseInt(currentMember.birthdate.substring(2, 4));
                        int date = parseInt(currentMember.birthdate.substring(0, 2));
                        Period period = Period.between(LocalDate.of(year, month, date), LocalDate.now());
                        int age = period.getYears();
                        if (age >= 18) {
                            currentMember.setMemberType("senior");
                        } else {
                            currentMember.setMemberType("junior");
                        }
                    }
                    System.out.println("Listen er blevet opdateret.");
                    end = true;
                    break;
                case "2":
                    System.out.println("Listen er ikke blevet ændret.");
                    end = true;
                    break;
                default:
                    System.out.println("Input ikke forstået. Prøv igen.\nEr du sikker på, at du vil starte en ny " +
                            "sæson (dette vil resette alle medlemmers \" +\n" +
                            "                \"betalingsstatus samt opdatere medlemstypen.\\n1: Ja 2: Nej");
                    userInput = input.nextLine();
                    break;
            }
        }

        File membersFile = new File("src/Files/MembersList");
        FileWriter fw = new FileWriter(membersFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(membersFile);
        pw.write("name;ID;birthdate;memberStatus;memberGroup;memberType;telephoneNo;email;startDate;hasPayed");
        for (Member currentMember : memberList) {
            pw.write("\n" + currentMember.name + ";" + currentMember.ID + ";" + currentMember.birthdate + ";" +
                    currentMember.memberStatus + ";" + currentMember.memberGroup + ";" + currentMember.memberType +
                    ";" + currentMember.telephoneNo + ";" + currentMember.email + ";" + currentMember.startDate + ";"
                    + currentMember.hasPayed);   //Medlemmet skal tilføjes til filen
        }
        pw.close();

    }

    public static void editPaymentStatus() throws IOException {
        readMembersFromFileAndAddToArray();
        Scanner input = new Scanner(System.in);
        Member memberToEdit = null;
        System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn 3: Afslut");
        String userInput = input.nextLine();
        boolean matchFound = false;
        int lineNumber = 0;
        boolean end = false;
        while (!end) {

            switch (userInput) {
                case "1":
                    System.out.println("Hvad er ID-nummeret på det medlem, du vil redigere?");
                    String inputID = input.nextLine();
                    int lineCounter = 0;

                    for (Member currentMember : memberList) {
                        String currentID = currentMember.getID();
                        if (currentID.equals(inputID)) {
                            System.out.println("Vil du ændre følgende medlem?\n" + currentMember.getID() + ", " +
                                    currentMember.getName() + "\nBetalingsstatus: " + currentMember.hasPayed +
                                    "\n1: Ja 2: Nej");
                            userInput = input.nextLine();
                            boolean end2 = false;
                            boolean newHasPayed = false;
                            while (!end2) {
                                switch (userInput) {
                                    case "1":
                                        System.out.println("Hvad skal betalingsstatus være? 1: Betalt 2: Ikke betalt");
                                        userInput = input.nextLine();
                                        switch (userInput) {
                                            case "1":
                                                newHasPayed = true;
                                                break;
                                            case "2":
                                                newHasPayed = false;
                                                break;
                                        }
                                        Member updatedMember = new Member(currentMember.name, currentMember.ID,
                                                currentMember.birthdate, currentMember.memberStatus,
                                                currentMember.memberGroup, currentMember.memberType,
                                                currentMember.telephoneNo, currentMember.email,
                                                currentMember.startDate, newHasPayed);
                                        lineNumber = lineCounter;
                                        memberList.set(lineNumber, updatedMember);
                                        end = true;
                                        end2 = true;
                                        break;
                                    case "2":
                                        end = true;
                                        end2 = true;
                                        break;
                                }
                            }
                            matchFound = true;
                        }
                        lineCounter++;
                    }
                    if (!matchFound) {
                        System.out.println("Der blev ikke fundet et match for ID'et. Prøv igen.");
                    }
                    break;
                case "2":
                    System.out.println("Hvad er navnet på det medlem, du vil redigere?");
                    String inputName = input.nextLine();
                    lineCounter = 0;
                    for (Member currentMember : memberList) {
                        if (currentMember.getName().toLowerCase().contains(inputName.toLowerCase())) {
                            System.out.println("Vil du ændre følgende medlem?\n" + currentMember.getID() + ", " +
                                    currentMember.getName() + "\nBetalingsstatus: " + currentMember.hasPayed +
                                    "\n1: Ja 2: Nej");
                            userInput = input.nextLine();
                            boolean end2 = false;
                            boolean newHasPayed = false;
                            while (!end2) {
                                switch (userInput) {
                                    case "1":
                                        System.out.println("Hvad skal betalingsstatus være? 1: Betalt 2: Ikke betalt");
                                        userInput = input.nextLine();
                                        switch (userInput) {
                                            case "1":
                                                newHasPayed = true;
                                                break;
                                            case "2":
                                                newHasPayed = false;
                                                break;
                                        }
                                        Member updatedMember = new Member(currentMember.name, currentMember.ID,
                                                currentMember.birthdate, currentMember.memberStatus,
                                                currentMember.memberGroup, currentMember.memberType,
                                                currentMember.telephoneNo, currentMember.email,
                                                currentMember.startDate, newHasPayed);
                                        lineNumber = lineCounter;
                                        memberList.set(lineNumber, updatedMember);
                                        end = true;
                                        end2 = true;
                                        break;
                                    case "2":
                                        end = true;
                                        end2 = true;
                                        break;
                                }
                            }
                            matchFound = false;
                        }
                        lineCounter++;
                    }
                    if (!matchFound) {
                        System.out.println("Der blev ikke fundet et match for navnet. Prøv igen.");
                    }
                case "3":
                    end = true;
                    break;
                default:
                    System.out.println("Input ikke forstået. Prøv igen.\nVil du søge efter ID eller navn? 1: ID 2: Navn");
                    userInput = input.nextLine();
                    break;
            }
        }

        File membersFile = new File("src/Files/MembersList");
        FileWriter fw = new FileWriter(membersFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(membersFile);
        pw.write("name;ID;birthdate;memberStatus;memberGroup;memberType;telephoneNo;email;startDate;hasPayed");
        for (Member currentMember : memberList) {
            pw.write("\n" + currentMember.name + ";" + currentMember.ID + ";" + currentMember.birthdate + ";" +
                    currentMember.memberStatus + ";" + currentMember.memberGroup + ";" + currentMember.memberType +
                    ";" + currentMember.telephoneNo + ";" + currentMember.email + ";" + currentMember.startDate + ";"
                    + currentMember.hasPayed);   //Medlemmet skal tilføjes til filen
        }
        pw.close();
        System.out.println("Proces er afsluttet.");
    }


    public static String addName() {
        Scanner input = new Scanner(System.in);
        System.out.println("Indtast medlemmets fulde navn:");
        String name = input.nextLine();

        boolean end = false;
        while (!end) {
            if (isValidName(name)) {
                System.out.println("Navnet er blevet tilføjet.");
                end = true;
            } else {
                System.out.println("Input ikke forstået. Prøv igen.");
                name = input.nextLine();
            }
        }
        return name;
    }

    public static boolean isValidName(String name) {
        boolean onlyLetters = true;
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c) && !isWhiteSpace(c) && c != '-' && c != '.') {
                onlyLetters = false;
            }
        }
        return onlyLetters;
    }


    public static String addBirthdate() {
        Scanner input = new Scanner(System.in);

        System.out.println("Indtast medlemmets fødseldsdag (ddMMyyyy):");
        boolean end = false;
        String birthdate = null;
        birthdate = input.nextLine();
        while (!end) {
            if (!isValidBirthdate(birthdate)) {
                System.out.println("Ugyldig fødselsdato\nFormatet er ‘ddMMyyyy’\nÅrstal skal tidligst være 140 år " +
                        "før dags dato samt minimum 6 år fra dags dato\nDato skal være mellem 1 og 31\nMåned skal " +
                        "være mellem 1 og 12\n\nIndtast medlemmets fødseldsdag (ddMMyyyy):");
                birthdate = input.nextLine();
            } else {
                System.out.println("Fødselsdato er blevet tilføjet.");
                end = true;
            }
        }
        return birthdate;
    }

    public static boolean isValidBirthdate(String birthdate) {
        if (birthdate.length() != 8 || !isNumeric(birthdate) || parseInt(birthdate.substring(0, 2)) > 31 ||
                parseInt(birthdate.substring(2, 4)) > 12 || parseInt(birthdate.substring(0, 2)) == 00 ||
                parseInt(birthdate.substring(2, 4)) == 00 || parseInt(birthdate.substring(4, 8)) > 2015 ||
                parseInt(birthdate.substring(4, 8)) < 1900) {
            return false;
        } else {
            return true;
        }
    }

    public static String addActivityStatus() {
        Scanner input = new Scanner(System.in);
        System.out.println("Er medlemmet aktivt? 1: Ja 2: Nej");
        boolean end = false;
        String memberStatus = null;
        String userInput = input.nextLine();
        while (!end) {

            switch (userInput) {
                case "1":
                    memberStatus = "active";
                    end = true;
                    break;
                case "2":
                    memberStatus = "passive";
                    end = true;
                    break;
                default:
                    System.out.println("Input ikke forstået. Prøv igen.\nEr medlemmet aktivt? 1: Ja 2: Nej");
                    userInput = input.nextLine();
                    break;
            }
        }
        return memberStatus;
    }

    public static String addMemberType() {
        Scanner input = new Scanner(System.in);
        System.out.println("Hvad er medlemstypen? 1: Motionist 2: Konkurrencesvømmer");
        String memberGroup = null;
        String userInput = input.nextLine();
        boolean end = false;
        while (!end) {
            switch (userInput) {
                case "1":
                    memberGroup = "motionist";
                    end = true;
                    break;
                case "2":
                    memberGroup = "konkurrence";
                    end = true;
                    break;
                default:
                    System.out.println("Input ikke forstået. Prøv igen.\nHvad er medlemstypen? 1: Motionist " +
                            "2: Konkurrencesvømmer");
                    userInput = input.nextLine();
                    break;
            }
        }
        return memberGroup;
    }

    public static String addPhoneNo() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);

        System.out.println("Indtast medlemmets telefonnummer:");
        String telephoneNo = input.nextLine();
        boolean end = false;
        while (!end) {
            if (!isValidPhoneNo(telephoneNo)) {
                System.out.println("Ugyldigt telefonnummer. Prøv igen:");
                telephoneNo = input.nextLine();
            } else {

                //Tjekker om telefonnummeret allerede eksisterer i databasen
                boolean alreadyExists = false;
                File membersFile = new File("src/Files/MembersList");
                int index = 6;

                if (!alreadyExistsInFile(telephoneNo, membersFile, index)) {
                    System.out.println("Telefonnummeret er blevet tilføjet.");
                    end = true;
                } else {
                    boolean end2 = false;
                    System.out.println("Nummeret eksisterer allerede i databasen. Vil du tilføje det alligevel? " +
                            "1: Ja 2: Nej");
                    String userInput = input.nextLine();
                    while (!end2) {
                        switch (userInput) {
                            case "1":
                                System.out.println("Telefonnummeret er blevet tilføjet.");
                                end = true;
                                end2 = true;
                                break;
                            case "2":
                                System.out.println("Indtast nyt telefonnummer:");
                                telephoneNo = input.nextLine();
                                end2 = true;
                                break;
                            default:
                                System.out.println("Input ikke forstået. Prøv igen.");
                                break;

                        }
                    }
                }


            }
        }
        return telephoneNo;
    }

    public static boolean alreadyExistsInFile(String toBeChecked, File file, int index) throws FileNotFoundException {
        //Tjekker om allerede eksisterer i databasen
        boolean alreadyExists = false;
        Scanner sc = new Scanner(file);

        //Skipper metadata linjen
        sc.nextLine();

        //while loop for alle linjer
        while (sc.hasNext()) {

            String currentMember = sc.nextLine();

            String[] lineAsArray = currentMember.split(";");
            String currentAttribute = lineAsArray[index].trim();
            if (toBeChecked.equals(currentAttribute)) {
                alreadyExists = true;
            }
        }
        return alreadyExists;
    }

    public static boolean isValidPhoneNo(String telephoneNo) {
        if (telephoneNo.length() != 8 || !isNumeric(telephoneNo)) {
            return false;
        } else {
            return true;
        }
    }


    public static String addEmail() {
        Scanner input = new Scanner(System.in);

        String email = null;
        System.out.println("Indtast medlemmets e-mail:");
        String tempEmail = input.nextLine();
        boolean end = false;
        while (!end) {
            if (isValidEmail(tempEmail)) {
                System.out.println("E-mailen er blevet tilføjet.");
                email = tempEmail;
                end = true;
            } else {
                System.out.println("Ugyldig e-mail. Prøv igen:");
                tempEmail = input.nextLine();
            }
        }
        return email;
    }

    public static boolean isValidEmail(String tempEmail) {
        if (tempEmail.contains("@") && tempEmail.contains(".dk") || tempEmail.contains(".com") || tempEmail.contains(".net")
                || tempEmail.contains(".co.uk") || tempEmail.contains(".gov")) {
            return true;
        } else {
            return false;
        }
    }

    public static String addStartDate() {
        Scanner input = new Scanner(System.in);

        System.out.println("Sæt startdato til i dag? 1: Ja 2: Nej");
        String startDate = null;
        String userInput = input.nextLine();
        boolean end = false;
        while (!end) {
            switch (userInput) {
                case "1":
                    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("ddMMyyyy");
                    startDate = LocalDateTime.now().format(formatTime);
                    System.out.println("Startdatoen er blevet tilføjet.");
                    end = true;
                    break;
                case "2":
                    System.out.println("Indtast startdato (ddMMyyyy)");
                    startDate = input.nextLine();
                    end = false;
                    while (!end) {
                        if (!isValidStartDate(startDate)) {
                            System.out.println("Ugyldig startdato\nFormatet er ‘ddMMyyyy’\nÅrstal skal tidligst være " +
                                    "2005\nDato skal være mellem 1 og 31\nMåned skal " +
                                    "være mellem 1 og 12\n\nIndtast medlemmets startdato (ddMMyyyy):");
                            startDate = input.nextLine();
                        } else {
                            System.out.println("Startdatoen er blevet tilføjet.");
                            end = true;
                        }

                    }
                    end = true;
                    break;
                default:
                    System.out.println("Input ikke forstået. Prøve igen.\nSæt startdato til i dag? 1: Ja 2: Nej");
                    userInput = input.nextLine();
                    break;
            }
        }
        return startDate;
    }

    public static boolean isValidStartDate(String startDate) {
        if (startDate.length() != 8 || !isNumeric(startDate) || parseInt(startDate.substring(0, 2)) > 31
                || parseInt(startDate.substring(2, 4)) > 12
                || parseInt(startDate.substring(0, 2)) == 00 || parseInt(startDate.substring(2, 4)) == 00
                || parseInt(startDate.substring(4, 8)) > 2021 || parseInt(startDate.substring(4, 8)) < 2005) {
            return false;
        } else {
            return true;
        }
    }


    public static boolean addPaymentStatus() {
        Scanner input = new Scanner(System.in);

        System.out.println("Har medlemmet betalt kontingent? 1: Ja 2: Nej");
        boolean hasPayed = false;
        String userInput = input.nextLine();
        boolean end = false;
        while (!end) {
            switch (userInput) {
                case "1":
                    hasPayed = true;
                    end = true;
                    break;
                case "2":
                    hasPayed = false;
                    end = true;
                    break;
                default:
                    System.out.println("Input ikke forstået. Prøv igen.\nHar medlemmet betalt kontingent? 1: Ja " +
                            "2: Nej");
                    userInput = input.nextLine();
                    break;
            }
        }
        return hasPayed;
    }


    public static void editTrainerTeams() throws IOException {
        Scanner sc = new Scanner(System.in);
        int lineCounter = 0;
        int lineNumber = 0;
        readMembersFromFileAndAddToArray();
        readTrainersFromFileAndAddToArray();
        System.out.println("Her er en liste over alle trænere:\n");
        for (Trainer trainer : trainerList) {
            System.out.println("ID: " + trainer.getID() + " navn: " + trainer.getName());
        }
        boolean end = false;
        while (!end) {
            System.out.println("\nIndtast ID'et på den træners hold, du vil redigere.");
            String inputID = sc.nextLine();
            boolean matchFound = false;
            lineNumber = 0;
            lineCounter = 0;
            for (Trainer currenTrainer : trainerList) {
                String currentID = currenTrainer.getID();
                if (currentID.equals(inputID)) {
                    matchFound = true;
                    lineNumber = lineCounter;
                }
                lineCounter++;
            }
            if (!matchFound) {
                System.out.println("Der blev ikke fundet et match for ID'et. Prøv igen.");
            } else {
                end = true;
            }
        }

        String trainerID = trainerList.get(lineNumber).getID();
        makeNewTrainerCSVFile(trainerID);
        File file = new File("src/TrainerFiles/" + trainerID);

        readTrainersFromFileAndAddToArray(file);

        if (currentTrainerList.size() != 0) {
            System.out.println("Nuværende liste");
            for (Member currentMember : currentTrainerList) {
                System.out.println("ID: " + currentMember.getID() + " Name: " + currentMember.getName());

            }
        } else {
            System.out.println("Listen er tom.");
        }

        boolean end4 = false;
        while (!end4) {
            System.out.println("Hvad ønsker du at gøre?\n1: Tilføj medlem 2: Slet medlem 3: Exit");
            String userInput = sc.nextLine();
            end = false;
            readMembersFromFileAndAddToArray();
            while (!end) {
                switch (userInput) {
                    case "1":
                        //Herunder er kode, hvor brugeren søger efter medlemmet, der skal tilføjes, vha. navn eller ID.
                        // Derefter tilføjes medlemmet til arraylisten, og brugeren får mulighed for at fortsætte med at
                        // redigere listen. Til sidst overkrives alt i filen med det opdaterede array.
                        Scanner input = new Scanner(System.in);
                        Member memberToAdd = null;
                        System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                        userInput = input.nextLine();
                        boolean matchFound = false;
                        lineNumber = 0;
                        boolean end2 = false;
                        while (!end2) {

                            switch (userInput) {
                                case "1":
                                    System.out.println("Hvad er ID-nummeret på det medlem, du vil tilføje?");
                                    String inputID = input.nextLine();
                                    lineCounter = 0;

                                    for (Member currentMember : memberList) {
                                        String currentID = currentMember.getID();
                                        if (currentID.equals(inputID) && trainerList.get(lineNumber).getMemberType().equals(currentMember.getMemberType())) {
                                            System.out.println("Vil du tilføje følgende medlem?\n" + currentMember.getID() + ", " +
                                                    currentMember.getName() + "\n1: Ja 2: Nej");
                                            userInput = input.nextLine();
                                            switch (userInput) {
                                                case "1":
                                                    memberToAdd = currentMember;
                                                    lineNumber = lineCounter;
                                                    end2 = true;
                                                    break;
                                                case "2":
                                                    System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                                                    break;
                                            }
                                            matchFound = true;
                                        } else if (currentID.equals(inputID) && !trainerList.get(lineNumber).
                                                getMemberType().equals(currentMember.getMemberType())){
                                            System.out.println("Træneren træner ikke svømmere i denne aldersgruppe. " +
                                                    "Træneren træner " + trainerList.get(lineNumber).getMemberType()
                                                    + ", denne svømmer er " + currentMember.getMemberType() + ". " +
                                                    "Prøv igen\nHvad er ID-nummeret på det medlem, du vil tilføje?");
                                        }
                                        lineCounter++;
                                    }
                                    if (!matchFound) {
                                        System.out.println("Der blev ikke fundet et match for ID'et. Prøv igen.");
                                    }
                                    break;
                                case "2":
                                    System.out.println("Hvad er navnet på det medlem, du vil tilføje?");
                                    String inputName = input.nextLine();
                                    lineCounter = 0;
                                    for (Member currentMember : memberList) {
                                        if (currentMember.getName().toLowerCase().contains(inputName.toLowerCase())
                                                && trainerList.get(lineNumber).getMemberType().equals(currentMember.
                                                getMemberType())) {
                                            System.out.println("Vil du tilføje følgende medlem?\n\n---ID: " + currentMember.getID() + "; Navn: " +
                                                    currentMember.getName() + "---\n\n1: Ja 2: Nej");
                                            userInput = input.nextLine();
                                            switch (userInput) {
                                                case "1":
                                                    memberToAdd = currentMember;
                                                    lineNumber = lineCounter;
                                                    end2 = true;
                                                    break;
                                                case "2":
                                                    System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                                                    break;
                                            }
                                            matchFound = true;
                                        } else if (currentMember.getName().toLowerCase().contains(inputName.
                                                toLowerCase()) && !trainerList.get(lineNumber).
                                                getMemberType().equals(currentMember.getMemberType())){
                                            System.out.println("Træneren træner ikke svømmere i denne aldersgruppe. " +
                                                    "Træneren træner " + trainerList.get(lineNumber).getMemberType()
                                                    + ", denne svømmer er " + currentMember.getMemberType() + ". " +
                                                    "Prøv igen\nHvad er navnet på det medlem, du vil tilføje?");
                                        }
                                        lineCounter++;
                                    }
                                    if (!matchFound) {
                                        System.out.println("Der blev ikke fundet et match for navnet. Prøv igen.");
                                    }
                                    break;
                                default:
                                    System.out.println("Input ikke forstået. Prøv igen.\nVil du søge efter ID eller navn? 1: ID 2: Navn");
                                    userInput = input.nextLine();
                                    break;
                            }
                        }

                        currentTrainerList.add(memberToAdd);

                        System.out.println("Er du færdig med at redigere? 1: Ja 2: Nej");
                        userInput = input.nextLine();
                        boolean end3 = false;
                        while (!end3) {
                            switch (userInput) {
                                case "1":
                                    FileWriter fw = new FileWriter(file, true);
                                    BufferedWriter bw = new BufferedWriter(fw);
                                    PrintWriter pw = new PrintWriter(file);
                                    pw.write("name;ID;birthdate;memberStatus;memberGroup;memberType;telephoneNo;email;startDate;hasPayed");
                                    for (Member currentMember : currentTrainerList) {
                                        pw.write("\n" + currentMember.name + ";" + currentMember.ID + ";" + currentMember.birthdate + ";" +
                                                currentMember.memberStatus + ";" + currentMember.memberGroup + ";" + currentMember.memberType +
                                                ";" + currentMember.telephoneNo + ";" + currentMember.email + ";" + currentMember.startDate + ";"
                                                + currentMember.hasPayed);   //Medlemmet skal tilføjes til filen
                                    }
                                    pw.close();
                                    end3 = true;
                                    end4 = true;
                                    break;
                                case "2":
                                    end3 = true;
                                default:
                                    System.out.println("Input ikke forstået. Prøv igen.\nEr du færdig med at tilføje? 1: Ja 2: Nej");
                            }
                        }


                        end = true;
                        break;
                    case "2":
                        //Herunder er kode, hvor brugeren søger efter medlemmet, der skal slettes, vha. navn eller ID.
                        // Derefter slettes medlemmet fra arraylisten, og brugeren får mulighed for at fortsætte med at
                        // redigere listen. Til sidst overkrives alt i filen med det opdaterede array.
                        input = new Scanner(System.in);
                        Member memberToDelete = null;
                        System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                        userInput = input.nextLine();
                        matchFound = false;
                        lineNumber = 0;
                        end2 = false;
                        while (!end2) {

                            switch (userInput) {
                                case "1":
                                    System.out.println("Hvad er ID-nummeret på det medlem, du vil slette?");
                                    String inputID = input.nextLine();
                                    lineCounter = 0;

                                    for (Member currentMember : currentTrainerList) {
                                        String currentID = currentMember.getID();
                                        if (currentID.equals(inputID)) {
                                            System.out.println("Vil du slette følgende medlem?\n" + currentMember.getID() + ", " +
                                                    currentMember.getName() + "\n1: Ja 2: Nej");
                                            userInput = input.nextLine();
                                            switch (userInput) {
                                                case "1":
                                                    memberToDelete = currentMember;
                                                    lineNumber = lineCounter;
                                                    end2 = true;
                                                    break;
                                                case "2":
                                                    System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                                                    break;
                                            }
                                            matchFound = true;
                                        }
                                        lineCounter++;
                                    }
                                    if (!matchFound) {
                                        System.out.println("Der blev ikke fundet et match for ID'et. Prøv igen.");
                                    }
                                    break;
                                case "2":
                                    System.out.println("Hvad er navnet på det medlem, du vil slette?");
                                    String inputName = input.nextLine();
                                    lineCounter = 0;
                                    for (Member currentMember : currentTrainerList) {
                                        if (currentMember.getName().toLowerCase().contains(inputName.toLowerCase())) {
                                            System.out.println("Vil du slette følgende medlem?\n\n---ID: " + currentMember.getID() + "; Navn: " +
                                                    currentMember.getName() + "---\n\n1: Ja 2: Nej");
                                            userInput = input.nextLine();
                                            switch (userInput) {
                                                case "1":
                                                    memberToDelete = currentMember;
                                                    lineNumber = lineCounter;
                                                    end2 = true;
                                                    break;
                                                case "2":
                                                    System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                                                    break;
                                            }
                                            matchFound = true;
                                        }
                                        lineCounter++;
                                    }
                                    if (!matchFound) {
                                        System.out.println("Der blev ikke fundet et match for navnet. Prøv igen.");
                                    }
                                    break;
                                default:
                                    System.out.println("Input ikke forstået. Prøv igen.\nVil du søge efter ID eller navn? 1: ID 2: Navn");
                                    userInput = input.nextLine();
                                    break;
                            }
                        }

                        //Her slettes medlemmet
                        currentTrainerList.remove(lineNumber);

                        System.out.println("Er du færdig med at redigere? 1: Ja 2: Nej");
                        userInput = input.nextLine();
                        end3 = false;
                        while (!end3) {
                            switch (userInput) {
                                case "1":
                                    FileWriter fw = new FileWriter(file, true);
                                    BufferedWriter bw = new BufferedWriter(fw);
                                    PrintWriter pw = new PrintWriter(file);
                                    pw.write("name;ID;birthdate;memberStatus;memberGroup;memberType;telephoneNo;email;startDate;hasPayed");
                                    for (Member currentMember : currentTrainerList) {
                                        pw.write("\n" + currentMember.name + ";" + currentMember.ID + ";" + currentMember.birthdate + ";" +
                                                currentMember.memberStatus + ";" + currentMember.memberGroup + ";" + currentMember.memberType +
                                                ";" + currentMember.telephoneNo + ";" + currentMember.email + ";" + currentMember.startDate + ";"
                                                + currentMember.hasPayed);   //Medlemmet skal tilføjes til filen
                                    }
                                    pw.close();
                                    end3 = true;
                                    end4 = true;
                                    break;
                                case "2":
                                    end3 = true;
                                default:
                                    System.out.println("Input ikke forstået. Prøv igen.\nEr du færdig med at tilføje? 1: Ja 2: Nej");
                            }
                        }

                        end = true;
                        break;
                    default:
                        System.out.println("Input ikke forstået. Prøv igen.");
                        break;
                }
            }


        }
    }

        public static void readTrainersFromFileAndAddToArray(File trainerFile) throws FileNotFoundException {
            Scanner sc = new Scanner(trainerFile);

            currentTrainerList.clear();

            //Skipper metadatalinjen
            sc.nextLine();

            //While-loop, så alle linjer læses
            while (sc.hasNext()) {

                //En variabel, som indeholder den nuværende linje
                String currentMember = sc.nextLine();

                String[] lineAsArray = currentMember.split(";");

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

                CompetitionMember newMember = new CompetitionMember(name, ID, birthdate, memberStatus, memberGroup,
                        memberType, telephoneNo, email, startDate, hasPayed);
                currentTrainerList.add(newMember);
            }
        }

        //@Amanda med udgangspunkt i Caspers arbejde
        public static void makeNewTrainerCSVFile (String trainerID){
            // test to see if a file exists
            File file = new File("src/TrainerFiles/" + trainerID);
            boolean exists = file.exists();
            if (exists) {
                System.out.println("Der eksisterer allerede en fil for denne træner.");
            } else {
                String fileLocation = "src/TrainerFiles/";
                generateCsvFile(fileLocation + trainerID);
                System.out.println("En ny fil er blevet oprettet for denne træner.");
            }
        }


        public static void main (String[]args) throws IOException {
            editTrainerTeams();
        }

        public String getBirthdate () {
            return birthdate;
        }

        public String getMemberStatus () {
            return memberStatus;
        }

        public String getMemberGroup () {
            return memberGroup;
        }


        public String getMemberType () {
            return memberType;
        }

        public boolean isHasPayed () {
            return hasPayed;
        }

        public String getName () {
            return name;
        }

        public String getID () {
            return ID;
        }

        public String getTelephoneNo () {
            return telephoneNo;
        }

        public String getEmail () {
            return email;
        }

        public static ArrayList<Member> getMemberList () {
            return memberList;
        }

        public static boolean isNumeric (String str){
            try {
                parseLong(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        /* Jeg droppede denne
        public static boolean birthdateChecker(String birthdate){
            try{
                Date birthdateTest=new SimpleDateFormat("ddMMyyyy").parse(birthdate);
                System.out.println("Fødselsdato er blevet tilføjet.");
                return true;
            }
            catch (Exception e){
                System.out.println("Ugyldig fødselsdato\nFormatet er ‘ddMMyyyy’\nÅrstal skal tidligst være 140 år " +
                        "før dags dato samt minimum 6 år fra dags dato\nDatoskal være mellem 1 og 31\nMåned skal " +
                        "være mellem 1 og 12");
                return false;
            }
        }

         */


        @Override
        public String toString () {
            return "Telefon: " + getTelephoneNo() + " ID: " + getID();
        }

        public void setHasPayed ( boolean hasPayed){
            this.hasPayed = hasPayed;
        }

        public void setMemberType (String memberType){
            this.memberType = memberType;
        }
    }
