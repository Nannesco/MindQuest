package domain.pedine;

import domain.Domanda;
import domain.caselle.Casella;
import domain.dadi.Dado;
import domain.regole.RegolePedina;

public class PedinaSaggio extends Pedina {

    public PedinaSaggio(Casella casellaCorrente, int puntiConoscenza, Dado dado, RegolePedina regole) {
        super(casellaCorrente, puntiConoscenza, dado, regole);
    }


    @Override
    public String onRispostaCorretta(Domanda domanda) {
        RegolePedina regole = super.getRegolePedina();
        aggiungiPuntiConoscenza(regole.saggioBonusExtra());
        return "Bonus Saggio: +" + regole.saggioBonusExtra() + " punti conoscenza extra per la risposta esatta!";
    }

    @Override
    public String toString() {
        return "Il Saggio";
    }

}
