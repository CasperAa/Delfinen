package Results;

import java.io.*;
import java.util.Scanner;
import Menu.MainMenu;

//@Casper

public class CompetitionResults extends Results {
    public static void main(String[] args){
        addNewCSVFile();
    }

    String competitionName;
    int placement;

    public CompetitionResults(String resultTime, String date, String swimType, String resultType, String competitionName, int placement) {
        super(resultTime, date, swimType, resultType);
        this.competitionName = competitionName;
        this.placement = placement;

    }

    public static void addNewCSVFile(){
        Scanner userID = new Scanner(System.in);
        System.out.println("user ID");
        String swimmerID = userID.nextLine();

        // test to see if a file exists
        File file = new File("src/Files/competitionMembers/" + swimmerID);
        boolean exists = file.exists();
        if (exists) {
            System.out.println("Denne fil eksitere ");
            String fileLocation = "src/Files/competitionMembers/";
            addResult(fileLocation+swimmerID);
        } else {
            System.out.println("Denne fil eksitere ikke\n");
            System.out.println("Vil du oprette en ny fil for dette ID?  ja/nej");
            switch (userID.nextLine()){
                case "ja":
                    String fileLocation = "src/Files/competitionMembers/";
                    generateCsvFile(fileLocation+swimmerID);

                    addResult(fileLocation+swimmerID);
                    break;
                case "nej":
                    break;
                default:
                    MainMenu.errorMessage();
            }
        }
    }


    public static void addResult(String fileLocation) {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(fileLocation);

        Scanner userInput = new Scanner(System.in);


        System.out.println("Resultat Type:\n"  + "     Tryk 1: Træning\n" + "     Tryk 2: Konkurrence");
        String resultypeUserInput = userInput.nextLine();
        String resultType = null;
        if (resultypeUserInput.equals("1")){
            resultType = "Træning";
        } else if (resultypeUserInput.equals("2")){
            resultType = "Konkurrence";
        } else {
            MainMenu.errorMessage();
            userInput.nextLine();
        }


        System.out.println("Dato: (DD/MM-ÅÅÅÅ)");
        String date = userInput.nextLine();
        if (date.length() != 10){
            MainMenu.errorMessage();
            userInput.nextLine();
        }

        System.out.println("Tid: (12:34)");
        String time = userInput.nextLine();
        if(time.length() != 5){
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

        System.out.println("Konkurrence Navn:");
        String competition = userInput.nextLine();

        System.out.println("Placering:");
        String placement = userInput.nextLine();
        try {
            Integer.parseInt(placement);
        }
        catch (Exception e){
            MainMenu.errorMessage();
            userInput.nextLine();
        }


        try {
            // create FileWriter object with file as parameter
            FileWriter fWriter = new FileWriter(file,true);
            BufferedWriter bWriter = new BufferedWriter(fWriter);

            // add data to csv

            String data = "\n" + resultType + ";" + date + ";" + time + ";" + swimMethod +  ";" + competition +  ";" + placement;
            bWriter.write(data);
            bWriter.close();     //Handlingen sker rent faktisk
            System.out.println("Resultat blev tilføjet til fil: " + fileLocation.substring(fileLocation.length()-4));


            // add data to csv

            // closing writer connection
        } catch (IOException e) {
            System.out.println("Der skete en fejl - Resultatet blev ikke tilføjet");
            e.printStackTrace();
        }
    }

    private static void generateCsvFile(String fileLocation) {

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

