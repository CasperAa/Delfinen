package Results;

import Menu.MainMenu;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        boolean dateIsValid = false;
        String date = null;
        while (!dateIsValid) {
            date = userInput.nextLine();
            if (!dateValidation(date)) {
                date = null;
                MainMenu.errorMessage();
            } else {
                dateIsValid = true;
            }
        }


        System.out.println("Tid: (i sekunder - Eksemble: 62.23)");
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
    public static boolean dateValidation(String date) {
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

    static boolean checkDate(String date) {
        String pattern = "(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[0-2])\\/([0-9]{4})";
        boolean flag = false;
        if (date.matches(pattern)) {
            flag = true;
        }
        return flag;
    }
}
