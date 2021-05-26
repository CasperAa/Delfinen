package Results;

import Members.Member;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//@Casper

public class TopFive{

    private ArrayList<DataReader> seniorCrawl;
    private ArrayList<DataReader> seniorBreaststroke;
    private ArrayList<DataReader> seniorButterfly;
    private ArrayList<DataReader> seniorRygcrawl;
    private ArrayList<DataReader> juniorCrawl;
    private ArrayList<DataReader> juniorBreaststroke;
    private ArrayList<DataReader> juniorButterfly;
    private ArrayList<DataReader> juniorRygcrawl;

    public static void main(String[] args) {
        TopFive topFive = new TopFive();
        topFive.fileReader();

        topFive.topFiveSeniorRygcrawl();
    }

    public void fileReader(){

        Member.readMembersFromFileAndAddToArray();

        try{

            File[] memberFiles = new File("src/Files/membersResults").listFiles();

        ArrayList<DataReader> tempListGeneral = new ArrayList<>();
        ArrayList<DataReader> tempResultList = new ArrayList<>();

        ArrayList<DataReader> crawlList = new ArrayList<>();
        ArrayList<DataReader> butterflyList = new ArrayList<>();
        ArrayList<DataReader> breaststrokeList = new ArrayList<>();
        ArrayList<DataReader> rygcrawlList = new ArrayList<>();

        final String crawl = "Crawl";
        final String butterfly = "Butterfly";
        final String breaststroke = "Brystsvømning";
        final String rygcrawl = "Rygcrawl";

        assert memberFiles != null;
        for (File currentFile : memberFiles) {


            Scanner scanCurrentFile = new Scanner(currentFile);

            //Skips metadata line
            scanCurrentFile.nextLine();

            //While-loop til file doesn't have a next row
            while (scanCurrentFile.hasNext()) {

                //A string which has all contents of the current row
                String currentRow = scanCurrentFile.nextLine();

                //Splitting the string into an String Array
                String[] lineAsArray = currentRow.split(";");

                //Giving each index in the array a attribute and storing the value within
                String id = currentFile.getName();
                String type = lineAsArray[0].trim();
                String date = lineAsArray[1].trim();
                double time = Double.parseDouble(lineAsArray[2].trim());
                String method = lineAsArray[3].trim();
                String competitionName = checkForValue(lineAsArray[4].trim());
                String placement = lineAsArray[5].trim();

                //Using the constructor create a new object and adding the object to the arraylist
                DataReader currentCompetitionResult = new DataReader(id, time, date, method, type, competitionName, placement);
                tempResultList.add(currentCompetitionResult);
            }

            Collections.sort(tempResultList);

            for (DataReader dataReader : tempResultList){
                switch (dataReader.swimType) {
                    case rygcrawl:
                        rygcrawlList.add(dataReader);
                        break;
                    case breaststroke:
                        breaststrokeList.add(dataReader);
                        break;
                    case crawl:
                        crawlList.add(dataReader);
                        break;
                    case butterfly:
                        butterflyList.add(dataReader);
                        break;
                }
            }

            tempResultList.clear();

            if(!rygcrawlList.isEmpty()) {
                tempListGeneral.add(rygcrawlList.get(0));
                rygcrawlList.clear();
            }
            if(!breaststrokeList.isEmpty()) {
                tempListGeneral.add(breaststrokeList.get(0));
                breaststrokeList.clear();
            }
            if(!crawlList.isEmpty()) {
                tempListGeneral.add(crawlList.get(0));
                crawlList.clear();
            }
            if(!butterflyList.isEmpty()) {
                tempListGeneral.add(butterflyList.get(0));
                butterflyList.clear();
            }
        }

        sortDataBySwimMethod(tempListGeneral);
    }
        catch(FileNotFoundException e){
            System.out.println("Kan ikke finde fil lokation!");
        }
    }


