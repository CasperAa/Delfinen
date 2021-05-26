package System;
import Members.Member;
import Members.Trainer;
import Menu.MainMenu;
import Subscriptions.Revenue;
import Subscriptions.UnpaidSubscriptions;
import Results.TopFive;
import java.util.Scanner;

//@Casper
public class MainSystem {
    public static void main(String[] args){

        //Adding data to ArrayList containing Members
        Members.Member.readMembersFromFileAndAddToArray();

        //Printing welcome screen and main menu
        MainMenu.welcomeScreen();
        MainMenu.loginScreen();
        MainSystem mainSystem = new MainSystem();


        Scanner userInput = new Scanner(System.in);
        boolean endProgram = false;

        while (!endProgram) {
            switch (userInput.nextLine()) {

                case "1": // Manager

                    MainMenu.menuScreenManager();
                    boolean endManager = false;
                    while(!endManager){
                        switch (userInput.nextLine()){
                            case "1":
                                Member.addNewMember();//Method for adding members
                                MainMenu.menuScreenManager();
                                break;
                            case "2":
                                Member.editMemberInfo();//Method for editing existing members
                                MainMenu.menuScreenManager();
                                break;
                            case "3":
                                Trainer.writeNewTrainer(); //Method for adding trainers
                                MainMenu.menuScreenManager();
                                break;
                            case "4":
                                Trainer.editTrainerInfo(); //Method for editing existing trainers
                                MainMenu.menuScreenManager();
                                break;
                            case "5":
                                Trainer.editTrainerTeam(); //Method for editing teams
                                MainMenu.menuScreenManager();
                                break;
                            case "9":
                                endManager = true;
                                MainMenu.loginScreen();
                                break;
                            default:
                                MainMenu.errorMessage();
                                MainMenu.menuScreenManager();
                        }
                    } break;

                case "2": // Cashier
                    MainMenu.menuScreenCashier();
                    boolean cashierMainMenu = true;
                    while (cashierMainMenu) {
                        switch (userInput.nextLine()) {
                            case "1":
                                //Method for overview of yearly revenue
                                System.out.println(Revenue.yearlyRevenue());
                                MainMenu.menuScreenCashier();
                                break;
                            case "2":
                                //Method for overview of members missing payment
                                UnpaidSubscriptions.paymentOverview();
                                MainMenu.menuScreenCashier();
                                break;
                            case "3":
                                //Method for editing members payment status
                                Member.editPaymentStatus();
                                break;

                            case "4":
                                //Method for resetting payment status and updating membertype for all members
                                Member.startNewSeason();
                                break;
                            case "9":
                                cashierMainMenu = false;
                                MainMenu.loginScreen();
                                break;
                            default:
                                MainMenu.errorMessage();
                                MainMenu.menuScreenCashier();
                        }
                    }
                    break;

                case "3": // Trainer
                    mainSystem.trainerMenu(userInput);
                    break;

                case "4": // Admin
                    MainMenu.menuScreenAdmin();
                    boolean adminMainMenu = true;
                    while (adminMainMenu) {
                        switch (userInput.nextLine()) {
                            case "1":
                                //Method for adding members
                                Member.addNewMember();
                                break;
                            case "2":
                                //Method for editing existing members
                                Member.editMemberInfo();
                                break;
                            case "3":
                                //Method for overview of yearly revenue
                                Revenue.yearlyRevenue();
                                break;
                            case "4":
                                //Method for editing members payment status
                                Member.editPaymentStatus();
                                break;
                            case "5":
                                mainSystem.trainerMenu(userInput);
                                break;
                            case "6":
                                //Method for resetting payment status and updating membertype for all members
                                Member.startNewSeason();
                            case "9":
                                Trainer.editTrainerTeam(); //Method for editing teams
                                break;
                            case "10":
                                adminMainMenu = false;
                                MainMenu.loginScreen();
                                break;
                            default:
                                MainMenu.errorMessage();
                                MainMenu.menuScreenAdmin();
                        }
                    }
                    break;

                case "9": // End Program
                    endProgram = true;
                    break;

                default:
                    MainMenu.errorMessage();
                    MainMenu.loginScreen();
            }

        }
    }
    public void trainerMenu (Scanner userInput){

        //Adding data to ArrayList containing Members
        Members.Member.readMembersFromFileAndAddToArray();
        TopFive topFive = new TopFive();
        topFive.fileReader();

        MainMenu.menuScreenTrainer();
        boolean stayTrainerMenu = true;
        while (stayTrainerMenu) {
            switch (userInput.nextLine()) {
                case "1":
                    //Method for adding new result
                    Results.Result.addNewCSVFile();
                    break;
                case "2":
                    MainMenu.trainerTopFiveScreen1();
                    boolean stayMemberTypeSelectionMenu = true;
                    while (stayMemberTypeSelectionMenu) {
                        switch (userInput.nextLine()) {
                            case "1":
                                MainMenu.trainerTopFiveScreen2Senior();
                                boolean stayTopFiveSeniorMenu = true;
                                while (stayTopFiveSeniorMenu) {
                                    switch (userInput.nextLine()) {

                                        case "1":
                                            topFive.topFiveSeniorBreaststroke();
                                            System.out.print("\n");
                                            MainMenu.trainerTopFiveScreen2Senior();
                                            break;

                                        case "2":
                                            topFive.topFiveSeniorButterfly();
                                            System.out.print("\n");
                                            MainMenu.trainerTopFiveScreen2Senior();
                                            break;

                                        case "3":
                                            topFive.topFiveSeniorCrawl();
                                            System.out.print("\n");
                                            MainMenu.trainerTopFiveScreen2Senior();
                                            break;

                                        case "4":
                                            topFive.topFiveSeniorRygcrawl();
                                            System.out.print("\n");
                                            MainMenu.trainerTopFiveScreen2Senior();
                                            break;

                                        case "9":
                                            MainMenu.trainerTopFiveScreen1();
                                            stayTopFiveSeniorMenu = false;
                                            break;

                                        default:
                                            MainMenu.errorMessage();
                                    }
                                }
                                break;

                            case "2":
                                MainMenu.trainerTopFiveScreen2Junior();
                                boolean stayTopFiveJuniorMenu = true;
                                while (stayTopFiveJuniorMenu) {
                                    switch (userInput.nextLine()) {

                                        case "1":
                                            topFive.topFiveJuniorBreaststroke();
                                            System.out.print("\n");
                                            MainMenu.trainerTopFiveScreen2Junior();
                                            break;

                                        case "2":
                                            topFive.topFiveJuniorButterfly();
                                            System.out.print("\n");
                                            MainMenu.trainerTopFiveScreen2Junior();
                                            break;

                                        case "3":
                                            topFive.topFiveJuniorCrawl();
                                            System.out.print("\n");
                                            MainMenu.trainerTopFiveScreen2Junior();
                                            break;

                                        case "4":
                                            topFive.topFiveJuniorRygcrawl();
                                            System.out.print("\n");
                                            MainMenu.trainerTopFiveScreen2Junior();
                                            break;

                                        case "9":
                                            MainMenu.trainerTopFiveScreen1();
                                            stayTopFiveJuniorMenu = false;
                                            break;

                                        default:
                                            MainMenu.errorMessage();
                                    }
                                }
                                break;

                            case "9":
                                MainMenu.menuScreenTrainer();
                                stayMemberTypeSelectionMenu = false;
                                break;
                            default:
                                MainMenu.errorMessage();
                                MainMenu.trainerTopFiveScreen1();
                        }
                    }
                    break;

                case "9":
                    stayTrainerMenu = false;
                    MainMenu.loginScreen();
                    break;

                default:
                    MainMenu.errorMessage();
                    MainMenu.menuScreenTrainer();
            }

        }
    }
}
