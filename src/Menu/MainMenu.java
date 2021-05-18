package Menu;
//@Casper

public class MainMenu {

    // Welcome Screen (Is prompted at the start of the program)
    public void welcomeScreen (){
        System.out.println("Åbner Delfinens administrationssystem \n");
    }

    public void loginScreen(){
        System.out.println("Vælg rolle!");
        System.out.println("Tryk 1: Formand");
        System.out.println("Tryk 2: Kassere");
        System.out.println("Tryk 3: Træner");
        System.out.println("Tryk 4: Admin");
    }

    // Main menu screen for Admin
    public void menuScreenAdmin (){
        System.out.println("Tryk 1: Tilføj nyt medlem");
        System.out.println("Tryk 2: Rediger eksisterende medlemmer");
        System.out.println("Tryk 3: Se medlemmer, der i restance");
        System.out.println("Tryk 4: Årlig indtjening");
        System.out.println("Tryk 5: Svømmeresultater");
        System.out.println("Tryk 9: Afslut program");

    }

    public void menuScreenManager (){
        System.out.println("Tryk 1: Tilføj nyt medlem");
        System.out.println("Tryk 2: Rediger eksisterende medlemmer");
        System.out.println("Tryk 9: Afslut program");
    }

    // Main menu screen
    public void menuScreenCashier () {
        System.out.println("Tryk 1: Se medlemmer, der i restance");
        System.out.println("Tryk 2: Årlig indtjening");
        System.out.println("Tryk 3: Rediger medlems betalings status");
        System.out.println("Tryk 9: Afslut program");
    }

    // Main result screen
    public void menuScreenTrainer(){
        System.out.println("Tryk 1: Indtast nyt resultat");
        System.out.println("Tryk 2: Se top 5");
        System.out.println("Tryk 9: Afslut program");
    }

    public void errorMessage(){
        System.out.println("Forstår dig ikke - Prøv igen");
    }
}
