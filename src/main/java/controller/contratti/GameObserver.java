package controller.contratti;

import java.util.ArrayList;
import java.util.List;

public interface GameObserver {
    // Eventi di gioco 
    void onTurnoIniziato(int turnoCorrente, String descrizioneGiocatore);
    void onDadoLanciato(int passi, int numeroCasella, int puntiDado, String nomeDado, String bonusDado);
    void onTabelloneAggiornato(List<String> nomi, List<Integer> id, List<Integer> posizioni, List<Integer> punti, int totaleCaselle, List<Boolean> mappaEventi);
    void onCasellaConoscenzaRaggiunta(int molteplicita);
    void onDomandaRicevuta(String testo, String materia, String difficolta, ArrayList<String> opzioni);
    void onEsitoDomandaElaborato(boolean vittoria, String messaggio, int puntiGuadagnati, char rispostaCorretta, int puntiTotali);
    void onCasellaEventoRaggiunta(String tipoEvento);
    void onGiocoTerminato(String nomeVincitore);
    void onAvanzamentoRound(int roundCorrente);
}
