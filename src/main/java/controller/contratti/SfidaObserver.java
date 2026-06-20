package controller.contratti;

import java.util.ArrayList;

public interface SfidaObserver {
    void onRichiestaSfidato(String sfidante, ArrayList<String> avversari);
    void onSfidaIniziata(String sfidante, String sfidato);
    void onTurnoSfidaG1(String nomeG, String testo, String materia, String difficolta, ArrayList<String> opzioni);
    void onTurnoSfidaG2(String nomeG, String testo, String materia, String difficolta, ArrayList<String> opzioni);
    void onEsitoTurnoSfidaG1(boolean corretto, char rc);
    void onEsitoTurnoSfidaG2(boolean corretto, char rc);
    void onPareggioSfida(int roundCounter);
    void onSfidaTerminata(String nomeVincitore, String nomePerdente, int puntiInPalio);
}
