package Members;
//@Amanda

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static Results.Result.generateCsvFile;

public class Trainer extends Member {
    static ArrayList<Trainer> trainerList = new ArrayList<>();
    static ArrayList<Member> currentTrainerList = new ArrayList<>();

    //Constructor
    public Trainer(String name, String ID, String birthdate, String memberStatus, String memberGroup, String memberType,
                             String telephoneNo, String email, String startDate, boolean hasPayed) {
        super(name, ID, birthdate, memberStatus, memberGroup, memberType,
                telephoneNo, email, startDate, hasPayed);
    }


    //Denne metode scanner filen TrainerList og tilføjer alle trænere som et trainer-objekt til arrayListen trainerList
    public static void readTrainersFromFileAndAddToArray() {
        trainerList.clear();

        try {
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

                Trainer newMember = new Trainer(name, ID, birthdate, memberStatus, memberGroup,
                        memberType, telephoneNo, email, startDate, hasPayed);
                trainerList.add(newMember);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fil mangler.");
        }

    }

    //Denne metode kalder andre metoder, der beder brugeren om at definere en ny træners attributter.
    // Til sidst kaldes en metode, der tilføjer træneren til filen TrainerList.
    public static void addNewTrainer() {
        String name = addName();

        File trainersFile = new File("src/Files/TrainerList");

        String ID = decideIDNumber(trainersFile);

        String birthdate = addBirthdate();

        String memberStatus = addActivityStatus();

        String memberType = addTrainerMemberType();

        String telephoneNo = addPhoneNo();

        String email = addEmail();

        String startDate = addStartDate();

        //Her tilføjes en linje med disse den nye træner til filen TrainerList
        try {
            //Filen redigeres
            FileWriter fw = new FileWriter(trainersFile, true);   //Filen bliver ikke overwritten.
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("\n" + name + ";" + ID + ";" + birthdate + ";" + memberStatus + ";" + "trainer" + ";"
                    + memberType +
                    ";" + telephoneNo + ";" + email + ";" + startDate + ";" + false);   //Træneren skal tilføjes til filen
            bw.close();     //Handlingen sker rent faktisk
            System.out.println("Medlem blev tilføjet");
        } catch (Exception e) {
            System.out.println("Der skete en fejl. Medlemmet blev ikke tilføjet.");
        }
    }


