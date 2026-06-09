package domain;

public class PedinaFortunato extends Pedina {

    public PedinaFortunato(Casella casellaCorrente, int puntiConoscenza, Dado dado) {
        super(casellaCorrente, puntiConoscenza, dado);
    }



    @Override
    public String onDadoLanciato(int passi) {

        if (passi == Regole.getFortunatoTiroTrigger()) {
            aggiungiPuntiConoscenza(Regole.getFortunatoPuntiBonus());
            return "Bonus Fortunato: Hai fatto " + Regole.getFortunatoTiroTrigger() + "! Guadagni " + Regole.getFortunatoPuntiBonus() + " punti conoscenza extra!";
        }
        return "";
    }

    @Override
    public String toString() {
        return "Il Fortunato";
    }

}
