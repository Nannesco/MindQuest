package domain;

public class PedinaSaggio extends Pedina {

    public PedinaSaggio(Casella casellaCorrente, int puntiConoscenza, Dado dado) {
        super(casellaCorrente, puntiConoscenza, dado);
    }


    @Override
    public String onRispostaCorretta(Domanda domanda) {
        aggiungiPuntiConoscenza(Regole.getSaggioBonusExtra());
        return "Bonus Saggio: +" + Regole.getSaggioBonusExtra() + " punti conoscenza extra per la risposta esatta!";
    }

    @Override
    public String toString() {
        return "Il Saggio";
    }

}
