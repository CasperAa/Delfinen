package Results;

import Menu.MainMenu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Result {

    double resultTime;
    String date;
    String swimType;
    String resultType;
    String competitionName;
    String placement;

    public Result(double resultTime, String date, String swimType, String resultType, String competitionName, String placement){
        this.resultTime = resultTime;
        this.date = date;
        this.swimType = swimType;
        this.resultType = resultType;
        this.competitionName = competitionName;
        this.placement = placement;
    }

    public static void addNewCSVFile(){
        Scanner userID = new Scanner(System.in);
        System.out.println("user ID");
        String memberID = userID.nextLine();

        if(memberID.length() != 4){
            MainMenu.errorMessage();
            userID.nextLine();
        }

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
                    TrainingResults.generateCsvFile(fileLocation+memberID);

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
            TrainingResults.addResultTraining(fileLocation);
        } else if (resultypeUserInput.equals("2")){
            CompetitionResults.addResultCompetition(fileLocation);
        } else {
            MainMenu.errorMessage();
            userInput.nextLine();
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

    public double getResultTime(){
        return resultTime;
    }
}
