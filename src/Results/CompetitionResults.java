package Results;

import java.io.*;
import java.util.Scanner;
import Menu.MainMenu;
import ValidityChecker.DateAndTime;
//@Casper

public class CompetitionResults extends SuperResult {


    public CompetitionResults(double resultTime, String date, String swimType, String resultType, String competitionName, String placement) {
        super(resultTime, date, swimType, resultType, competitionName, placement);
    }


    public static void addResultCompetition(String fileLocation) {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(fileLocation);

        Scanner userInput = new Scanner(System.in);

        String resultType = "Konkurrence";

        System.out.println("Dato: (DD/MM/ÅÅÅÅ)");
        boolean dateIsValid = false;
        String date = null;
        while (!dateIsValid) {
            date = userInput.nextLine();
            if (!DateAndTime.dateValidation(date)) {
                date = null;
                MainMenu.errorMessage();
            } else {
                dateIsValid = true;
            }
        }

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

        System.out.println("Metode:\n" + "     Tryk 1: Butterfly\n" + "     Tryk 2: Crawl\n" + "     Tryk 3: Rygcrawl\n" + "     Tryk 4: Brystsvømning");
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

        System.out.println("Konkurrence Navn:");
        String competition = userInput.nextLine();

        System.out.println("Placering:");
        String placement = userInput.nextLine();
        try {
            Integer.parseInt(placement);
        } catch (Exception e) {
            placement = null;
            MainMenu.errorMessage();
            userInput.nextLine();
        }


        try {
            // create FileWriter object with file as parameter
            FileWriter fWriter = new FileWriter(file, true);
            BufferedWriter bWriter = new BufferedWriter(fWriter);

            // add data to csv

            String data = "\n" + resultType + ";" + date + ";" + time + ";" + swimMethod + ";" + competition + ";" + placement;
            bWriter.write(data);
            bWriter.close();     //Handlingen sker rent faktisk
            System.out.println("Resultat blev tilføjet til fil: " + fileLocation.substring(fileLocation.length() - 4));

            System.out.println("Opret flere resultater? ja/nej");
            switch (userInput.nextLine()) {
                case "ja":
                    addNewCSVFile();
                    break;
                case "nej":
                    MainMenu.loginScreen();
                    break;
                default:
                    MainMenu.errorMessage();
                    userInput.nextLine();
                    break;
            }

            // closing writer connection
        } catch (IOException e) {
            System.out.println("Der skete en fejl - Resultatet blev ikke tilføjet");
            e.printStackTrace();
        }
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public String getPlacement() {
        return placement;
    }
}



