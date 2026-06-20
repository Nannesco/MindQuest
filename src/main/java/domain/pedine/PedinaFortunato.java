package domain.pedine;

import domain.caselle.Casella;
import domain.dadi.Dado;
import domain.regole.RegolePedina;

public class PedinaFortunato extends Pedina {

    public PedinaFortunato(Casella casellaCorrente, int puntiConoscenza, Dado dado, RegolePedina regole) {
        super(casellaCorrente, puntiConoscenza, dado, regole);
    }



    @Override
    public String onDadoLanciato(int passi) {
        RegolePedina regole = super.getRegolePedina();
        if (passi == regole.fortunatoTrigger()) {
            aggiungiPuntiConoscenza(regole.fortunatoBonus());
            return "Bonus Fortunato: Hai fatto " + regole.fortunatoTrigger() + "! Guadagni " + regole.fortunatoBonus() + " punti conoscenza extra!";
        }
        return "";
    }

    @Override
    public String toString() {
        return "Il Fortunato";
    }

}
