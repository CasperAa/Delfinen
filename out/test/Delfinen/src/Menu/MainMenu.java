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
        System.out.println("Tryk 2: Kasserer");
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
        System.out.println("Tryk 6: Start ny sæson");
        System.out.println("Tryk 7: Tilføj ny træner");
        System.out.println("Tryk 8: Rediger eksisterende træner");
        System.out.println("Tryk 9: Rediger hold");
        System.out.println("Tryk 10: Afslut program");
    }

    // Main menu screen for Manager
    public static void menuScreenManager (){
        System.out.println("Tryk 1: Tilføj nyt medlem");
        System.out.println("Tryk 2: Rediger eksisterende medlemmer");
        System.out.println("Tryk 3: Tilføj ny træner");
        System.out.println("Tryk 4: Rediger eksisterende træner");
        System.out.println("Tryk 5: Rediger hold");
        System.out.println("Tryk 9: Afslut program");
    }

    // Main menu screen for Cashier
    public static void menuScreenCashier () {
        System.out.println("Tryk 1: Årlig indtjening");
        System.out.println("Tryk 2: Se medlemmer, der i restance");
        System.out.println("Tryk 3: Rediger medlems betalingsstatus");
        System.out.println("Tryk 4: Start ny sæson");
        System.out.println("Tryk 9: Afslut program");
    }

    // Main result screen for Trainer
    public static void menuScreenTrainer(){
        System.out.println("Tryk 1: Indtast nyt resultat");
        System.out.println("Tryk 2: Se top 5");
        System.out.println("Tryk 9: Afslut program");
    }

    public static void trainerTopFiveScreen1(){
        System.out.println("Top 5 - Vælg målgruppe \n");
        System.out.println("Tryk 1: Senior");
        System.out.println("Tryk 2: Junior");
        System.out.println("Tryk 9: Tilbage");

    }

    public static void trainerTopFiveScreen2Junior(){
        System.out.println("Top 5 Junior - Vælg Svømmedisciplin  \n");
        System.out.println("Tryk 1: Brystsvømning");
        System.out.println("Tryk 2: Butterfly");
        System.out.println("Tryk 3: Crawl");
        System.out.println("Tryk 4: Rygcrawl");
        System.out.println("Tryk 9: Tilbage");

    }

    public static void trainerTopFiveScreen2Senior(){
        System.out.println("Top 5 Senior- Vælg Svømmedisciplin \n");
        System.out.println("Tryk 1: Brystsvømning");
        System.out.println("Tryk 2: Butterfly");
        System.out.println("Tryk 3: Crawl");
        System.out.println("Tryk 4: Rygcrawl");
        System.out.println("Tryk 9: Tilbage");

    }

    // Error Message for general use
    public static void errorMessage(){
        System.out.println("Forstår dig ikke - Prøv igen");
    }
    }
