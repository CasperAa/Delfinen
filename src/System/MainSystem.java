package System;
import Members.Member;
import Members.Trainer;
import Subscriptions.Revenue;
import Subscriptions.UnpaidSubscriptions;
import Results.TopFive;

import java.io.IOException;
import java.util.Scanner;

//@Casper
public class MainSystem {
    public static void main(String[] args){
        //Adding new instance of Top 5 class
        TopFive topFive = new TopFive();
        //Sorting data for top 5 lists
        topFive.fileReader();


        //Adding data to ArrayList containing Members
        Members.Member.readMembersFromFileAndAddToArray();

        //Printing welcome screen and main menu
        Menu.MainMenu.welcomeScreen();
        Menu.MainMenu.loginScreen();


        Scanner userInput = new Scanner(System.in);
        boolean endProgram = false;
        boolean endManager = false;

        while (!endProgram) {
            switch (userInput.nextLine()) {

                case "1": // Manager

                    Menu.MainMenu.menuScreenManager();
                    while(!endManager){
                        switch (userInput.nextLine()){
                            case "1":
                                Member.writeNewMember();//Method for adding members
                                Menu.MainMenu.menuScreenManager();
                                break;
                            case "2":
                                Member.editMemberInfo();//Method for editing existing members
                                Menu.MainMenu.menuScreenManager();
                                break;
                            case "3":
                                Trainer.writeNewTrainer(); //Method for adding trainers
                                Menu.MainMenu.menuScreenManager();
                                break;
                            case "4":
                                Trainer.editTrainerInfo(); //Method for editing existing trainers
                                Menu.MainMenu.menuScreenManager();
                                break;
                            case "5":
                                Trainer.editTrainerTeams(); //Method for editing teams
                                Menu.MainMenu.menuScreenManager();
                                break;
                            case "9":
                                endManager = true;
                                endProgram = true;
                                break;
                            default:
                                Menu.MainMenu.errorMessage();
                                Menu.MainMenu.menuScreenManager();
                        }
                    } break;

                case "2": // Cashier
                    Menu.MainMenu.menuScreenCashier();
                    switch (userInput.nextLine()){
                        case "1":
                            //Method for overview of yearly revenue
                            Revenue.yearlyRevenue();
                            Menu.MainMenu.menuScreenCashier();
                            break;
                        case "2":
                            //Method for overview of members missing payment
                            UnpaidSubscriptions.paymentOverview();
                            Menu.MainMenu.menuScreenCashier();
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
                            endProgram = true;
                            break;
                        default:
                            Menu.MainMenu.errorMessage();
                            Menu.MainMenu.menuScreenCashier();
                    } break;

                case "3": // Trainer
                    Menu.MainMenu.menuScreenTrainer();
                    switch (userInput.nextLine()){
                        case "1":
                            //Method for adding new result
                            Results.Result.addNewCSVFile();
                            break;
                        case "2":
                            Menu.MainMenu.trainerTopFiveScreen1();
                            switch (userInput.nextLine()){
                                case "1":
                                    Menu.MainMenu.trainerTopFiveScreen2Senior();
                                    boolean stay = true;
                                    while (stay) {
                                        switch (userInput.nextLine()) {
                                            case "1":
                                                topFive.topFiveSeniorBreaststroke();
                                                System.out.print("\n");
                                                Menu.MainMenu.trainerTopFiveScreen2Senior();
                                                break;
                                            case "2":
                                                topFive.topFiveSeniorButterfly();
                                                System.out.print("\n");
                                                Menu.MainMenu.trainerTopFiveScreen2Senior();
                                                break;
                                            case "3":
                                                topFive.topFiveSeniorCrawl();
                                                System.out.print("\n");
                                                Menu.MainMenu.trainerTopFiveScreen2Senior();
                                                break;
                                            case "4":
                                                topFive.topFiveSeniorRygcrawl();
                                                System.out.print("\n");
                                                Menu.MainMenu.trainerTopFiveScreen2Senior();
                                                break;
                                            case "9":
                                                Menu.MainMenu.loginScreen();
                                                stay = false;
                                                break;
                                            default:
                                                Menu.MainMenu.errorMessage();
                                                Menu.MainMenu.trainerTopFiveScreen2Senior();
                                        }
                                    }
                                    Menu.MainMenu.loginScreen();
                                    break;

                                case "2":
                                    Menu.MainMenu.trainerTopFiveScreen2Junior();
                                    switch (userInput.nextLine()){
                                        case "1":
                                            topFive.topFiveJuniorBreaststroke();
                                            System.out.print("\n");
                                            break;
                                        case "2":
                                            topFive.topFiveJuniorButterfly();
                                            System.out.print("\n");

                                            break;
                                        case "3":
                                            topFive.topFiveJuniorCrawl();
                                            System.out.print("\n");

                                            break;
                                        case "4":
                                            topFive.topFiveJuniorRygcrawl();
                                            System.out.print("\n");

                                            break;
                                        case "9":
                                            Menu.MainMenu.loginScreen();
                                            break;
                                        default:
                                            Menu.MainMenu.errorMessage();
                                            Menu.MainMenu.trainerTopFiveScreen2Senior();
                                    }
                                    Menu.MainMenu.loginScreen();

                                    break;

                                case "9":
                                    Menu.MainMenu.menuScreenTrainer();
                                    break;

                                default:
                                    Menu.MainMenu.errorMessage();
                                    Menu.MainMenu.trainerTopFiveScreen1();
                            }
                            break;
                        case "9":
                            endProgram = true;
                            break;
                        default:
                            Menu.MainMenu.errorMessage();
                            Menu.MainMenu.menuScreenTrainer();
                    } break;

                case "4": // Admin
                    Menu.MainMenu.menuScreenAdmin();
                    switch (userInput.nextLine()){
                        case "1":
                            //Method for adding members
                            Member.writeNewMember();
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
                            Menu.MainMenu.menuScreenTrainer();
                            break;
                        case "6":
                            //Method for resetting payment status and updating membertype for all members
                            Member.startNewSeason();
                        case "9":
                            Trainer.editTrainerTeams(); //Method for editing teams
                            break;
                        case "10":
                            endProgram = true;
                            break;
                        default:
                            Menu.MainMenu.errorMessage();
                            Menu.MainMenu.menuScreenAdmin();
                    } break;

                case "9": // End Program
                    endProgram = true;
                    break;

                default:
                    Menu.MainMenu.errorMessage();
                    Menu.MainMenu.loginScreen();
            }

        }
    }
}
