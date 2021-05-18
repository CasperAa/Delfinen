package Subscriptions;
import Members.Members;

public class Revenue {
    double junior = 1000.00;
    double senior = 1600.00;
    double sixtyDiscount = 0.25;
    double passive = 500.00;
    double sixty = 60.00;



    public long yearlyRevenue (){
        double yearlyIncome = 0.00;

        for (Members member : memberList){
            if (Members.getMembertype.equals("Junior")){
                yearlyIncome + junior;
            } else if ( Members.getMemberType.equals("Senoir") && )

        }


        return yearlyIncome;
    }



}
