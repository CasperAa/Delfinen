package Members;
//@Amanda

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static Results.Result.generateCsvFile;
import static java.lang.Integer.parseInt;

public class Trainer extends Member {
    protected String discipline;
    static ArrayList<Trainer> trainerList = new ArrayList<Trainer>();
    static ArrayList<Member> currentTrainerList = new ArrayList<Member>();
    private static ArrayList<Integer> IDListeTrainer;

    //Constructor
    public Trainer(String name, String ID, String birthdate, String memberStatus, String memberGroup, String memberType,
                             String telephoneNo, String email, String startDate, boolean hasPayed, String discipline) {
        super(name, ID, birthdate, memberStatus, memberGroup, memberType,
                telephoneNo, email, startDate, hasPayed);

        this.discipline = discipline;
    }


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
                String discipline = lineAsArray[10].trim();

                Trainer newMember = new Trainer(name, ID, birthdate, memberStatus, memberGroup,
                        memberType, telephoneNo, email, startDate, hasPayed, discipline);
                trainerList.add(newMember);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fil mangler.");
        }

    }

    //Denne metode kalder andre metoder, der beder brugeren om at definere en ny træners attributter.
    // Til sidst kaldes en metode, der tilføjer træneren til filen TrainerList.
    public static void writeNewTrainer() {
        String name = addName();

        File trainersFile = new File("src/Files/TrainerList");

        String ID = decideIDNumber(trainersFile);

        String birthdate = addBirthdate();

        String memberStatus = addActivityStatus();

        String memberType = addTrainerMemberType();

        String telephoneNo = addPhoneNo();

        String email = addEmail();

        String startDate = addStartDate();

        String discipline = addDiscipline();


        addTrainerToFile(name, ID, birthdate, memberStatus, memberType, telephoneNo, email, startDate, discipline);
    }


    //Denne metode får som input en træners definerede attributter og tilføjer en linje med disse til filen
    // TrainerList
    public static void addTrainerToFile(String name, String ID, String birthdate, String memberStatus, String memberType,
                                        String telephoneNo, String email, String startDate, String discipline) {

        try {
            File trainersFile = new File("src/Files/TrainerList");

            //Filen redigeres
            FileWriter fw = new FileWriter(trainersFile, true);   //Filen bliver ikke overwritten.
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("\n" + name + ";" + ID + ";" + birthdate + ";" + memberStatus + ";" + "trainer" + ";"
                    + memberType +
                    ";" + telephoneNo + ";" + email + ";" + startDate + ";" + false + ";" + discipline);   //Træneren skal tilføjes til filen
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
        String discipline = memberToEdit.discipline;

        //Brugeren får mulighed for at vælge, hvilken attribut, der skal ændres, og metoden,
        // der tilføjer denne attribut, kaldes.
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
                    userInput = input.nextLine();
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
                telephoneNo, email, startDate, false, discipline);
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

    public static void editTrainerTeams() {
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

        readTrainerListAndMakeArray(file);

        if (currentTrainerList.size() != 0) {
            System.out.println("Nuværende liste:");
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
                                                getMemberType().equals(currentMember.getMemberType())) {
                                            System.out.println("Træneren træner ikke svømmere i denne aldersgruppe. " +
                                                    "Træneren træner " + trainerList.get(lineNumber).getMemberType()
                                                    + ", denne svømmer er " + currentMember.getMemberType() + ". " +
                                                    "Prøv igen.\nHvad er ID-nummeret på det medlem, du vil tilføje?");
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
                                                getMemberType().equals(currentMember.getMemberType())) {
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
                                    overrideFileWithArrayList(file, currentTrainerList);
                                    end3 = true;
                                    end4 = true;
                                    break;
                                case "2":
                                    end3 = true;
                                    break;
                                default:
                                    System.out.println("Input ikke forstået. Prøv igen.\nEr du færdig med at redigere? 1: Ja 2: Nej");
                                    userInput = input.nextLine();
                                    break;
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
                                    overrideFileWithArrayList(file, currentTrainerList);
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
                    case "3":
                        end = true;
                        break;
                    default:
                        System.out.println("Input ikke forstået. Prøv igen.");
                        userInput = sc.nextLine();
                        break;
                }
            }


        }
    }

    //Denne metode beder brugeren om at vælge mellem senior og junior og giver dette som et String-output.
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

    //Denne metode overrider en fil med oplysninger fra et array af trænere
    public static void overrideTrainerFileWithArrayList(File file, ArrayList<Trainer> arrayList){
        try{
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
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