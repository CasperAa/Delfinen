package Results;

public class DataReader extends Result implements Comparable <DataReader>{
        private String id;
        public DataReader(String id, double resultTime, String date, String swimType, String resultType, String competitionName, String placement){
            super(resultTime, date, swimType,resultType, competitionName, placement);
            this.id = id;
        }

    public String getId(){
            return id;
    }

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
