package controller;

import java.util.ArrayList;
import java.util.List;

import domain.Giocatore;

public interface PartitaObserver {
    // eventi di setup iniziale 
    void onGiocoAvviato();
    void onRichiestaLetturaRegole();
    void onRegoleMostrate();
    void onRichiestaConfigurazioneGiocatori();
    void onGiocatoriInizializzati(List<Giocatore> giocatori, List<String> ordineTurni);

    // Eventi di gioco 
    void onTurnoIniziato(int turnoCorrente, String descrizioneGiocatore);
    void onDadoLanciato(int passi, int numeroCasella, int puntiDado, String nomeDado, String bonusDado);
    void onTabelloneAggiornato(List<String> nomi, List<Integer> id, List<Integer> posizioni, List<Integer> punti, int totaleCaselle, List<Boolean> mappaEventi);
    void onCasellaConoscenzaRaggiunta(int molteplicita);
    void onDomandaRicevuta(String testo, String materia, String difficolta, ArrayList<String> opzioni);
    void onEsitoDomandaElaborato(boolean vittoria, String messaggio, int puntiGuadagnati, char rispostaCorretta, int puntiTotali);
    void onCasellaEventoRaggiunta(String tipoEvento);
    void onRichiestaSfidato(String sfidante, ArrayList<String> avversari);
    void onSfidaIniziata(String sfidante, String sfidato);
    void onNuovoTurnoSfida(String giocatoreCorrente, String testo, String materia, String difficolta, ArrayList<String> opzioni);
    void onEsitoTurnoSfida(boolean corretto, char rispostaCorretta);
    void onSfidaTerminata(String nomeVincitore, String nomePerdente, int puntiInPalio);
    void onMessaggioStato(String messaggio);
    void onGiocoTerminato(String nomeVincitore);
    void onAvanzamentoTurno(int turnoCorrente);
    void onEventoCambioDado(String giocatoreCorrente, String dadoVecchio, String dadoNuovo);
    void onEventoAttivazioneMaledizione(String username);
    void onEventoPenalita(String giocatoreCorrente, int puntiPersi);
    void onEventoModificaPunti(String username, int puntiDaAggiungere, int puntiConoscenza);
}