package Results;

public class ConstructorData extends SuperResult{
        String id;
        public ConstructorData (String id, double resultTime, String date, String swimType, String resultType, String competitionName, String placement){
            super(resultTime, date, swimType,resultType, competitionName, placement);
            this.id = id;
        }
}
