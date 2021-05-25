package Subscriptions;
import Members.Member;
import java.util.Calendar;

//@Casper
public class Revenue {



    public static void yearlyRevenue (){
        final double juniorRate = 1000.00;
        final double seniorRate = 1600.00;
        final double seniorDiscount = 0.25;
        final double passive = 500.00;
        final int sixty = 60;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        double yearlyIncome = 0.00;
        int juniorMembers = 0;
        int seniorMembers = 0;
        int seniorMembersOverSixty = 0;
        int passiveMembers = 0;

        for (Member member : Member.getMemberList()){
            if(member.getMemberStatus().equals("passive")){
                yearlyIncome += passive;
                passiveMembers++;
            } else if (member.getMemberType().equals("junior")){
                yearlyIncome += juniorRate;
                juniorMembers++;
            } else if (member.getMemberType().equals("senior") && currentYear - Integer.parseInt(member.getBirthdate().substring(member.getBirthdate().length()-4)) < sixty){
                yearlyIncome += seniorRate;
                seniorMembers++;
            } else{
                yearlyIncome += seniorRate - (seniorDiscount * seniorRate);
                seniorMembersOverSixty++;
            }
        }

        System.out.println("Årlig indkomst: "+ yearlyIncome + " kr,-");
        System.out.println("     Antal Juniorer            " +juniorMembers);
        System.out.println("     Antal Seniorer            " +seniorMembers);
        System.out.println("     Antal seniorer over 60    " +seniorMembersOverSixty);
        System.out.println("     Antal passive medlemmer   " +passiveMembers);

    }
}