    //Denne metode får brugeren til at vælge en eksisterende træner og gør det muligt for denne at ændre trænerens
    // attributter. Filen TrainerList opdateres med de nye oplysninger, idet hele filen overrides.
    public static void editTrainerInfo() {

        //Her kaldes metoden, der læser TrainerList og tilføjer trænerobjekterne til arrayListen trainerList.
        readTrainersFromFileAndAddToArray();

        // Brugeren får mulighed for at søge efter træneren i arrayListen trainerList enten vha. ID eller navn.
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
                        //En linecounter holder sammen med attributten lineNumber styr på, hvilken træner,
                        // der skal ændres.
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

        //Trænerens attributter hentes, så de attributter, der ikke ændres, kan tilføjes, når træneren
        // oprettes på ny.
        String name = memberToEdit.name;
        String ID = memberToEdit.ID;
        String birthdate = memberToEdit.birthdate;
        String memberStatus = memberToEdit.memberStatus;
        String telephoneNo = memberToEdit.telephoneNo;
        String email = memberToEdit.email;
        String startDate = memberToEdit.startDate;

        //Brugeren får mulighed for at vælge, hvilken attribut, der skal ændres, og metoden,
        // der tilføjer denne attribut, kaldes.
        input = new Scanner(System.in);
        end = false;
        while (!end) {
            System.out.println("Hvad vil du ændre?\n1: Navn\n2: Fødselsdato\n3: Aktivitetsstatus" +
                    "\n4: Telefonnummer\n5: e-mail\n6: Startdato");
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
                default:
                    System.out.println("Input ikke forstået.");
                    break;
            }

            //Brugeren får mulighed for at afslutte ændringen af træneren eller ændre endnu en attribut.
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
                        System.out.println("Input ikke forstået, prøv igen.");
                        userInput2 = input.nextLine();
                        break;
                }
            }
        }

        //Træneren defineres med de evt. ændrede attributter
        Trainer updatedTrainer = new Trainer(name, ID, birthdate, memberStatus, "trainer", "trainer",
                telephoneNo, email, startDate, false);
        trainerList.set(lineNumber, updatedTrainer);

        //Filen TrainerFile overrides med oplysninger fra trainerList, deriblandt er den netop opdaterede træner.
        File trainersFile = new File("src/Files/TrainerList");
        overrideTrainerFileWithArrayList(trainersFile, trainerList);
    }


    //Denne metode bruges til at læse en fil med en træners hold og oprette et array med alle
    // konkurrencemedlemmer på holdet.
    public static void readTrainerListAndMakeArray(File trainerFile) {
        try {
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
        } catch (FileNotFoundException e){
            System.out.println("Fejl.");
        }
    }



    //Denne metode bruges til at redigere en træners hold
    public static void editTrainerTeam() {
        Scanner sc = new Scanner(System.in);

        //Først findes den træner, hvis hold, der skal redigeres
        Trainer trainerWithTeam = searchForTrainer();

        //Derefter oprettes en fil, hvis der ikke allerede findes én
        String trainerID = trainerWithTeam.getID();
        makeNewTrainerCSVFile(trainerID);
        File file = new File("src/TrainerFiles/" + trainerID);

        readTrainerListAndMakeArray(file);

        if (currentTrainerList.size() != 0) {
            System.out.println("Nuværende liste:");
            for (Member currentMember : currentTrainerList) {
                System.out.println("ID: " + currentMember.getID() + " Name: " + currentMember.getName());

            }
        } else {
            System.out.println("Listen er tom.");
        }

        //Brugeren skal så vælge, om han/hun vil tilføje eller slette et medlem
        System.out.println("Hvad ønsker du at gøre?\n1: Tilføj medlem 2: Slet medlem 3: Exit");
        String userInput = sc.nextLine();
        boolean end = false;
        readMembersFromFileAndAddToArray();
        while (!end) {
            switch (userInput) {
                case "1":
                    Member memberToAdd = addMemberToTeam(trainerWithTeam);
                    currentTrainerList.add(memberToAdd);
                    break;
                case "2":
                    int number = lineNumberOfmemberToDelete();
                    currentTrainerList.remove(number);
                    break;
                case "3":
                    end = true;
                    break;
                default:
                    System.out.println("Input ikke forstået. Prøv igen.\nHvad ønsker du at gøre?\n1: Tilføj medlem " +
                            "2: Slet medlem 3: Exit");
                    break;
            }

            //Brugeren får mulighed for at afslutte eller fortsætte redigeringen
            System.out.println("Er du færdig med at redigere? 1: Ja 2: Nej");
            userInput = sc.nextLine();
            boolean end2 = false;
            while (!end2) {
                switch (userInput) {
                    case "1":
                        overrideFileWithArrayList(file, currentTrainerList);
                        end = true;
                        end2 = true;
                        break;
                    case "2":
                        System.out.println("Hvad ønsker du at gøre?\n1: Tilføj medlem 2: Slet medlem 3: Exit");
                        userInput = sc.nextLine();
                        end2 = true;
                        break;
                    default:
                        System.out.println("Input ikke forstået. Prøv igen.\nEr du færdig med at redigere? 1: Ja 2: Nej");
                        userInput = sc.nextLine();
                        break;
                }
            }

        }
    }

    //Denne metode undersøger, om der allerede findes en fil for en træner, og hvis ikke, oprettes der én
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

    //Denne metode gør det muligt for brugeren at finde en bestemt træner fra filen TrainerList
    public static Trainer searchForTrainer() {
        Scanner sc = new Scanner(System.in);
        Trainer trainerWithTeam = null;
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
            for (Trainer currentTrainer : trainerList) {
                String currentID = currentTrainer.getID();
                if (currentID.equals(inputID)) {
                    matchFound = true;
                    trainerWithTeam = currentTrainer;

                }
            }
            if (!matchFound) {
                System.out.println("Der blev ikke fundet et match for ID'et. Prøv igen.");
            } else {
                end = true;
            }
        }

        return trainerWithTeam;
    }

    //Metoden her får brugeren til at søge efter medlemmet, der skal tilføjes, vha. navn eller ID. Medlemmet returneres.
    public static Member addMemberToTeam(Trainer trainerWithTeam) {
        Scanner input = new Scanner(System.in);
        Member memberToAdd = null;
        System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
        String userInput = input.nextLine();
        boolean matchFound = false;
        boolean end2 = false;
        while (!end2) {

            switch (userInput) {
                case "1":
                    System.out.println("Hvad er ID-nummeret på det medlem, du vil tilføje?");
                    String inputID = input.nextLine();

                    for (Member currentMember : memberList) {
                        String currentID = currentMember.getID();
                        if (currentID.equals(inputID) && trainerWithTeam.getMemberType().equals(currentMember.getMemberType())) {
                            System.out.println("Vil du tilføje følgende medlem?\n" + currentMember.getID() + ", " +
                                    currentMember.getName() + "\n1: Ja 2: Nej");
                            userInput = input.nextLine();
                            switch (userInput) {
                                case "1":
                                    memberToAdd = currentMember;
                                    end2 = true;
                                    matchFound = true;
                                    break;
                                case "2":
                                    System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                                    break;
                            }
                        } else if (currentID.equals(inputID) && !trainerWithTeam.getMemberType()
                                .equals(currentMember.getMemberType())) {
                            System.out.println("Træneren træner ikke svømmere i denne aldersgruppe. " +
                                    "Træneren træner " + trainerWithTeam.getMemberType()
                                    + ", denne svømmer er " + currentMember.getMemberType() + ". " +
                                    "Prøv igen.\nHvad er ID-nummeret på det medlem, du vil tilføje?");
                        }
                    }
                    if (!matchFound) {
                        System.out.println("Der blev ikke fundet et match for ID'et. Prøv igen.");
                    }
                    break;
                case "2":
                    System.out.println("Hvad er navnet på det medlem, du vil tilføje?");
                    String inputName = input.nextLine();
                    for (Member currentMember : memberList) {
                        if (currentMember.getName().toLowerCase().contains(inputName.toLowerCase())
                                && trainerWithTeam.getMemberType().equals(currentMember.
                                getMemberType())) {
                            System.out.println("Vil du tilføje følgende medlem?\n\n---ID: " + currentMember.getID() +
                                    "; Navn: " +
                                    currentMember.getName() + "---\n\n1: Ja 2: Nej");
                            userInput = input.nextLine();
                            switch (userInput) {
                                case "1":
                                    memberToAdd = currentMember;
                                    end2 = true;
                                    break;
                                case "2":
                                    System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                                    userInput = input.nextLine();
                                    break;
                            }
                            matchFound = true;
                        } else if (currentMember.getName().toLowerCase().contains(inputName.
                                toLowerCase()) && !trainerWithTeam.getMemberType()
                                .equals(currentMember.getMemberType())) {
                            System.out.println("Træneren træner ikke svømmere i denne aldersgruppe. " +
                                    "Træneren træner " + trainerWithTeam.getMemberType()
                                    + ", denne svømmer er " + currentMember.getMemberType() + ". " +
                                    "Prøv igen\nHvad er navnet på det medlem, du vil tilføje?");
                        }
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

        return memberToAdd;
    }


    //Herunder er kode, hvor brugeren søger efter medlemmet, der skal slettes, vha. navn eller ID.
    // Indexet for den linje, medlemmet står på i arrayListen, returneres.
    public static int lineNumberOfmemberToDelete(){
        Scanner input = new Scanner(System.in);
        System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
        String userInput = input.nextLine();
        boolean matchFound = false;
        int lineOfMemberInArray = 0;
        int lineCounter;
        boolean end = false;
        while (!end) {

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
                                    lineOfMemberInArray = lineCounter;
                                    end = true;
                                    break;
                                case "2":
                                    System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                                    userInput = input.nextLine();
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
                                    lineOfMemberInArray = lineCounter;
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

        return lineOfMemberInArray;
    }

    //Denne metode beder brugeren om at vælge mellem senior og junior og returnerer dette som et String-output.
    public static String addTrainerMemberType(){
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

    //Denne metode overrider en inputtet fil med oplysninger fra et inputtet array af trænere
    public static void overrideTrainerFileWithArrayList(File file, ArrayList<Trainer> arrayList){
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
}