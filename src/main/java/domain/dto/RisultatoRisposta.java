package domain.dto;

public record RisultatoRisposta(
    boolean corretta, 
    String bonusMessage, 
    int puntiGuadagnati, 
    char rispostaCorretta, 
    int puntiTotali) {}
