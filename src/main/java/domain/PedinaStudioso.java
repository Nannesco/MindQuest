package domain;

public class PedinaStudioso extends Pedina {

    int contatoreRisposteDifficili = 0;

    public PedinaStudioso(Casella casellaCorrente, int puntiConoscenza, Dado dado) {
        super(casellaCorrente, puntiConoscenza, dado);
    }



    @Override
    public String onRispostaCorretta(Domanda domanda) {
        if ("difficile".equalsIgnoreCase(domanda.getDifficolta())) {
            contatoreRisposteDifficili++;
            
            if (contatoreRisposteDifficili == Regole.getStudiosoStreakRichiesta()) {
                raddoppiaPuntiConoscenza();
                contatoreRisposteDifficili = 0;
                return "Bonus Studioso: " + Regole.getStudiosoStreakRichiesta() + " risposte difficili di fila! Punti raddoppiati!";
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
