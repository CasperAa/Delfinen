package Subscriptions;

import Members.Member;

//@Casper

public class UnpaidSubscriptions {

    public static void paymentOverview (){

        System.out.println("Disse medlemmer er i restance:");

        //Printing a member object if it's hasPayed attribute is false
        for(Member member : Member.getMemberList()){
            if (!member.isHasPayed()){
                System.out.println("\n"+ member.getID() + ": " + member.getName() + " - " + member.getMemberType() + "\n      "
                        + "E-mail: " + member.getEmail() + "  Mobil: " + member.getTelephoneNo());
            }
        }
    }
}
