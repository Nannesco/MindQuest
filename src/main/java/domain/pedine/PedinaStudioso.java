package domain.pedine;

import domain.Domanda;
import domain.caselle.Casella;
import domain.dadi.Dado;
import domain.regole.RegolePedina;

public class PedinaStudioso extends Pedina {

    private int contatoreRisposteDifficili = 0;

    public PedinaStudioso(Casella casellaCorrente, int puntiConoscenza, Dado dado, RegolePedina regole) {
        super(casellaCorrente, puntiConoscenza, dado, regole);
    }



    @Override
    public String onRispostaCorretta(Domanda domanda) {
        RegolePedina regole = super.getRegolePedina();
        if ("difficile".equalsIgnoreCase(domanda.getDifficolta())) {
            contatoreRisposteDifficili++;
            
            if (contatoreRisposteDifficili == regole.studiosoStreak()) {
                raddoppiaPuntiConoscenza();
                contatoreRisposteDifficili = 0;
                return "Bonus Studioso: " + regole.studiosoStreak() + " risposte difficili di fila! Punti raddoppiati!";
            }
        }
        return "";
    }

    @Override
    public String onRispostaErrata(Domanda domanda) {
        if ("difficile".equalsIgnoreCase(domanda.getDifficolta())) {
            contatoreRisposteDifficili = 0;
        }
        return "";
    }

    @Override
    public String toString() {
        return "Lo Studioso";
    }
    
    
}
