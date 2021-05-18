package Subscriptions;
import Members.Members;
import java.util.Calendar;
//@Casper
public class Revenue {
    private final double junior = 1000.00;
    private final double senior = 1600.00;
    private final double seniorDiscount = 0.25;
    private final double passive = 500.00;
    private final int sixty = 60;
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);


    public void yearlyRevenue (){
        double yearlyIncome = 0.00;
        int juniorMembers = 0;
        int seniorMembers = 0;
        int seniorMembersOverSixty = 0;
        int passiveMembers = 0;

        for (Members member : memberList){
            if(member.getMemberStatus.equals("passive")){
                yearlyIncome += passive;
                passiveMembers++;
            } else if (member.getMembertype.equals("junior")){
                yearlyIncome += junior;
                juniorMembers++;
            } else if (member.getMemberType.equals("senior") && currentYear - member.getBirthday.substring(member.getBirthday.length()-4) < sixty){
                yearlyIncome += senior;
                seniorMembers++;
            } else{
                yearlyIncome += senior - (seniorDiscount * senior);
                seniorMembersOverSixty++;
            }
        }
        System.out.println("Årlig indkomst: "+ yearlyIncome + " kr,-");
        System.out.println("     Antal Juniorer         : " +juniorMembers);
        System.out.println("     Antal Seniorer         : " +seniorMembers);
        System.out.println("     Antal seniorer over 60 : " +seniorMembersOverSixty);
        System.out.println("     Antal passive medlemmer: " +passiveMembers);

    }
}
