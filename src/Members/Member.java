package Members;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;


//@Amanda
public class Member {

    private String name;
    private String ID;
    private String birthdate;
    private String memberStatus;
    private String memberGroup;
    private String memberType;
    private String telephoneNo;
    private String email;
    private String startDate;
    private boolean hasPayed;
    static ArrayList<Member> memberList = new ArrayList<Member>();

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


    public static void addMembersFromFileToArray() throws FileNotFoundException {
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


    public static void addAttributesToNewMemberAndAddMemberToFile() throws IOException {
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

            String tempID = "000" + (Collections.max(IDListe) + 1);
            ID = tempID.substring(tempID.length() - 4);
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

    //Nedenstående er ikke færdig
    public static void editMemberInfo() throws IOException {
        addMembersFromFileToArray();
        Scanner input = new Scanner(System.in);
        Member memberToEdit = null;
        System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
        String userInput = input.nextLine();
        int lineNumber = 0;
        boolean end = false;
        while (end == false) {

            switch (userInput) {
                case "1":
                    System.out.println("Hvad er ID-nummeret på det medlem, du vil redigere?");
                    String inputID = input.nextLine();
                    int lineCounter = 0;

                    //Nedenstående er den nye løsning, der ikke var nødvendig,da fejlen var i fil-læseren
                    /*
                    for (int i = 0; i < memberList.size(); i++) {

                        if (memberList.get(i).getID().equals(inputID)){
                            System.out.println("Vil du ændre følgende medlem?\n" + memberList.get(i).getName());
                            memberToEdit = memberList.get(i);
                            lineNumber = lineCounter;
                        }
                     */

                    for (Member currentMember : memberList){
                        String currentID = currentMember.getID();
                        if (currentID.equals(inputID)){
                            System.out.println("Vil du ændre følgende medlem?\n" + currentMember.getID() + ", " +
                                    currentMember.getName() + "\n1: Ja 2: Nej");
                            userInput = input.nextLine();
                            switch(userInput){
                                case "1":
                                    memberToEdit = currentMember;
                                    lineNumber = lineCounter;
                                    end = true;
                                    break;
                                case "2":
                                    System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                                    break;
                            }
                        }
                        lineCounter++;
                    }
                    break;
                case "2":
                    System.out.println("Hvad er navnet på det medlem, du vil redigere?");
                    String inputName = input.nextLine();
                    lineCounter = 0;
                    for (Member currentMember : memberList){
                        if (currentMember.getName().toLowerCase().contains(inputName.toLowerCase())){
                            System.out.println("Vil du ændre følgende medlem?\n\n---ID: " + currentMember.getID() + "; Navn: " +
                                    currentMember.getName() + "---\n\n1: Ja 2: Nej");
                            userInput = input.nextLine();
                            switch(userInput){
                                case "1":
                                    memberToEdit = currentMember;
                                    lineNumber = lineCounter;
                                    end = true;
                                    break;
                                case "2":
                                    System.out.println("Vil du søge efter ID eller navn? 1: ID 2: Navn");
                                    break;
                            }
                        }
                        lineCounter++;
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
        while(end==false) {
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
            while (end2 == false) {
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
        memberList.set(lineNumber,updatedMember);


        File membersFile = new File("src/Files/MembersList");
        FileWriter fw = new FileWriter(membersFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(membersFile);
        System.out.println("Memberlist inden redigering af liste");
        pw.write("name;ID;birthdate;memberStatus;memberGroup;memberType;telephoneNo;email;startDate;hasPayed");
        for (Member currentMember : memberList){
            pw.write("\n" + currentMember.name + ";" + currentMember.ID + ";" + currentMember.birthdate + ";" +
                    currentMember.memberStatus + ";" + currentMember.memberGroup + ";" + currentMember.memberType +
                    ";" + currentMember.telephoneNo + ";" + currentMember.email + ";" + currentMember.startDate + ";"
                    + currentMember.hasPayed);   //Medlemmet skal tilføjes til filen
        }
        pw.close();

    }


    public static String addName(){
        Scanner input = new Scanner(System.in);
        System.out.println("Indtast medlemmets fulde navn:");
        String name = input.nextLine();
        return name;
    }

    public static String addBirthdate(){
        Scanner input = new Scanner(System.in);

        System.out.println("Indtast medlemmets fødseldsdag (ddMMyyyy):");
        boolean end = false;
        String birthdate = null;
        birthdate = input.nextLine();
        while (!end){
            if(isValidBirthdate(birthdate)){
                birthdate = input.nextLine();
            } else{
                System.out.println("Fødselsdato er blevet tilføjet.");
                end = true;
            }
        }
        return birthdate;
    }

    public static boolean isValidBirthdate(String birthdate){
        if(birthdate.length() != 8 || !isNumeric(birthdate) || parseInt(birthdate.substring(0,2))>31 ||
                parseInt(birthdate.substring(2,4))>12 || parseInt(birthdate.substring(0,2))==00 ||
                parseInt(birthdate.substring(2,4))==00||parseInt(birthdate.substring(4,8))>2021||
                parseInt(birthdate.substring(4,8))<1900){
            System.out.println("Ugyldig fødselsdato\nFormatet er ‘ddMMyyyy’\nÅrstal skal tidligst være 140 år " +
                    "før dags dato samt minimum 6 år fra dags dato\nDato skal være mellem 1 og 31\nMåned skal " +
                    "være mellem 1 og 12\n\nIndtast medlemmets fødseldsdag (ddMMyyyy):");
            return false;
        } else{
            return true;
        }
    }

    public static String addActivityStatus(){
        Scanner input = new Scanner(System.in);
        System.out.println("Er medlemmet aktivt? 1: Ja 2: Nej");
        boolean end = false;
        String memberStatus = null;
        String userInput = input.nextLine();
        while (end == false) {

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

    public static String addMemberType(){
        Scanner input = new Scanner(System.in);
        System.out.println("Hvad er medlemstypen? 1: Motionist 2: Konkurrencesvømmer");
        String memberGroup = null;
        String userInput = input.nextLine();
        boolean end = false;
        while (end == false) {
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

    public static String addPhoneNo() {
        Scanner input = new Scanner(System.in);

        System.out.println("Indtast medlemmets telefonnummer:");
        String telephoneNo = input.nextLine();
        boolean end = false;
        while (end == false) {
            if (telephoneNo.length() != 8 || !isNumeric(telephoneNo)) {
                System.out.println("Ugyldigt telefonnummer. Prøv igen:");
                telephoneNo = input.nextLine();
            } else {
                System.out.println("Telefonnummeret er blevet tilføjet.");
                end = true;

            }
        }
        return telephoneNo;
    }

    public static String addEmail(){
        Scanner input = new Scanner(System.in);

        String email = null;
        System.out.println("Indtast medlemmets e-mail:");
        String tempEmail = input.nextLine();
        boolean end = false;
        while (end == false){
            if(tempEmail.contains("@") && tempEmail.contains(".dk") || tempEmail.contains(".com") || tempEmail.contains(".net")
                    || tempEmail.contains(".co.uk") || tempEmail.contains(".gov")){
                email = tempEmail;
                System.out.println("E-mailen er blevet tilføjet.");
                end = true;
            } else{
                System.out.println("Ugyldig e-mail. Prøv igen:");
                tempEmail = input.nextLine();
            }
        }
        return email;
    }

    public static String addStartDate(){
        Scanner input = new Scanner(System.in);

        System.out.println("Sæt startdato til i dag? 1: Ja 2: Nej");
        String startDate = null;
        String userInput= input.nextLine();
        boolean end = false;
        while(end == false){
            switch (userInput) {
                case "1":
                    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("ddMMyyyy");
                    startDate = LocalDateTime.now().format(formatTime);
                    end = true;
                    break;
                case "2":
                    System.out.println("Indtast startdato (ddMMyyyy)");
                    startDate = input.nextLine();
                    end = false;
                    while (end == false){
                        if(startDate.length() != 8 || !isNumeric(startDate) || parseInt(startDate.substring(0,2))>31
                                || parseInt(startDate.substring(2,4))>12
                                || parseInt(startDate.substring(0,2))==00 || parseInt(startDate.substring(2,4))==00
                                || parseInt(startDate.substring(4,8))>2021||parseInt(startDate.substring(4,8))<1900){
                            System.out.println("Ugyldig startdato\nFormatet er ‘ddMMyyyy’\nÅrstal skal tidligst være " +
                                    "140 år før dags " +
                                    "dato samt minimum 6 år fra dags dato\nDato skal være mellem 1 og 31\nMåned skal " +
                                    "være mellem 1 og 12\n\nIndtast medlemmets startdato (ddMMyyyy):");
                            startDate = input.nextLine();
                        } else{
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

    public static boolean addPaymentStatus(){
        Scanner input = new Scanner(System.in);

        System.out.println("Har medlemmet betalt kontingent? 1: Ja 2: Nej");
        boolean hasPayed = false;
        String userInput= input.nextLine();
        boolean end = false;
        while(end == false){
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
                    System.out.println("Input ikke forstået. Prøve igen.\nHar medlemmet betalt kontingent? 1: Ja " +
                            "2: Nej");
                    userInput = input.nextLine();
                    break;
            }
        }
        return hasPayed;
    }


    public static void main (String[]args) throws IOException {
        editMemberInfo();
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

        public String getName() {
            return name;
        }

        public String getID() {
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
    public String toString() {
        return "Telefon: " + getTelephoneNo() + " ID: " + getID();
    }
}
