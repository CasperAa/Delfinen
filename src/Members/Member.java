package Members;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;


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

    public static void memberReader() throws FileNotFoundException {
        File membersFile = new File("src/Files/MembersList");
        Scanner sc = new Scanner(membersFile);

        //Skipper metadatalinjen
        sc.nextLine();

        //While-loop, så alle linjer læses
        while (sc.hasNext()) {
            //En variabel, som indeholder den nuværende linje
            String currentPizza = sc.nextLine();

            String[] lineAsArray = currentPizza.split(";");

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
                CompetitionMember newMember = new CompetitionMember(name, ID, birthdate, memberStatus, memberGroup, memberType,
                        telephoneNo, email, startDate, hasPayed);
                memberList.add(newMember);
            } else {
                System.out.println("Fejl i tilføjelse af medlem");
            }
        }

    }


    public static void AddingProcess() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Indtast medlemmets fulde navn:");
        String name = input.nextLine();

        System.out.println("Indtast medlemmets fødseldsdag (ddMMyyyy):");
        boolean end = false;
        String birthdate = null;
        birthdate = input.nextLine();
        while (end == false){
            if(birthdate.length() != 8 || !isNumeric(birthdate) || parseInt(birthdate.substring(0,2))>31 || parseInt(birthdate.substring(2,4))>12
                    || parseInt(birthdate.substring(0,2))==00 || parseInt(birthdate.substring(2,4))==00||parseInt(birthdate.substring(4,8))>2021||parseInt(birthdate.substring(4,8))<1900){
                System.out.println("Ugyldig fødselsdato\nFormatet er ‘ddMMyyyy’\nÅrstal skal tidligst være 140 år før dags " +
                        "dato samt minimum 6 år fra dags dato\nDato skal være mellem 1 og 31\nMåned skal være mellem 1 og 12\n\nIndtast medlemmets fødseldsdag (ddMMyyyy):");
                birthdate = input.nextLine();
            } else{
                System.out.println("Fødselsdato er blevet tilføjet.");
                end = true;
            }

        }

        System.out.println("Er medlemmet aktivt? 1: Ja 2: Nej");
        String memberStatus = null;
        String userInput = input.nextLine();
        end = false;
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
                        System.out.println("Input ikke forstået. Prøve igen.\nEr medlemmet aktivt? 1: Ja 2: Nej");
                        userInput = input.nextLine();
                        break;
                }
        }

        System.out.println("Hvad er medlemstypen? 1: Motionist 2: Konkurrencesvømmer");
        String memberGroup = null;
        userInput= input.nextLine();
        end = false;
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
                    System.out.println("Input ikke forstået. Prøve igen.\nHvad er medlemstypen? 1: Motionist 2: Konkurrencesvømmer");
                    userInput = input.nextLine();
                    break;
            }
        }

        System.out.println("Indtast medlemmets telefonnummer:");
        String telephoneNo = input.nextLine();
        end = false;
        while (end == false){
            if(telephoneNo.length() != 8 || !isNumeric(telephoneNo)){
                System.out.println("Ugyldigt telefonnummer. Prøv igen:");
                telephoneNo = input.nextLine();
            } else{
                System.out.println("Telefonnummeret er blevet tilføjet.");
                end = true;
            }
        }

        System.out.println("Indtast medlemmets e-mail:");
        String email = input.nextLine();
        end = false;
        while (end == false){
            if(!email.contains("@")){
                System.out.println("Ugyldig e-mail. Prøv igen:");
                email = input.nextLine();
            } else{
                System.out.println("Emailen er blevet tilføjet.");
                end = true;
            }
        }

        System.out.println("Sæt startdato til i dag? 1: Ja 2: Nej");
        String startDate = null;
        userInput= input.nextLine();
        end = false;
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
                        if(startDate.length() != 8 || !isNumeric(startDate) || parseInt(startDate.substring(0,2))>31 || parseInt(startDate.substring(2,4))>12
                                || parseInt(startDate.substring(0,2))==00 || parseInt(startDate.substring(2,4))==00||parseInt(startDate.substring(4,8))>2021||parseInt(startDate.substring(4,8))<1900){
                            System.out.println("Ugyldig startdato\nFormatet er ‘ddMMyyyy’\nÅrstal skal tidligst være 140 år før dags " +
                                    "dato samt minimum 6 år fra dags dato\nDato skal være mellem 1 og 31\nMåned skal være mellem 1 og 12\n\nIndtast medlemmets startdato (ddMMyyyy):");
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

        System.out.println("Har medlemmet betalt kontingent? 1: Ja 2: Nej");
        boolean hasPayed = false;
        userInput= input.nextLine();
        end = false;
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
                    System.out.println("Input ikke forstået. Prøve igen.\nHar medlemmet betalt kontingent? 1: Ja 2: Nej");
                    userInput = input.nextLine();
                    break;
            }
        }

            MemberAdder(name, birthdate, memberStatus, memberGroup, telephoneNo, email, startDate, hasPayed);
    }


        private static ArrayList<Integer> IDListe;

        public static void MemberAdder (String name, String birthdate, String memberStatus, String memberGroup,
                String telephoneNo, String email, String startDate,boolean hasPayed) throws IOException {

            try {
                //Nedenstående bestemmer et ID-nummer, der er én højere end det hidtil højeste ID.
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

                bw.write("\n" + name + ";" + ID + ";" + birthdate + ";" + memberStatus + ";" + memberGroup + ";" + memberType +
                        ";" + telephoneNo + ";" + email + ";" + startDate + ";" + hasPayed);   //Medlemmet skal tilføjes til filen
                bw.close();     //Handlingen sker rent faktisk
                System.out.println("Medlem blev tilføjet");
            } catch (Exception e) {
                System.out.println("Der skete en fejl. Medlemmet blev ikke tilføjet.");
            }


        }


        public static void main (String[]args) throws IOException {
            AddingProcess();
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

        public static boolean birthdateChecker(String birthdate){
            try{
                Date birthdateTest=new SimpleDateFormat("ddMMyyyy").parse(birthdate);
                System.out.println("Fødselsdato er blevet tilføjet.");
                return true;
            }
            catch (Exception e){
                System.out.println("Ugyldig fødselsdato\nFormatet er ‘ddMMyyyy’\nÅrstal skal tidligst være 140 år før dags " +
                        "dato samt minimum 6 år fra dags dato\nDatoskal være mellem 1 og 31\nMåned skal være mellem 1 og 12");
                return false;
            }
        }

    }
