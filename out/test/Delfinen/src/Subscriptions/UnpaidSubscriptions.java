package Subscriptions;

import Members.Member;

public class UnpaidSubscriptions {

    public static void paymentOverview (){
        System.out.println("Disse medlemmer er i restance:");

        for(Member member : Member.getMemberList()){
            if (!member.isHasPayed()){
                System.out.println("\n"+ member.getID() + ": " + member.getName() + " - " + member.getMemberType() + "\n      "
                        + "E-mail: " + member.getEmail() + "  Mobil: " + member.getTelephoneNo());
            }
        }
    }

}
