package Results;


public abstract class Results {

    String resultTime;
    String date;
    String swimType;
    String resultType;

    public Results (String resultTime, String date, String swimType, String resultType){
        this.resultTime = resultTime;
        this.date = date;
        this.swimType = swimType;
        this.resultType = resultType;
    }

}
