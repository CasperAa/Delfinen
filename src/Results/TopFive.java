package Results;

import Members.Member;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//@Casper

public class TopFive{

    //Creating ArrayLists
    private ArrayList<DataReader> seniorCrawl,seniorBreaststroke, seniorButterfly, seniorRygcrawl, juniorCrawl, juniorBreaststroke,juniorButterfly, juniorRygcrawl;


    //Method for adding objects to arraylist from Files
    public void fileReader(){
        try{

        //File array with all files in pathname
        File[] memberFiles = new File("src/Files/membersResults").listFiles();

        //Temporary ArrayList
        ArrayList<DataReader> tempListGeneral = new ArrayList<>();
        ArrayList<DataReader> tempResultList = new ArrayList<>();
        ArrayList<DataReader> crawlList = new ArrayList<>();
        ArrayList<DataReader> butterflyList = new ArrayList<>();
        ArrayList<DataReader> breaststrokeList = new ArrayList<>();
        ArrayList<DataReader> rygcrawlList = new ArrayList<>();

        //String to avoid errors with equals method
        final String crawl = "Crawl";
        final String butterfly = "Butterfly";
        final String breaststroke = "Brystsvømning";
        final String rygcrawl = "Rygcrawl";

        //Checking if file is empty
        assert memberFiles != null;
        //for loop for each file in package
        for (File currentFile : memberFiles) {

            Scanner scanCurrentFile = new Scanner(currentFile);

            //Skipping metadata line
            scanCurrentFile.nextLine();

            //While-loop for all rows in file
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

                //Using the constructor create a new object and adding the object to the temporary arraylist
                DataReader currentCompetitionResult = new DataReader(id, time, date, method, type, competitionName, placement);
                tempResultList.add(currentCompetitionResult);
            }

            //This arraylist only has Objects from one member
            //Sorting all objects after time, fastest first
            Collections.sort(tempResultList);

            //Dividing objects after swim discipline to a temporary arraylist
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

            //Removing all objects from Arraylist so only one members time results is on the temporary ArrayList
            tempResultList.clear();

            //Getting the top result for each swim discipline and adding that to a general temporary Arraylist with all members best results
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

        //Call method to sort member type and swim discipline
        sortBySwimMethodAndMemberType(tempListGeneral);
    }
        catch(FileNotFoundException e){
            System.out.println("Kan ikke finde fil lokation!");
        }
    }


    public void sortBySwimMethodAndMemberType(ArrayList <DataReader> listWithTopResultForEachMember) {

        seniorBreaststroke = new ArrayList<>();
        seniorCrawl = new ArrayList<>();
        seniorButterfly = new ArrayList<>();
        seniorRygcrawl = new ArrayList<>();
        juniorBreaststroke = new ArrayList<>();
        juniorCrawl = new ArrayList<>();
        juniorButterfly = new ArrayList<>();
        juniorRygcrawl = new ArrayList<>();

        //Getting all members to a new ArrayList
        ArrayList<Member> allMembers = Member.getMemberList();

        try {
            //for loop for all objects in result ArrayList
            for (DataReader currentResult : listWithTopResultForEachMember) {
                //for loop for all Members in membersList
                for (Member currentMember : allMembers) {
                    //Matching currentResult and currentMember with ID
                    if (currentResult.getId().equals(currentMember.getID())) {
                        //Adding currentResult object to it's designated ArrayList
                        switch (currentMember.getMemberType()) {
                            case "senior":
                                switch (currentResult.swimType) {
                                    case "Brystsvømning":
                                        seniorBreaststroke.add(currentResult);
                                        break;
                                    case "Crawl":
                                        seniorCrawl.add(currentResult);
                                        break;
                                    case "Butterfly":
                                        seniorButterfly.add(currentResult);
                                        break;
                                    case "Rygcrawl":
                                        seniorRygcrawl.add(currentResult);
                                        break;
                                }
                                break;
                            case "junior":
                                switch (currentResult.swimType) {
                                    case "Brystsvømning":
                                        juniorBreaststroke.add(currentResult);
                                        break;
                                    case "Crawl":
                                        juniorCrawl.add(currentResult);
                                        break;

                                    case "Butterfly":
                                        juniorButterfly.add(currentResult);
                                        break;

                                    case "Rygcrawl":
                                        juniorRygcrawl.add(currentResult);
                                        break;
                                }
                                break;
                        }
                    }
                }
            }

            //Sorting all ArrayList after result time
            Collections.sort(juniorButterfly);
            Collections.sort(juniorBreaststroke);
            Collections.sort(juniorCrawl);
            Collections.sort(juniorRygcrawl);
            Collections.sort(seniorBreaststroke);
            Collections.sort(seniorButterfly);
            Collections.sort(seniorCrawl);
            Collections.sort(seniorRygcrawl);


        } catch (NullPointerException e) {
            System.out.println("Et medlem har forkert data i sin resultat fil!");
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

