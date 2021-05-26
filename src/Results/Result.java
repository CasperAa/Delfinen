package Results;

import Members.Member;
import Menu.MainMenu;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

//@Casper

public class Result {

    double resultTime;
    String date;
    String swimType;
    String resultType;
    String competitionName;
    String placement;

    //Constructor
    public Result(double resultTime, String date, String swimType, String resultType, String competitionName, String placement){
        this.resultTime = resultTime;
        this.date = date;
        this.swimType = swimType;
        this.resultType = resultType;
        this.competitionName = competitionName;
        this.placement = placement;
    }

    //Method for adding a new CSV file
    public static void addNewCSVFile(){
        //Asking for input - UserID to compare with exiting files in membersResult package
        Scanner userID = new Scanner(System.in);
        System.out.println("user ID");
        String memberID = userID.nextLine();

        //Verifying input from user, needs to be a length of four chars
        if(memberID.length() != 4){
            MainMenu.errorMessage();
            userID.nextLine();
        }

        File file = new File("src/Files/membersResults/" + memberID);

        //test to see if a file exists, asking user before adding new file, if file doesn't exist
        boolean exists = file.exists();
        if (exists) {
            System.out.println("Denne fil eksisterer - Tilhøre: " + returnMemberName(memberID));
            String fileLocation = "src/Files/membersResults/";
            resultType(fileLocation+memberID);

        } else {
            System.out.println("Denne fil eksisterer ikke\n");
            System.out.println("Vil du oprette en ny fil for dette ID?  ja/nej");

            switch (userID.nextLine()){
                //Adding new file
                case "ja":
                    String fileLocation = "src/Files/membersResults/";
                    Result.generateCsvFile(fileLocation+memberID);

                    System.out.println("Registrer resultat? ja/nej");
                    switch (userID.nextLine()){
                        case "ja":
                            resultType(fileLocation+memberID);
                        case "nej":
                            MainMenu.loginScreen();
                            break;
                        default:
                            MainMenu.errorMessage();
                            userID.nextLine();
                    }
                    break;
                //Not adding a new file
                case "nej":
                    MainMenu.loginScreen();
                    break;
                default:
                    MainMenu.errorMessage();
                    userID.nextLine();

            }
        }
    }

    //Setting result type
    public static void resultType (String fileLocation) {
        System.out.println("Resultat Type:\n"  + "     Tryk 1: Træning\n" + "     Tryk 2: Konkurrence");
        Scanner userInput = new Scanner(System.in);
        String resultypeUserInput = userInput.nextLine();
        if (resultypeUserInput.equals("1")){
            addResult(fileLocation, "Træning");
        } else if (resultypeUserInput.equals("2")){
            addResult(fileLocation, "Konkurrence");
        } else {
            MainMenu.errorMessage();
            userInput.nextLine();
        }
    }

    //Adding values to the remaining attributes of the object
    public static void addResult(String fileLocation, String resultType) {

        //Create object in file placed at location
        File file = new File(fileLocation);

        Scanner userInput = new Scanner(System.in);

        //Adding date value and checking input for validity
        System.out.println("Dato: (DD/MM/ÅÅÅÅ)");
        boolean dateIsValid = false;
        String date = null;
        while (!dateIsValid) {
            date = userInput.nextLine();
            if (!dateFormatValidator(date)) {
                date = null;
                MainMenu.errorMessage();
            } else {
                dateIsValid = true;
            }
        }

        //Adding swim discipline value and checking input for validity
        System.out.println("Svømmedisciplin:\n" + "     Tryk 1: Butterfly\n" + "     Tryk 2: Crawl\n" + "     Tryk 3: Rygcrawl\n" + "     Tryk 4: Brystsvømning");
        String method = userInput.nextLine();
        String swimMethod = null;
        switch (Integer.parseInt(method)) {
            case 1:
                swimMethod = "Butterfly";
                break;
            case 2:
                swimMethod = "Crawl";
                break;
            case 3:
                swimMethod = "Rygcrawl";
                break;
            case 4:
                swimMethod = "Brystsvømning";
                break;
            default:
                MainMenu.errorMessage();
                userInput.nextLine();
        }

        //Adding swim time value and checking input for validity
        System.out.println("Tid: (i sekunder - 62.23)");
        boolean timeIsValid = false;
        String time = null;
        while (!timeIsValid) {
            time = userInput.nextLine();
            try {
                Double.parseDouble(time);
                timeIsValid = true;

            } catch (Exception e) {
                MainMenu.errorMessage();
            }
        }

        //Adding competition name and placement values, if the result is from a competition and checking input for validity
        String competition;
        String placement;
        if(resultType.equals("Konkurrence")) {
            System.out.println("Konkurrence Navn:");
            competition = userInput.nextLine();

            System.out.println("Placering:");
            placement = userInput.nextLine();
            try {
                Integer.parseInt(placement);
            } catch (Exception e) {
                placement = null;
                MainMenu.errorMessage();
                userInput.nextLine();
            }
        //Giving competition name and placement empty values, if result is from training
        } else {
            competition = "";
            placement = "0";
        }

        try {
            //Create FileWriter object with file as parameter
            FileWriter fWriter = new FileWriter(file, true);
            BufferedWriter bWriter = new BufferedWriter(fWriter);

            //Creating String with all values from above
            String data = "\n" + resultType + ";" + date + ";" + time + ";" + swimMethod + ";" + competition + ";" + placement;
            bWriter.write(data);
            bWriter.close();

            System.out.println("Resultat blev tilføjet til fil: " + fileLocation.substring(fileLocation.length() - 4));

            //asking if user want's to add more results
            System.out.println("Opret flere resultater? ja/nej");
            switch (userInput.nextLine()) {
                case "ja":
                    addNewCSVFile();
                    break;
                case "nej":
                    MainMenu.menuScreenTrainer();
                    break;
                default:
                    MainMenu.errorMessage();
                    userInput.nextLine();
                    break;
            }

            //Catching IOException
        } catch (IOException e) {
            System.out.println("Der skete en fejl - Resultatet blev ikke tilføjet");
            e.printStackTrace();
        }
    }


    //Method for creating CSV files
    public static void generateCsvFile(String fileLocation) {

        FileWriter fWriter;
        BufferedWriter bWriter = null;

        try {

            fWriter = new FileWriter(fileLocation);
            bWriter = new BufferedWriter(fWriter);

            //Adding metadata to row one
            bWriter.write("Resultat Type" + ";" + "Dato" + ";" + "Tid" + ";" + "Svømnings Metode" + ";" + "Konkurrence Navn" + ";" + "Placering");

            //Confirming file has been created
            System.out.println("Filen \""+fileLocation.substring(fileLocation.length() - 4)+"\" er oprettet... ");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bWriter != null;
                bWriter.flush();
                bWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Date format validator
    public static boolean dateFormatValidator(String date) {
        boolean status = false;
        if (checkDate(date)) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(date);
                status = true;
            } catch (Exception e) {
                status = false;
            }
        }
        return status;
    }

    //Valid date validator
    static boolean checkDate(String date) {
        String pattern = "(0?[1-9]|[12][0-9]|3[01])(0?[1-9]|1[0-2])([0-9]{4})";
        boolean flag = false;
        if (date.matches(pattern)) {
            flag = true;
        }
        return flag;
    }

    //Returning member name based on their ID
    public static String returnMemberName(String memberID){
        String name = null;
        for (Member members : Member.getMemberList()){
            if(members.getID().equals(memberID)){
                name = members.getName();
            }
        } return name;
    }

    public double getResultTime(){
        return resultTime;
    }
}
