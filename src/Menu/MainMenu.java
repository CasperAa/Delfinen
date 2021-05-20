package Menu;
//@Casper

public class MainMenu {

    // Welcome Screen (Is prompted at the start of the program)
    public static void welcomeScreen (){
        System.out.println("Åbner Delfinens administrationssystem \n");
    }

    public static void loginScreen(){
        System.out.println("Vælg rolle!");
        System.out.println("Tryk 1: Formand");
        System.out.println("Tryk 2: Kassere");
        System.out.println("Tryk 3: Træner");
        System.out.println("Tryk 4: Admin");
    }

    // Main menu screen for Admin
    public static void menuScreenAdmin (){
        System.out.println("Tryk 1: Tilføj nyt medlem");
        System.out.println("Tryk 2: Rediger eksisterende medlemmer");
        System.out.println("Tryk 3: Årlig indtjening");
        System.out.println("Tryk 4: Se medlemmer, der i restance");
        System.out.println("Tryk 5: Svømmeresultater");
        System.out.println("Tryk 9: Afslut program");

    }
    // Main menu screen for Manager
    public static void menuScreenManager (){
        System.out.println("Tryk 1: Tilføj nyt medlem");
        System.out.println("Tryk 2: Rediger eksisterende medlemmer");
        System.out.println("Tryk 9: Afslut program");
    }

    // Main menu screen for Cashier
    public static void menuScreenCashier () {
        System.out.println("Tryk 1: Årlig indtjening");
        System.out.println("Tryk 2: Se medlemmer, der i restance");
        System.out.println("Tryk 3: Rediger medlems betalings status");
        System.out.println("Tryk 9: Afslut program");
    }

    // Main result screen for Trainer
    public static void menuScreenTrainer(){
        System.out.println("Tryk 1: Indtast nyt resultat");
        System.out.println("Tryk 2: Se top 5");
        System.out.println("Tryk 9: Afslut program");
    }

    // Error Message for general use
    public static void errorMessage(){
        System.out.println("Forstår dig ikke - Prøv igen");
    }
    }
