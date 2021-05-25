package Members;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static Results.Result.generateCsvFile;
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
    static ArrayList<Member> memberList = new ArrayList<>();
    private static ArrayList<Integer> IDListe;

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

    //Denne metode scanner filen MembersList og tilføjer alle medlemmer som et member-objekt til arrayListen memberList.
    public static void readMembersFromFileAndAddToArray() {
        memberList.clear();

        try {
            File membersFile = new File("src/Files/MembersList");
            Scanner sc = new Scanner(membersFile);


        //Skipper metadatalinjen
        sc.nextLine();


        //While-loop, så alle linjer læses
        while (sc.hasNext()) {

            //En variabel, som indeholder den linje, scanneren netop har læst
            String currentMember = sc.nextLine();

            //Linjen deles op i attributter, der defineres
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

            //Herunder afgøres det, om det er tale om objektet Member eller CompetetionMember baseret på attributten
            // memberGroup. Objektet oprettes derefter og tilføjes til arrayListen memberList.
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
        } catch (FileNotFoundException e) {
            System.out.println("Fil mangler.");
        }
    }



    //Denne metode kalder andre metoder, der beder brugeren om at definere et nyt medlems attributter.
    // Til sidst kaldes en metode, der tilføjer medlemmet til filen MemberList.
    public static void writeNewMember(){
        Scanner sc = new Scanner(System.in);

        String name = addName();

        String birthdate = addBirthdate();

        String memberStatus = addActivityStatus();

        String memberType = addMemberGroup();

        String telephoneNo = addPhoneNo();

        String email = addEmail();

        String startDate = addStartDate();

        boolean hasPayed = addPaymentStatus();

        addMemberToFile(name, birthdate, memberStatus, memberType, telephoneNo, email, startDate, hasPayed);
    }

    //Denne metode får som input et medlemmers definerede attributter og tilføjer en linje med disse til filen
    // MembersList
    public static void addMemberToFile(String name, String birthdate, String memberStatus, String memberGroup,
                                       String telephoneNo, String email, String startDate, boolean hasPayed){
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




    //Denne metode beder brugeren om at vælge mellem senior og junior og giver dette som e String-output.
    public static String addMemberType(){
        Scanner input = new Scanner(System.in);
        System.out.println("Hvilken aldersgruppe skal træneren træne? 1: Senior 2: Junior");
        String memberType = null;
        String userInput = input.nextLine();
        boolean end = false;
        while (!end) {
            switch (userInput) {
                case "1":
                    memberType = "senior";
                    end = true;
                    break;
                case "2":
                    memberType = "junior";
                    end = true;
                    break;
                default:
                    System.out.println("Input ikke forstået. Prøv igen.\nHvilken aldersgruppe skal træneren træne? 1: Senior 2: Junior");
                    userInput = input.nextLine();
                    break;
            }
        }
        return memberType;
    }


    //Denne metode beder brugeren om at vælge disciplin og giver dette som e String-output.
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


    //Denne metode får brugeren til at vælge et eksisterende medlem og gør det muligt for denne at ændre medlemmets
    // attributter. Filen MembersList opdateres med de nye oplysninger, idet hele filen overrides.
    public static void editMemberInfo() {

        //Her kaldes metoden, der læser MemberList og tilføjer medlemsobjekterne til arrayListen memberList.
        readMembersFromFileAndAddToArray();
        try {

            //Brugeren får mulighed for at søge efter medlemmet i arrayListen memberList enten vha. ID eller navn.
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
                            //En linecounter holder sammen med attributten lineNumber styr på, hvilket medlem,
                            // der skal ændres.
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
                            //En linecounter holder sammen med attributten lineNumber styr på, hvilket medlem,
                            // der skal ændres.
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

            //Medlemmets attributter hentes, så de attributter, der ikke ændres, kan tilføjes, når medlemmet
            // oprettes på ny.
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

            //Brugeren får mulighed for at vælge, hvilken attribut, der skal ændres, og metoden,
            // der tilføjer denne attribut, kaldes.
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
                        memberGroup = addMemberGroup();
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
                        System.out.println("Input ikke forstået. Prøv igen.");
                        userInput = input.nextLine();
                        break;
                }

                //Brugeren får mulighed for at afslutte ændringen af medlemmet eller ændre endnu en attribut.
                System.out.println("Er du færdig med at redigere medlemmet? 1: Ja 2: Nej");
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
                            System.out.println("Input ikke forstået. Prøv igen.");
                            userInput2 = input.nextLine();
                            break;
                    }
                }
            }

            //Medlemmet defineres med de evt. ændrede attributter
            Member updatedMember = new Member(name, ID, birthdate, memberStatus, memberGroup, memberType,
                    telephoneNo, email, startDate, hasPayed);
            memberList.set(lineNumber, updatedMember);


            //Filen MembersFile overrides med oplysninger fra memberList, deriblandt er det netop opdaterede medlem.
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

            //Exception catches, hvis der sker fejl v. input/output
        } catch (IOException e){
            System.out.println("Fejl");
        }
    }


    //Denne metode bruges ved starten af en ny sæson: Betalingsstatus for alle medlemmer nulstilles til
    // ikke-betalt (false), og alle medlemmers alder undersøges, så medlemstypen kan fastsættes på baggrund af denne.
    public static void startNewSeason() {
        readMembersFromFileAndAddToArray();

        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Er du sikker på, at du vil starte en ny sæson (dette vil resette alle medlemmers " +
                    "betalingsstatus samt opdatere medlemstypen.\n1: Ja 2: Nej");
            String userInput = input.nextLine();
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

            //Alle de opdaterede medlemmer tilføjes filen MemerList, idet den overrides.
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

        }catch (IOException e){
            System.out.println("Fejl.");
        }
    }


    //Denne metode prompter brugeren til at skrive et navn, og den giver dette som et String-output.
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
                System.out.println("Ugyldigt navn. Prøv igen.\n" +
                        "Navne må kun indeholde bogstaver, mellemrum, punktum og bindestreg.");
                name = input.nextLine();
            }
        }
        return name;
    }

    //Denne metode tjekker, om en String opfylder de krav, som et navn i systemet skal opfylde.
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

    //Denne metode prompter brugeren til at skrive en fødselsdato, og den giver denne som et String-output.
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

    //Denne metode tjekker, om en String opfylder de krav, som en førdselsdato i systemet skal opfylde.
    public static boolean isValidBirthdate(String birthdate) {

        if (birthdate.length() == 8 && isNumeric(birthdate) && parseInt(birthdate.substring(0, 2)) <= 31 &&
                parseInt(birthdate.substring(2, 4)) <= 12 && parseInt(birthdate.substring(0, 2)) != 00 &&
                parseInt(birthdate.substring(2, 4)) != 00) {
            int year = parseInt(birthdate.substring(birthdate.length() - 4));
            int month = parseInt(birthdate.substring(2, 4));
            int date = parseInt(birthdate.substring(0, 2));
            Period period = Period.between(LocalDate.of(year, month, date), LocalDate.now());
            int age = period.getYears();
            if (age >= 6 && age <= 140){
                return true;
            }else{
                return false;
            }
        } else {
            return false;
        }
    }

    //Denne metode prompter brugeren til at vælge, hvorvidt medlemmet er aktivt, og outputter et en string enten
    // lig "active" eller "passive".
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

    //Denne metode prompter brugeren til at vælge, hvilken medlemtype, medlemmet er, og outputter et en string enten
    // lig "Motionist" eller "Konkurrencesvømmer".
    public static String addMemberGroup() {
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

    //Denne metode prompter brugeren til at skrive en telefonnummer, og den giver dette som et String-output.
    public static String addPhoneNo() {

        Scanner input = new Scanner(System.in);

        System.out.println("Indtast medlemmets telefonnummer:");
        String telephoneNo = input.nextLine();
        boolean end = false;
        while (!end) {
            if (!isValidPhoneNo(telephoneNo)) {
                System.out.println("Ugyldigt telefonnummer. Prøv igen.\n" +
                        "Telefonnummeret skal være på 8 cifre.");
                telephoneNo = input.nextLine();
            } else {

                //Tjekker om telefonnummeret allerede eksisterer i databasen
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
                                userInput = input.nextLine();
                                break;

                        }
                    }
                }


            }
        }
        return telephoneNo;
    }


    //Denne metode tjekker, om en String allerede findes i en fil, idet filen laves til et array, og der scannes
    // i et bestemt index.
    public static boolean alreadyExistsInFile(String toBeChecked, File file, int index) {
        boolean alreadyExists = false;
        try {
            //Tjekker om allerede eksisterer i databasen
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

        } catch (FileNotFoundException e){
            System.out.println("Filen blev ikke fundet.");
        }
        return alreadyExists;
    }


    //Denne metode tjekker, om en String opfylder de krav, som et telefonnummer i systemet skal opfylde.
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
            if (!isValidEmail(tempEmail)) {
                System.out.println("Ugyldig e-mail. Prøv igen:");
                tempEmail = input.nextLine();
            } else {
                //Tjekker om emailen allerede eksisterer i databasen
                File membersFile = new File("src/Files/MembersList");
                int index = 7;

                if (!alreadyExistsInFile(tempEmail, membersFile, index)) {
                    System.out.println("E-mailen er blevet tilføjet.");
                    email = tempEmail;
                    end = true;
                } else {
                    boolean end2 = false;
                    System.out.println("E-mailen eksisterer allerede i databasen. Vil du tilføje den alligevel? " +
                            "1: Ja 2: Nej");
                    String userInput = input.nextLine();
                    while (!end2) {
                        switch (userInput) {
                            case "1":
                                System.out.println("E-mailen er blevet tilføjet.");
                                end = true;
                                end2 = true;
                                break;
                            case "2":
                                System.out.println("Indtast ny e-mail:");
                                tempEmail = input.nextLine();
                                end2 = true;
                                break;
                            default:
                                System.out.println("Input ikke forstået. Prøv igen.");
                                userInput = input.nextLine();
                                break;

                        }
                    }
                }
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
                || parseInt(startDate.substring(4, 8)) < 2005) {
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

    //Denne metode bruges til at ændre attributten hasPayed, der fortæller, om et medlem har betalt kontingent.
    public static void editPaymentStatus() {
        readMembersFromFileAndAddToArray();
        try {
            Scanner input = new Scanner(System.in);
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
        } catch (IOException e){
            System.out.println("Fejl.");
        }
    }


    public static void main (String[]args) {
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

    //Denne metode undersøger, om et String-input udelukkende består af tal.
    public static boolean isNumeric (String str){
            try {
                parseLong(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
    }

    @Override
    public String toString () {
            return "Telefon: " + getTelephoneNo() + " ID: " + getID();
    }

    public void setHasPayed(boolean hasPayed){
            this.hasPayed = hasPayed;
    }

    public void setMemberType (String memberType){
            this.memberType = memberType;
    }
}