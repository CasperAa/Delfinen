package System;
import Menu.MainMenu;
import Subscriptions.Revenue;
import Subscriptions.UnpaidSubscriptions;

import java.io.FileNotFoundException;
import java.util.Scanner;

//@Casper
public class MainSystem {
    public static void main(String[] args) throws FileNotFoundException {
        //New instance of mainMenu class
        MainMenu Menu = new MainMenu();

        //Adding data to ArrayList containing Members
        Members.Member.addMembersFromFileToArray();

        //Printing welcome screen and main menu
        Menu.welcomeScreen();
        Menu.loginScreen();


        Scanner userInput = new Scanner(System.in);
        boolean endProgram = false;

        while (!endProgram) {
            switch (userInput.nextLine()) {

                case "1": // Manager
                    Menu.menuScreenManager();
                    switch (userInput.nextLine()){
                        case "1":
                            //Method for adding members
                            break;
                        case "2":
                            //Method for editing existing members
                            break;
                        case "9":
                            endProgram = true;
                            break;
                        default:
                            Menu.errorMessage();
                            Menu.menuScreenManager();
                    } break;

                case "2": // Cashier
                    Menu.menuScreenCashier();
                    switch (userInput.nextLine()){
                        case "1":
                            //Method for overview of yearly revenue
                            Revenue.yearlyRevenue();
                            break;
                        case "2":
                            //Method for overview of members missing payment
                            UnpaidSubscriptions.paymentOverview();
                            break;
                        case "3":
                            //Method for editing members payment status
                            break;
                        case "9":
                            endProgram = true;
                            break;
                        default:
                            Menu.errorMessage();
                            Menu.menuScreenCashier();
                    } break;

                case "3": // Trainer
                    Menu.menuScreenTrainer();
                    switch (userInput.nextLine()){
                        case "1":
                            //Method for adding new result
                            break;
                        case "2":
                            //Method for top 5 overview
                            break;
                        case "9":
                            endProgram = true;
                            break;
                        default:
                            Menu.errorMessage();
                            Menu.menuScreenTrainer();
                    } break;

                case "4": // Admin
                    Menu.menuScreenAdmin();
                    switch (userInput.nextLine()){
                        case "1":
                            //Method for adding members
                            break;
                        case "2":
                            //Method for editing existing members
                            break;
                        case "3":
                            //Method for overview of yearly revenue
                            Revenue.yearlyRevenue();
                            break;
                        case "4":
                            //Method for editing members payment status
                            break;
                        case "5":
                            Menu.menuScreenTrainer();
                            break;
                        case "9":
                            endProgram = true;
                            break;
                        default:
                            Menu.errorMessage();
                            Menu.menuScreenAdmin();
                    } break;

                case "9": // End Program
                    endProgram = true;
                    break;

                default:
                    Menu.errorMessage();
                    Menu.loginScreen();
            }

        }
    }
}
