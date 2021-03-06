package Results;

//@Casper


public class DataReader extends Result implements Comparable <DataReader>{

    //Constructor for arraylist to sort after top 5, adding ID so we can compare to MembersList
        private final String id;
        public DataReader(String id, double resultTime, String date, String swimType, String resultType, String competitionName, String placement){
            super(resultTime, date, swimType,resultType, competitionName, placement);
            this.id = id;
        }

    public String getId(){
            return id;
    }


    // Changing compreTo method to compare resultTime, sorting for quickest swim time
    @Override
    public int compareTo(DataReader other) {
        if(this.resultTime > other.getResultTime()){
            return 1;
        }
        else if (this.resultTime < other.getResultTime()){
            return -1;
        }
        else {
            return 0;
        }
    }

}
