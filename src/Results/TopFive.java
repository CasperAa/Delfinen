package Results;

import Members.Member;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//@Casper

public class TopFive{

    private ArrayList<ConstructorData> resultsArrayList;
    private ArrayList<ConstructorData> seniorCrawl, seniorBreaststroke, seniorButterfly, seniorRygcrawl, juniorCrawl, juniorBreaststroke, juniorButterfly, juniorRygcrawl;


    public void fileReader() throws FileNotFoundException {
        Member.readMembersFromFileAndAddToArray();

        File[] memberFiles = new File("src/Files/membersResults").listFiles();

        resultsArrayList = new ArrayList<>();

        assert memberFiles != null;
        for (File currentFile : memberFiles) {

            Scanner scanCurrentFile = new Scanner(currentFile);

            //Skip metadata line
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
        sortDataBySwimMethod();
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

        ArrayList<Member> allMembers = Member.getMemberList();

        try {
            for (ConstructorData currentDataRow : resultsArrayList) {
                for (Member currentMember : allMembers) {
                    if (currentDataRow.getId().equals(currentMember.getID())) {
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

            Collections.sort(juniorBreaststroke);
            Collections.sort(juniorButterfly);
            Collections.sort(juniorBreaststroke);
            Collections.sort(juniorButterfly);
            Collections.sort(juniorCrawl);
            Collections.sort(juniorRygcrawl);
            Collections.sort(seniorBreaststroke);
            Collections.sort(seniorButterfly);
            Collections.sort(seniorCrawl);
            Collections.sort(seniorRygcrawl);

        } catch (NullPointerException e) {
            System.out.println("something went wrong");
        }
    }

    public void topFiveJuniorBreaststroke(){
        for (int i = 0 ; i < 5 ; i++ ){
            System.out.println( "Tid: " + juniorBreaststroke.get(i).resultTime + "      ID: "+  juniorBreaststroke.get(i).getId() + " Navn: " + Member.getMemberList().get((Integer.parseInt(juniorBreaststroke.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
        }
    }
    public void topFiveJuniorButterfly(){
        for (int i = 0 ; i < 5 ; i++ ){
            System.out.println( "Tid: " + juniorButterfly.get(i).resultTime + "      ID: "+  juniorButterfly.get(i).getId() + " Navn: " + Member.getMemberList().get((Integer.parseInt(juniorButterfly.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
        }
    }
    public void topFiveJuniorCrawl(){
        for (int i = 0 ; i < 5 ; i++ ){
            System.out.println( "Tid: " + juniorCrawl.get(i).resultTime + "      ID: "+  juniorCrawl.get(i).getId() + " Navn: " + Member.getMemberList().get((Integer.parseInt(juniorCrawl.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
        }
    }
    public void topFiveJuniorRygcrawl(){
        for (int i = 0 ; i < 5 ; i++ ){
            System.out.println( "Tid: " + juniorRygcrawl.get(i).resultTime + "      ID: "+  juniorRygcrawl.get(i).getId() + " Navn: " + Member.getMemberList().get((Integer.parseInt(juniorRygcrawl.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
        }
    }
    public void topFiveSeniorBreaststroke(){
        for (int i = 0 ; i < 5 ; i++ ){
            System.out.println( "Tid: " + seniorBreaststroke.get(i).resultTime + "      ID: "+  seniorBreaststroke.get(i).getId() + " Navn: " + Member.getMemberList().get((Integer.parseInt(seniorBreaststroke.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
        }
    }
    public void topFiveSeniorButterfly(){
        for (int i = 0 ; i < 5 ; i++ ){
            System.out.println( "Tid: " + seniorButterfly.get(i).resultTime + "      ID: "+  seniorButterfly.get(i).getId() + " Navn: " + Member.getMemberList().get((Integer.parseInt(seniorButterfly.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
        }
    }
    public void topFiveSeniorCrawl(){
        for (int i = 0 ; i < 5 ; i++ ){
            System.out.println( "Tid: " + seniorCrawl.get(i).resultTime + "      ID: "+  seniorCrawl.get(i).getId() + " Navn: " + Member.getMemberList().get((Integer.parseInt(seniorCrawl.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
        }
    }
    public void topFiveSeniorRygcrawl(){
        for (int i = 0 ; i < 5 ; i++ ){
            System.out.println( "Tid: " + seniorRygcrawl.get(i).resultTime + "      ID: "+  seniorRygcrawl.get(i).getId() + " Navn: " + Member.getMemberList().get((Integer.parseInt(seniorRygcrawl.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
        }
    }

    public static String checkForValue (String text){
        if (text.equals("")) {
            return "N/A";
        } else {
            return text;
        }
    }


}

