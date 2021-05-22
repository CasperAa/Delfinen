package Results;

import Members.CompetitionMember;
import Members.Member;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TopFive {


    private ArrayList<ConstructorData> resultsArrayList;
    private ArrayList<ConstructorData> seniorCrawl, seniorBreaststroke,seniorButterfly, seniorRygcrawl,juniorCrawl, juniorBreaststroke, juniorButterfly, juniorRygcrawl;


    //test main menu
    public static void main(String[] args) throws FileNotFoundException {
        TopFive test = new TopFive();
        Member.readMembersFromFileAndAddToArray();
        test.fileReader();
        test.sortDataBySwimMethod();
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
                String competitionName = checkForValue(lineAsArray[4].trim());
                String placement = lineAsArray[5].trim();


                ConstructorData currentCompetitionResult = new ConstructorData(id, time, date, method, type, competitionName, placement);
                resultsArrayList.add(currentCompetitionResult);

            }
        }
    }
    public void sortDataBySwimMethod() {
        seniorBreaststroke = new ArrayList<>();
        seniorCrawl = new ArrayList<>();
        seniorButterfly = new ArrayList<>();
        seniorRygcrawl = new ArrayList<>();
        juniorBreaststroke = new ArrayList<>();
        juniorCrawl = new ArrayList<>();
        juniorButterfly = new ArrayList<>();
        juniorRygcrawl = new ArrayList<>();

        ArrayList <Member> allMembers = Member.getMemberList();

        try {
            for (ConstructorData currentDataRow : resultsArrayList) {
                for(Member currentMember : allMembers){
                    if(currentDataRow.id.equals(currentMember.getID())) {
                        switch (currentMember.getMemberType()) {
                            case "senior":
                                switch (currentDataRow.swimType) {
                                    case "Brystsvømning":
                                        seniorBreaststroke.add(currentDataRow);
                                        break;
                                    case "Crawl":
                                        seniorCrawl.add(currentDataRow);
                                        break;
                                    case "Butterfly":
                                        seniorButterfly.add(currentDataRow);
                                        break;
                                    case "Rygcrawl":
                                        seniorRygcrawl.add(currentDataRow);
                                        break;
                                }
                                break;

                            case "junior":
                                switch (currentDataRow.swimType) {
                                    case "Brystsvømning":
                                        juniorBreaststroke.add(currentDataRow);
                                        break;
                                    case "Crawl":
                                        juniorCrawl.add(currentDataRow);
                                        break;

                                    case "Butterfly":
                                        juniorButterfly.add(currentDataRow);
                                        break;

                                    case "Rygcrawl":
                                        juniorRygcrawl.add(currentDataRow);
                                        break;
                                }
                                break;
                        }
                    }
                }
            }
        } catch (NullPointerException e){
            System.out.println("something went wrong");
    }


    }
    public static String checkForValue(String text){
        if (text.equals("")) {
            return "N/A";
        } else {
            return text;
        }
    }

}
