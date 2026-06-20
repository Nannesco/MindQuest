package domain.pedine;

import domain.Domanda;
import domain.caselle.Casella;
import domain.dadi.Dado;
import domain.regole.RegolePedina;

public class PedinaSperimentatore extends Pedina {

    public PedinaSperimentatore(Casella casellaCorrente, int puntiConoscenza, Dado dado, RegolePedina regole) {
        super(casellaCorrente, puntiConoscenza, dado, regole);
    }


    @Override
    public String onRispostaCorretta(Domanda domanda) {
        RegolePedina regole = super.getRegolePedina();
        if ("difficile".equalsIgnoreCase(domanda.getDifficolta())) {
            aggiungiPuntiConoscenza(regole.sperimentatoreBonus());
            return "Bonus Sperimentatore: Hai indovinato una domanda difficile! +" + regole.sperimentatoreBonus() + " punti conoscenza extra!";
        }
        return "";
    }

    @Override
    public String toString() {
        return "Lo Sperimentatore";
    }

}