    public void sortDataBySwimMethod(ArrayList <DataReader> listWithTopResultForEachMember) {


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
            for (DataReader currentDataRow : listWithTopResultForEachMember) {
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


            Collections.sort(juniorButterfly);
            Collections.sort(juniorBreaststroke);
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
        System.out.println("Top 5 - Junior Brystsvømning");
        for (int i = 0 ; i < 5 ; i++ ) {
            if (juniorBreaststroke.get(i).resultType.equals("Træning")) {
                System.out.println(i+1 + ": " + juniorBreaststroke.get(i).swimType + "     Tid: " + juniorBreaststroke.get(i).resultTime + " - " + juniorBreaststroke.get(i).resultType + "          ID: " + juniorBreaststroke.get(i).getId() + " - Navn: " + Member.getMemberList().get((Integer.parseInt(juniorBreaststroke.get(i).getId().replaceFirst("^0+(?!$)", ""))) - 1).getName());
            } else {
                System.out.println(i+1 + ": " + juniorBreaststroke.get(i).swimType + "     Tid: " + juniorBreaststroke.get(i).resultTime + " - " + juniorBreaststroke.get(i).resultType + "      ID: " + juniorBreaststroke.get(i).getId() + " - Navn: " + Member.getMemberList().get((Integer.parseInt(juniorBreaststroke.get(i).getId().replaceFirst("^0+(?!$)", ""))) - 1).getName());
            }
        }
    }

    public void topFiveJuniorButterfly(){
        System.out.println("Top 5 - Junior Butterfly");
        for (int i = 0 ; i < 5 ; i++ ){
            if(juniorButterfly.get(i).resultType.equals("Træning")){
                System.out.println(i+1 +": " + juniorButterfly.get(i).swimType +  "     Tid: " + juniorButterfly.get(i).resultTime + " - " + juniorButterfly.get(i).resultType +"          ID: "+  juniorButterfly.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(juniorButterfly.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            } else {
                System.out.println(i+1 +": " +juniorButterfly.get(i).swimType +   "     Tid: " + juniorButterfly.get(i).resultTime + " - " + juniorButterfly.get(i).resultType +"      ID: "+  juniorButterfly.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(juniorButterfly.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            }
        }
    }
    public void topFiveJuniorCrawl(){
        System.out.println("Top 5 - Junior Crawl");
        for (int i = 0 ; i < 5 ; i++ ){
            if(juniorCrawl.get(i).resultType.equals("Træning")){
                System.out.println(i+1 +": " + juniorCrawl.get(i).swimType +  "     Tid: " + juniorCrawl.get(i).resultTime + " - " + juniorCrawl.get(i).resultType +"          ID: "+  juniorCrawl.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(juniorCrawl.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            } else {
                System.out.println(i+1 +": " +juniorCrawl.get(i).swimType +   "     Tid: " + juniorCrawl.get(i).resultTime + " - " + juniorCrawl.get(i).resultType +"      ID: "+  juniorCrawl.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(juniorCrawl.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            }
        }
    }
    public void topFiveJuniorRygcrawl(){
        System.out.println("Top 5 - Junior Rygcrawl");
        for (int i = 0 ; i < 5 ; i++ ){
            if(juniorRygcrawl.get(i).resultType.equals("Træning")){
                System.out.println(i+1 +": " + juniorRygcrawl.get(i).swimType +  "     Tid: " + juniorRygcrawl.get(i).resultTime + " - " + juniorRygcrawl.get(i).resultType +"          ID: "+  juniorRygcrawl.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(juniorRygcrawl.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            } else {
                System.out.println(i+1 +": " +juniorRygcrawl.get(i).swimType +   "     Tid: " + juniorRygcrawl.get(i).resultTime + " - " + juniorRygcrawl.get(i).resultType +"      ID: "+  juniorRygcrawl.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(juniorRygcrawl.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            }
        }
    }
    public void topFiveSeniorBreaststroke(){
        System.out.println("Top 5 - Senior Brystsvømning");
        for (int i = 0 ; i < 5 ; i++ ){
            if(seniorBreaststroke.get(i).resultType.equals("Træning")){
                System.out.println(i+1 +": " + seniorBreaststroke.get(i).swimType +  "     Tid: " + seniorBreaststroke.get(i).resultTime + " - " + seniorBreaststroke.get(i).resultType +"          ID: "+  seniorBreaststroke.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(seniorBreaststroke.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            } else {
                System.out.println(i+1 +": " +seniorBreaststroke.get(i).swimType +   "     Tid: " + seniorBreaststroke.get(i).resultTime + " - " + seniorBreaststroke.get(i).resultType +"      ID: "+  seniorBreaststroke.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(seniorBreaststroke.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            }
        }
    }
    public void topFiveSeniorButterfly(){
        System.out.println("Top 5 - Senior Butterfly");
        for (int i = 0 ; i < 5 ; i++ ){
            if(seniorButterfly.get(i).resultType.equals("Træning")){
                System.out.println(i+1 +": " + seniorButterfly.get(i).swimType +  "     Tid: " + seniorButterfly.get(i).resultTime + " - " + seniorButterfly.get(i).resultType +"          ID: "+  seniorButterfly.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(seniorButterfly.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            } else {
                System.out.println(i+1 +": " +seniorButterfly.get(i).swimType +   "     Tid: " + seniorButterfly.get(i).resultTime + " - " + seniorButterfly.get(i).resultType +"      ID: "+  seniorButterfly.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(seniorButterfly.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            }
        }
    }
    public void topFiveSeniorCrawl(){
        System.out.println("Top 5 - Senior Crawl");
        for (int i = 0 ; i < 5 ; i++ ){
            if(seniorCrawl.get(i).resultType.equals("Træning")){
                System.out.println(i+1 +": " + seniorCrawl.get(i).swimType +  "     Tid: " + seniorCrawl.get(i).resultTime + " - " + seniorCrawl.get(i).resultType +"          ID: "+  seniorCrawl.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(seniorCrawl.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            } else {
                System.out.println(i+1 +": " +seniorCrawl.get(i).swimType +   "     Tid: " + seniorCrawl.get(i).resultTime + " - " + seniorCrawl.get(i).resultType +"      ID: "+  seniorCrawl.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(seniorCrawl.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            }
        }
    }
    public void topFiveSeniorRygcrawl(){
        System.out.println("Top 5 - Senior Rygcrawl");
        for (int i = 0 ; i < 5 ; i++ ){
            if(seniorRygcrawl.get(i).resultType.equals("Træning")){
                System.out.println(i+1 +": " + seniorRygcrawl.get(i).swimType +  "     Tid: " + seniorRygcrawl.get(i).resultTime + " - " + seniorRygcrawl.get(i).resultType +"          ID: "+  seniorRygcrawl.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(seniorRygcrawl.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            } else {
                System.out.println(i+1 +": " +seniorRygcrawl.get(i).swimType +   "     Tid: " + seniorRygcrawl.get(i).resultTime + " - " + seniorRygcrawl.get(i).resultType +"      ID: "+  seniorRygcrawl.get(i).getId() + " - " + Member.getMemberList().get((Integer.parseInt(seniorRygcrawl.get(i).getId().replaceFirst("^0+(?!$)", "")))-1).getName() );
            }
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

