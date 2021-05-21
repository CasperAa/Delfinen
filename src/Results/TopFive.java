package Results;

import Members.CompetitionMember;
import Members.Member;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class TopFive {


    private ArrayList<ConstructorData> resultsArrayList;

    private ArrayList<ConstructorData> seniorCrawl;
    private ArrayList<ConstructorData> seniorBreaststroke;
    private ArrayList<ConstructorData> seniorBufferfly;
    private ArrayList<ConstructorData> seniorRygcrawl;
    private ArrayList<ConstructorData> juniorCrawl;
    private ArrayList<ConstructorData> juniorBreaststroke;
    private ArrayList<ConstructorData> juniorBufferfly;
    private ArrayList<ConstructorData> juniorRygcrawl;


    public static void main(String[] args) throws FileNotFoundException {
        TopFive test = new TopFive();
        test.fileReader();
        //test.sortDataBySwimMethod();
    }

    public void fileReader() throws FileNotFoundException {
        File[] memberFiles = new File("src/Files/membersResults").listFiles();

        resultsArrayList = new ArrayList<>();

        assert memberFiles != null;
        for (File currentFile : memberFiles) {

            Scanner scanCurrentFile = new Scanner(currentFile);

            //Skipper metadatalinjen
            scanCurrentFile.nextLine();


            //While-loop, så alle linjer læses
            while (scanCurrentFile.hasNext()) {

                //En variabel, som indeholder den nuværende linje
                String currentRow = scanCurrentFile.nextLine();

                String[] lineAsArray = currentRow.split(";");

                String id = currentFile.getName();
                String type = lineAsArray[0].trim();
                String date = lineAsArray[1].trim();
                double time = Double.parseDouble(lineAsArray[2].trim());
                String method = lineAsArray[3].trim();
                String competitionName = lineAsArray[4].trim();
                String placement = lineAsArray[5].trim();


                ConstructorData currentCompetitionResult = new ConstructorData(id, time, date, method, type, competitionName, placement);
                resultsArrayList.add(currentCompetitionResult);
                System.out.println(id + " " + time + " " + date + " " + method + " " + type + " " + competitionName + " " + placement);


            }
        }
    }
/*
    public void sortDataBySwimMethod() {
        try {
            for (int i = 0; i < resultsArrayList.size(); i++) {
                if (resultsArrayList.get(i).swimType.equals("Brystsvømning")) {
                    seniorBreaststroke.add(resultsArrayList.get(i));
                    System.out.println(seniorBreaststroke.size());
                }
            }
        } catch (NullPointerException e){
            System.out.println("something is wrong");
        }
    }

 */
}
