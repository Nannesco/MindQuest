package domain;

/**
 * DTO immutabile restituito da Partita.processaRisposta().
 * Il GameController lo usa per notificare la View senza interrogare
 * molteplici getter separati.
 */
public class RisultatoRisposta {

    public final boolean corretta;
    public final String  bonusMessage;
    public final int puntiGuadagnati;
    public final char rispostaCorretta; // ' ' se la risposta era corretta
    public final int puntiTotali;

    public RisultatoRisposta(boolean corretta, String bonusMessage, int puntiGuadagnati, char rispostaCorretta, int puntiTotali) {
        this.corretta = corretta;
        this.bonusMessage = bonusMessage;
        this.puntiGuadagnati = puntiGuadagnati;
        this.rispostaCorretta = rispostaCorretta;
        this.puntiTotali = puntiTotali;
    }
}
