package Subscriptions;
import Members.Member;
import java.util.Calendar;

//@Casper
public class Revenue {



    public static String yearlyRevenue (){
        //Base values received from client
        final double juniorRate = 1000.00;
        final double seniorRate = 1600.00;
        final double seniorDiscount = 0.25;
        final double passive = 500.00;
        final int sixty = 60;

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        //Counting attributes
        double yearlyIncome = 0.00;
        int juniorMembers = 0;
        int seniorMembers = 0;
        int seniorMembersOverSixty = 0;
        int passiveMembers = 0;
        int totalMembersWhoPays = 0;


        //For loop for all members sorting out people who hasn't paid for the current season
        for (Member member : Member.getMemberList()){
            if(member.isHasPayed()){
                totalMembersWhoPays++;
                //Adding all passive members subscription balance and counting amount of members of that type
                if(member.getMemberStatus().equals("passive")){
                    yearlyIncome += passive;
                    passiveMembers++;

                //Adding all junior members subscription balance and counting amount of members of that type
                } else if (member.getMemberType().equals("junior")){
                    yearlyIncome += juniorRate;
                    juniorMembers++;

                //Adding all senior members subscription balance and counting amount of members of that type
                } else if (member.getMemberType().equals("senior") && currentYear - Integer.parseInt(member.getBirthdate().substring(member.getBirthdate().length()-4)) < sixty){
                    yearlyIncome += seniorRate;
                    seniorMembers++;

                //Adding all senior members with discount subscription balance and counting amount of members of that type
                } else{
                    yearlyIncome += seniorRate - (seniorDiscount * seniorRate);
                    seniorMembersOverSixty++;
                }
            }
        }

        return "Årlig indkomst: "+ yearlyIncome + " kr,- \n"+
                "     Total antal medlemmer     " +totalMembersWhoPays +"\n"+
                "     Antal Juniorer            " +juniorMembers +"\n"+
                "     Antal Seniorer            " +seniorMembers +"\n" +
                "     Antal seniorer over 60    " +seniorMembersOverSixty +"\n"+
                "     Antal passive medlemmer   " +passiveMembers +"\n";
    }
}