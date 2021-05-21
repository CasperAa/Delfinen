package Results;


import Menu.MainMenu;
import ValidityChecker.DateAndTime;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class Results {

    String resultTime;
    String date;
    String swimType;
    String resultType;

    public Results (String resultTime, String date, String swimType, String resultType){
        this.resultTime = resultTime;
        this.date = date;
        this.swimType = swimType;
        this.resultType = resultType;
    }

    public static void addNewCSVFile(){
        Scanner userID = new Scanner(System.in);
        System.out.println("user ID");
        String memberID = userID.nextLine();

        // test to see if a file exists
        File file = new File("src/Files/membersResults/" + memberID);
        boolean exists = file.exists();
        if (exists) {
            System.out.println("Denne fil eksitere!");
            String fileLocation = "src/Files/membersResults/";
            resultType(fileLocation+memberID);
        } else {
            System.out.println("Denne fil eksitere ikke\n");
            System.out.println("Vil du oprette en ny fil for dette ID?  ja/nej");
            switch (userID.nextLine()){
                case "ja":
                    String fileLocation = "src/Files/membersResults/";
                    generateCsvFile(fileLocation+memberID);

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
                case "nej":
                    MainMenu.loginScreen();
                    break;
                default:
                    MainMenu.errorMessage();
                    userID.nextLine();

            }
        }
    }

    public static void resultType (String fileLocation) {
        System.out.println("Resultat Type:\n"  + "     Tryk 1: Træning\n" + "     Tryk 2: Konkurrence");
        Scanner userInput = new Scanner(System.in);
        String resultypeUserInput = userInput.nextLine();
        if (resultypeUserInput.equals("1")){
            addResultTraining(fileLocation);
        } else if (resultypeUserInput.equals("2")){
            CompetitionResults.addResultCompetition(fileLocation);
        } else {
            MainMenu.errorMessage();
            userInput.nextLine();
        }
    }


public static void addResultTraining(String fileLocation) {
    // first create file object for file placed at location
    // specified by filepath
    File file = new File(fileLocation);

    Scanner userInput = new Scanner(System.in);

        String resultType = "Træning";

        System.out.println("Dato: (DD/MM/ÅÅÅÅ)");
        String date = userInput.nextLine();
        if (!DateAndTime.dateValidation(date)){
            MainMenu.errorMessage();
            userInput.nextLine();
        }

        System.out.println("Tid: (i sekunder - 62,23)");
        String time = userInput.nextLine();

        try {
        Integer.parseInt(time);
        }
        catch (Exception e) {
        MainMenu.errorMessage();
        userInput.nextLine();
        }

        System.out.println("Metode:\n" + "     Tryk 1: Butterfly\n" + "     Tryk 2: Crawl\n" + "     Tryk 3: Rygcrawl\n" + "     Tryk 4: Brystsvømning");
        String method = userInput.nextLine();
        String swimMethod = null;
        switch (Integer.parseInt(method)){
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


        try {
            // create FileWriter object with file as parameter
            FileWriter fWriter = new FileWriter(file,true);
            BufferedWriter bWriter = new BufferedWriter(fWriter);

            // add data to csv

            String data = "\n" + resultType + ";" + date + ";" + time + ";" + swimMethod +  ";" + "N/A" +  ";" + "N/A";
            bWriter.write(data);
            bWriter.close();     //Handlingen sker rent faktisk
            System.out.println("Resultat blev tilføjet til fil: " + fileLocation.substring(fileLocation.length()-4));

            System.out.println("Opret flere resultater? ja/nej");
            switch (userInput.nextLine()){
                case "ja":
                    addNewCSVFile();
                case "nej":
                    MainMenu.loginScreen();
                default:
                    MainMenu.errorMessage();
                    userInput.nextLine();
            }

            // closing writer connection
        } catch (IOException e) {
            System.out.println("Der skete en fejl - Resultatet blev ikke tilføjet");
            e.printStackTrace();
        }
    }

    public static void generateCsvFile(String fileLocation) {

        FileWriter fWriter;
        BufferedWriter bWriter = null;

        try {

            fWriter = new FileWriter(fileLocation);
            bWriter = new BufferedWriter(fWriter);
            bWriter.write("Resultat Type" + ";" + "Dato" + ";" + "Tid" + ";" + "Svømnings Metode" + ";" + "Konkurrence Navn" + ";" + "Placering");

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

}
