package domain;

public class PedinaSperimentatore extends Pedina {

    public PedinaSperimentatore(Casella casellaCorrente, int puntiConoscenza, Dado dado) {
        super(casellaCorrente, puntiConoscenza, dado);
    }


    @Override
    public String onRispostaCorretta(Domanda domanda) {
        if ("difficile".equalsIgnoreCase(domanda.getDifficolta())) {
            aggiungiPuntiConoscenza(Regole.getSperimentatoreBonusDifficile());
            return "Bonus Sperimentatore: Hai indovinato una domanda difficile! +" + Regole.getSperimentatoreBonusDifficile() + " punti conoscenza extra!";
        }
        return "";
    }

    @Override
    public String toString() {
        return "Lo Sperimentatore";
    }

}
