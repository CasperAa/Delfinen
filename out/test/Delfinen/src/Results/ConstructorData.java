package Results;

public class ConstructorData extends SuperResult implements Comparable <ConstructorData>{
        private String id;
        public ConstructorData (String id, double resultTime, String date, String swimType, String resultType, String competitionName, String placement){
            super(resultTime, date, swimType,resultType, competitionName, placement);
            this.id = id;
        }

    public String getId(){
            return id;
    }

    @Override
    public int compareTo(ConstructorData other) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConstructorData) {
            return ((ConstructorData) obj).id == id;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Integer.parseInt(this.id);
    }
}
