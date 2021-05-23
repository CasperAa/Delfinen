package Results;

import Menu.MainMenu;
import ValidityChecker.DateAndTime;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TrainigResults extends SuperResult{

    public TrainigResults(double resultTime, String date, String swimType, String resultType, String competitionName, String placement){
        super(resultTime, date, swimType, resultType, competitionName, placement);
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

        System.out.println("Tid: (i sekunder - Eksemble: 62.23)");
        String time = userInput.nextLine();
        try {
        Double.parseDouble(time);
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
            String data = "\n" + resultType + ";" + date + ";" + time + ";" + swimMethod + ";" + " " + ";" + 0;
            bWriter.write(data);
            bWriter.close();     //Handlingen sker rent faktisk
            System.out.println("Resultat blev tilføjet til fil: " + fileLocation.substring(fileLocation.length()-4));

            System.out.println("Opret flere resultater? ja/nej");
            switch (userInput.nextLine()){
                case "ja":
                    SuperResult.addNewCSVFile();
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
}