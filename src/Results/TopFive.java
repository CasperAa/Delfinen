package Results;

import Members.CompetitionMember;
import Members.Member;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class TopFive {


    static ArrayList<Results> resultsArrayList;


    public static void main(String[] args) throws FileNotFoundException {
        fileReader();
    }

    public static void fileReader() throws FileNotFoundException {
        File[] memberFiles = new File("src/Files/membersResults").listFiles();

        resultsArrayList = new ArrayList<Results>();

        for (File currentFile : memberFiles) {

            Scanner scanCurrentFile = new Scanner(currentFile);

            //Skipper metadatalinjen
            scanCurrentFile.nextLine();


            //While-loop, så alle linjer læses
            while (scanCurrentFile.hasNext()) {

                //En variabel, som indeholder den nuværende linje
                String currentRow = scanCurrentFile.nextLine();

                String[] lineAsArray = currentRow.split(";");

                String type = lineAsArray[0].trim();
                String date = lineAsArray[1].trim();
                double time = Double.parseDouble(lineAsArray[2].trim());
                String method = lineAsArray[3].trim();
                String competitionName = null;
                int placement = 0;
                if(type.equals("Konkurrence")) {
                    competitionName = lineAsArray[4].trim();
                    placement = Integer.parseInt(lineAsArray[5].trim());
                }

                if (type.equals("Konkurrence")) {
                    CompetitionResults currentCompetitionResult = new CompetitionResults(time, date, method, type, competitionName, placement);
                    resultsArrayList.add(currentCompetitionResult);
                } else {
                    Results currentResult = new Results(time, date, method, type);
                    resultsArrayList.add(currentResult);
                }

            }

        }
        System.out.println(resultsArrayList.toString());
    }

    @Override
    public String toString(){
        return CompetitionResults.getCompetitionName();
    }
}
