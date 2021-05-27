package Members;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

        File membersFile = new File("src/Files/MembersList");
        Scanner sc;

        try {

        sc = new Scanner(membersFile);

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


    //Denne metode kalder andre metoder, der beder brugeren om at definere nogle af et nyt medlems attributter, samt
    // definerer resten af dem autromatisk.
    // Til sidst tilføjes medlemmet til filen MembersList.
    public static void addNewMember(){
        File membersFile = new File("src/Files/MembersList");

        String name = addName();

        String ID = decideIDNumber(membersFile);

        String birthdate = addBirthdate();

        String memberStatus = addActivityStatus();

        String memberGroup = addMemberGroup();

        String memberType = decideMemberType(birthdate);

        String telephoneNo = addPhoneNo();

        String email = addEmail();

        String startDate = addStartDate();

        boolean hasPayed = addPaymentStatus();

        try {
            //Filen redigeres
            FileWriter fw = new FileWriter(membersFile, true);   //Filen bliver ikke overwritten.
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("\n" + name + ";" + ID + ";" + birthdate + ";" + memberStatus + ";" + memberGroup + ";"
                    + memberType +
                    ";" + telephoneNo + ";" + email + ";" + startDate + ";" + hasPayed);   //Medlemmet skal tilføjes til filen
            bw.close();     //Handlingen sker rent faktisk
            System.out.println("Medlem blev tilføjet");
        } catch (IOException e){
            System.out.println("Fejl.");
        }

    }


    //Denne metode overrider en fil med oplysninger fra et array af medlemmer
    public static void overrideFileWithArrayList(File file, ArrayList<Member> arrayList){
        try{
            PrintWriter pw = new PrintWriter(file);
            pw.write("name;ID;birthdate;memberStatus;memberGroup;memberType;telephoneNo;email;startDate;hasPayed");
            for (Member currentMember : arrayList) {
                pw.write("\n" + currentMember.name + ";" + currentMember.ID + ";" + currentMember.birthdate + ";" +
                        currentMember.memberStatus + ";" + currentMember.memberGroup + ";" + currentMember.memberType +
                        ";" + currentMember.telephoneNo + ";" + currentMember.email + ";" + currentMember.startDate + ";"
                        + currentMember.hasPayed);   //Medlemmet skal tilføjes til filen
            }
            pw.close();
        } catch(IOException e){
            System.out.println("Der skete en fejl.");
        }
    }


    //Metoden returnerer et ID-nummer, der er én højere end det hidtil højeste ID i input-filen.
    // Hvis der ikke findes nogen ID-numre i filen, resturneres Id-nummeret 0001.
    public static String decideIDNumber(File file){
        String ID = null;
        try {

            Scanner sc = new Scanner(file);

            ArrayList<Integer> IDListe = new ArrayList<>();
            //Skipper metadata linjen
            sc.nextLine();

            //while loop for alle linjer
            while (sc.hasNext()) {

                String currentMember = sc.nextLine();

                String[] lineAsArray = currentMember.split(";");
                int currentID = parseInt(lineAsArray[1].trim());

                IDListe.add(currentID);
            }

            //Hvis der ikke findes noget medlem med et ID, returneres ID=0001
            if (IDListe.size() != 0) {
                String tempID = "000" + (Collections.max(IDListe) + 1);
                ID = tempID.substring(tempID.length() - 4);
            } else {
                ID = "0001";
            }
        } catch (FileNotFoundException e){
            System.out.println("Filen eksisterer ikke.");
        }
        return ID;
    }


    //Metoden tager et medlems fødselsdag som input og returnerer enten junior eller senior afhængig af medlemmets alder.
    public static String decideMemberType(String birthdate){

        String memberType;

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
        return memberType;
    }


    //Denne metode får brugeren til at vælge et eksisterende medlem og gør det muligt at ændre medlemmets
    // attributter. Filen MembersList opdateres med de nye oplysninger, idet hele filen overrides.
    public static void editMemberInfo() {

        //Her kaldes metoden, der læser MembersList og tilføjer medlemsobjekterne til arrayListen memberList.
        readMembersFromFileAndAddToArray();

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
        overrideFileWithArrayList(membersFile, memberList);
    }


    //Denne metode bruges ved starten af en ny sæson: Betalingsstatus for alle medlemmer nulstilles til
    // ikke-betalt (false), og alle medlemmers alder undersøges, så medlemstypen kan fastsættes på baggrund af denne.
    public static void startNewSeason() {
        readMembersFromFileAndAddToArray();

        Scanner input = new Scanner(System.in);
        System.out.println("Er du sikker på, at du vil starte en ny sæson (dette vil resette alle medlemmers " +
                "betalingsstatus samt opdatere medlemstypen.\n1: Ja 2: Nej");
        String userInput = input.nextLine();
        boolean end = false;
        while (!end) {

            switch (userInput) {
                case "1":
                    for (Member currentMember : memberList) {
                        //Sætter betalingsstatusen til ikke-betalt
                        currentMember.setHasPayed(false);

                        //Ændrer senior/junior-status, hvis dette er aktuelt
                        currentMember.setMemberType(decideMemberType(currentMember.getBirthdate()));
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

        //Alle de opdaterede medlemmer tilføjes filen MemberList, idet den overrides.
        File membersFile = new File("src/Files/MembersList");
        overrideFileWithArrayList(membersFile, memberList);
    }


    //Denne metode prompter brugeren til at skrive et navn, og den returnerer dette som et String-output.
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
        boolean isValid = true;
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c) && !isWhiteSpace(c) && c != '-' && c != '.') {
                isValid = false;
            }
        }
        return isValid;
    }


    //Denne metode prompter brugeren til at skrive en fødselsdato, og den returnerer denne som et String-output.
    public static String addBirthdate() {
        Scanner input = new Scanner(System.in);

        System.out.println("Indtast medlemmets fødseldsdag (ddMMyyyy):");
        boolean end = false;
        String birthdate;
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
                parseInt(birthdate.substring(2, 4)) <= 12 && parseInt(birthdate.substring(0, 2)) != 0 &&
                parseInt(birthdate.substring(2, 4)) != 0) {
            int year = parseInt(birthdate.substring(birthdate.length() - 4));
            int month = parseInt(birthdate.substring(2, 4));
            int date = parseInt(birthdate.substring(0, 2));
            Period period = Period.between(LocalDate.of(year, month, date), LocalDate.now());
            int age = period.getYears();
            return age >= 6 && age <= 140;
        } else {
            return false;
        }
    }


    //Denne metode prompter brugeren til at vælge, hvorvidt medlemmet er aktivt, og outputtet er en string enten
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


    //Denne metode prompter brugeren til at vælge, hvilken medlemstype, medlemmet er, og outputtet er en string enten
    // lig "motionist" eller "konkurrencesvømmer".
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


    //Denne metode prompter brugeren til at skrive en telefonnummer, og den returnerer dette som et String-output.
    public static String addPhoneNo() {

        Scanner input = new Scanner(System.in);

        System.out.println("Indtast medlemmets telefonnummer:");
        String telephoneNo = input.nextLine();
        boolean end = false;
        while (!end) {

            //Undersøger, om telefonnummeret overholder kravene
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


    //Denne metode tjekker, om en String opfylder de krav, som et telefonnummer i systemet skal opfylde.
    public static boolean isValidPhoneNo(String telephoneNo) {
        return telephoneNo.length() == 8 && isNumeric(telephoneNo);
    }


    //Denne metode tjekker, om en String allerede findes i en fil, idet filen laves til en arrayList, og der scannes
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


    //Denne metode prompter brugeren til at skrive en e-mail, og den returnerer denne som et String-output.
    public static String addEmail() {
        Scanner input = new Scanner(System.in);

        String email = null;
        System.out.println("Indtast medlemmets e-mail:");
        String tempEmail = input.nextLine();
        boolean end = false;
        while (!end) {

            //Tjekker, om e-mailen overholder kravene
            if (!isValidEmail(tempEmail)) {
                System.out.println("Ugyldig e-mail. Prøv igen:");
                tempEmail = input.nextLine();
            } else {
                //Tjekker, om e-mailen allerede eksisterer i databasen
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


    //Denne metode tjekker, om en String opfylder de krav, som en e-mail i systemet skal opfylde.
    public static boolean isValidEmail(String tempEmail) {
        return tempEmail.contains("@") && tempEmail.contains(".dk") || tempEmail.contains(".com") || tempEmail.contains(".net")
                || tempEmail.contains(".co.uk") || tempEmail.contains(".gov");
    }


    //Denne metode giver brugeren mulighed for enten at vælge dags dato eller manuelt indtaste en dato.
    // Datoen returneres som et String-output.
    public static String addStartDate() {
        Scanner input = new Scanner(System.in);

        System.out.println("Sæt startdato til i dag? 1: Ja 2: Nej");
        String startDate = null;
        String userInput = input.nextLine();
        boolean end = false;
        while (!end) {
            switch (userInput) {
                case "1":  //Dags dato vælges
                    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("ddMMyyyy");
                    startDate = LocalDateTime.now().format(formatTime);
                    System.out.println("Startdatoen er blevet tilføjet.");
                    end = true;
                    break;
                case "2": //Brugeren skal manuelt indtaste en dato
                    System.out.println("Indtast startdato (ddMMyyyy)");
                    startDate = input.nextLine();
                    end = false;
                    while (!end) {
                        //Det undersøges, om den indtastede dato overholder kravene
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


    //Denne metode tjekker, om en String opfylder de krav, som en startdato i systemet skal opfylde.
    // Det antages, at Delfinen startede i 2005
    public static boolean isValidStartDate(String startDate) {
        return startDate.length() == 8 && isNumeric(startDate) && parseInt(startDate.substring(0, 2)) <= 31
                && parseInt(startDate.substring(2, 4)) <= 12
                && parseInt(startDate.substring(0, 2)) != 0 && parseInt(startDate.substring(2, 4)) != 0
                && parseInt(startDate.substring(4, 8)) >= 2005;
    }


    //Denne metode prompter brugeren til at vælge, om medlemmet har betalt kontingent, og outputtet er en boolean.
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
        Scanner input = new Scanner(System.in);
        System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn 3: Afslut");
        String userInput = input.nextLine();
        boolean matchFound = false;
        int lineNumber;
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
                            matchFound = true;
                        }
                        lineCounter++;
                    }
                    if (!matchFound) {
                        System.out.println("Der blev ikke fundet et match for navnet. Prøv igen.");
                    }
                    break;
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
        overrideFileWithArrayList(membersFile, memberList);
        System.out.println("Proces er afsluttet.");
    }


    //Denne metode undersøger, om et String-input udelukkende består af tal
    public static boolean isNumeric (String str){
        try {
            parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    //Herunder er 9 getters
    public String getBirthdate () {
            return birthdate;
    }

    public String getMemberStatus () {
            return memberStatus;
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


    //Herunder overrides toString, så der opnås et tilpasset print
    @Override
    public String toString () {
            return "Telefon: " + getTelephoneNo() + " ID: " + getID();
    }


    //Herunder er to setters
    public void setHasPayed(boolean hasPayed){
            this.hasPayed = hasPayed;
    }

    public void setMemberType (String memberType){
            this.memberType = memberType;
    }

}