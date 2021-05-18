package System;
import Menu.MainMenu;

import java.util.Scanner;

//@Casper
public class MainSystem {
    public static void main(String[] args) {
        //New instance of mainMenu class
        MainMenu Menu = new MainMenu();

        //Printing welcome screen and main menu
        Menu.welcomeScreen();
        Menu.loginScreen();


        Scanner userInput = new Scanner(System.in);
        boolean endProgram = false;

        while (!endProgram) {
            switch (userInput.nextLine()) {

                case "1": // Add member

                case "2":
            }

        }
    }
}
