import config.GameLauncher;

public class Main {
     public static void main(String[] args) {
        boolean giocaAncora;
        do{
        giocaAncora = GameLauncher.avviaApplicazione();
        } while(giocaAncora);   
    }
}
