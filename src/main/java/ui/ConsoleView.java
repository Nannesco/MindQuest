package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.Giocatore;
import controller.GiocoListener;
import controller.PartitaObserver;

public class ConsoleView implements PartitaObserver {
    
    private final Scanner lettore;
    private GiocoListener listener;

    public ConsoleView() {
        this.lettore = new Scanner(System.in);

        
    }

    public void setListener(GiocoListener listener) {
        this.listener = listener;
    }

    private String leggiInput() {
        return lettore.nextLine().trim().toUpperCase();
    }


    @Override
    public void onGiocoAvviato() {
        System.out.println(Info.PRESENTAZIONE_GIOCO);
        leggiInput();
        if (listener != null) listener.onInizioPremuto();
    }

    @Override
    public void onRichiestaLetturaRegole() {
        System.out.print("\nVuoi leggere le regole del gioco? (S/N): ");
        String risposta = leggiInput();
        if (listener != null) listener.onSceltaRegoleEffettuata(risposta.equalsIgnoreCase("S"));
    }

    @Override
    public void onRegoleMostrate(String testoRegole) {
        System.out.println(testoRegole);
        System.out.println("\nPremere INVIO per proseguire alla creazione della partita...");
        leggiInput();

    }

    @Override
    public void onRichiestaConfigurazioneGiocatori() {
        int numeroGiocatori = 0;
        do {
            System.out.print("\nQuanti giocatori siete? (Inserisci un numero da 2 a 4): ");
            try {
                numeroGiocatori = Integer.parseInt(leggiInput());
                if (numeroGiocatori >= 2 && numeroGiocatori <= 4) break;
            } catch (NumberFormatException e) {}
            System.out.println("Numero non valido! Inserisci un numero tra 2 e 4.");
        } while (true);

        ArrayList<String> nomiGiocatori = new ArrayList<>();
        for (int i = 1; i <= numeroGiocatori; i++) {
            System.out.print("\nInserisci il nome del giocatore " + i + ": ");
            nomiGiocatori.add(leggiInput());
        }

        if (listener != null) listener.onAnagraficaGiocatoriInserita(nomiGiocatori);
    }

    @Override
    public void onGiocatoriInizializzati(List<Giocatore> giocatori, List<String> ordineTurni) {
        System.out.println("\n--- ASSEGNAZIONE PEDINE ---");
        for (Giocatore g : giocatori) {
            System.out.println(g.getUsername() + ", ti è stata assegnata la pedina " + g.getPedina() + "!");
        }
        
        System.out.println("\nL'ordine dei giocatori è stato stabilito casualmente:");
        for (int i = 0; i < ordineTurni.size(); i++) {
            System.out.println((i + 1) + ". " + ordineTurni.get(i));
        }
    }


    public void onAvanzamentoTurno(int turnoCorrente) {
        System.out.println("\n--- INIZIO TURNO " + turnoCorrente + " ---");
    }
    
    @Override
    public void onTurnoIniziato(int turnoCorrente, String descrizioneGiocatore) {
        System.out.println("\nÈ il turno di " + descrizioneGiocatore + "!");
        System.out.print("Premi INVIO per lanciare il dado... ");
        leggiInput();
        if (listener != null) listener.onLancioDadoRichiesto();
    }

    @Override
    public void onDadoLanciato(int passi, int numeroCasella, int puntiDado, String nomeDado, String bonusDado) {
        if (puntiDado != 0){
            System.out.println("Stai usando il " + nomeDado + "!");
            if(puntiDado > 0) System.out.println("Hai guadagnato " + puntiDado + " punto.");
            if(puntiDado < 0) System.out.println("Hai perso " + Math.abs(puntiDado) + " punto.");
        }
        System.out.println("\nHai fatto " + passi + "!");
        if(bonusDado != null && !bonusDado.isEmpty()) System.out.println(bonusDado);
        System.out.println("\nLa tua pedina si sposta sulla casella numero " + numeroCasella + ".");
    }

    @Override
    public void onCasellaConoscenzaRaggiunta(int molteplicita) {
        if (molteplicita == 1) System.out.println("Sei finito su una Casella Conoscenza.");
        if (molteplicita == 2) System.out.println("Sei finito su una Casella Conoscenza con DOPPIA DOMANDA.");
    }

    @Override
    public void onDomandaRicevuta(String testo, String materia, String difficolta, ArrayList<String> opzioni) {
        System.out.println("\nDomanda di " + materia.toUpperCase() + "\n" + "Difficoltà: " + difficolta.toUpperCase() + "\n");
        System.out.println(testo);
        if (opzioni != null && !opzioni.isEmpty()) {
            for (int i = 0; i < opzioni.size(); i++) {
                char lettera = (char) ('A' + i);
                System.out.println("   " + lettera + ") " + opzioni.get(i));
            }
        }
        System.out.print("\nInserisci la tua risposta: ");
        String risposta = leggiInput();
        if (listener != null) listener.onRispostaDomandaInserita(risposta);
    }

    @Override
    public void onEsitoDomandaElaborato(boolean vittoria, String bonusMessage, int puntiGuadagnati, char rispostaCorretta, int puntiConoscenza) {
        if (bonusMessage != null && !bonusMessage.isEmpty()) System.out.println("\n" + bonusMessage);
        if (vittoria) {
            System.out.println("\nRisposta esatta! Hai guadagnato " + puntiGuadagnati + " punti.");
        } else {
            System.out.println("\nRisposta sbagliata! La risposta corretta era: " + rispostaCorretta);
            System.out.println("Hai perso " + Math.abs(puntiGuadagnati) + " punti.");
        }
        System.out.println("\nPunti conoscenza totali: " + puntiConoscenza);
    }

    @Override
    public void onCasellaEventoRaggiunta(String tipoEvento) {
        System.out.println("Sei finito su una Casella Evento: " + tipoEvento.toUpperCase() + "! ");
    }

    @Override
    public void onRichiestaSfidato(String sfidante, ArrayList<String> avversari) {
        System.out.println("\n" + sfidante + ", scegli chi vuoi sfidare nel duello:");
        for (int i = 0; i < avversari.size(); i++) System.out.println("   " + (i + 1) + ") " + avversari.get(i));
        while (true) {
            System.out.print("Inserisci il numero del giocatore da sfidare: ");
            try {
                int scelta = Integer.parseInt(leggiInput());
                if (scelta >= 1 && scelta <= avversari.size()) {
                    if (listener != null) listener.onAvversarioScelto(avversari.get(scelta - 1));
                    return;
                }
            } catch (NumberFormatException e) {}
            System.out.println("Scelta non valida! Inserisci un numero della lista.");
        }
    }

    @Override
    public void onEventoCambioDado(String giocatoreCorrente, String dadoVecchio, String dadoNuovo) {
        System.out.println( giocatoreCorrente + " ha cambiato il dado da " + dadoVecchio + " a " + dadoNuovo + "!");
    }

    @Override
    public void onEventoAttivazioneMaledizione(String giocatoreCorrente) {
        System.out.println( giocatoreCorrente + " ha risvegliato la Maledizione del Tabellone! Tutte le altre Caselle Evento si sono trasformate in TRAPPOLE per 5 turni interi!");
    }

    @Override
    public void onEventoPenalita(String giocatoreCorrente, int puntiPersi) {
        System.out.println( giocatoreCorrente + " è incappato in una trappola! Ha perso " + puntiPersi + " punti conoscenza!");
    }

    @Override
    public void onEventoModificaPunti(String giocatoreCorrente, int puntiModificati, int puntiTotali) {
        if (puntiModificati > 0) {
            System.out.println( giocatoreCorrente + " ha guadagnato " + puntiModificati + " punti conoscenza! Ora ha " + puntiTotali + " punti.");
        } else if (puntiModificati < 0) {
            System.out.println( giocatoreCorrente + " ha perso " + Math.abs(puntiModificati) + " punti conoscenza! Ora ha " + puntiTotali + " punti.");
        }
    }

    @Override
    public void onSfidaIniziata(String sfidante, String sfidato) {
        System.out.println("\nInizio del duello: " + sfidante + " CONTRO " + sfidato + "! ");
        System.out.println("Si va a oltranza finché uno indovina e l'altro sbaglia.");
    }

    @Override
    public void onNuovoTurnoSfida(String giocatoreCorrente, String testo, String materia, String difficolta, ArrayList<String> opzioni) {
        System.out.println("\n--- TURNO DI " + giocatoreCorrente.toUpperCase() + " NELLA SFIDA ---");
        System.out.println("Domanda di " + materia.toUpperCase() + "  |  Difficoltà: " + difficolta.toUpperCase());
        System.out.println("\n" + testo);
        if (opzioni != null && !opzioni.isEmpty()) {
            for (int i = 0; i < opzioni.size(); i++) {
                char lettera = (char) ('A' + i);
                System.out.println("   " + lettera + ") " + opzioni.get(i));
            }
        }
        System.out.print("\nInserisci la tua risposta: ");
        String risposta = leggiInput();
        if (listener != null) listener.onRispostaSfidaInserita(risposta);
    }

    @Override
    public void onEsitoTurnoSfida(boolean corretto, char rispostaCorretta) {
        if (corretto) {
            System.out.println("Risposta esatta!");
        } else {
            System.out.println("Risposta sbagliata! La risposta corretta era: " + rispostaCorretta);
        }
    }

    @Override
    public void onSfidaTerminata(String nomeVincitore, String nomePerdente, int puntiInPalio) {
        System.out.println("\n" + nomeVincitore + " ha vinto la sfida! " + nomeVincitore + " ruba a "+ nomePerdente + "  " + puntiInPalio + " punti conoscenza.");
    }

    @Override
    public void onMessaggioStato(String messaggio) {
        System.out.println("\n" + messaggio);
    }

    @Override
    public void onGiocoTerminato(String nomeVincitore) {
        System.out.println("================================================================================================================================");
        System.out.println("HA VINTO GIOCATORE: " + nomeVincitore);
        System.out.println("================================================================================================================================");
        if (this.lettore != null) this.lettore.close();
    }

    @Override
    public void onTabelloneAggiornato(List<String> nomiGiocatori, List<Integer> idGiocatori, List<Integer> numCasellaGiocatori, List<Integer> puntiGiocatori, int NUM_CASELLE, List<Boolean> mappaEventi) {
               final String RESET = "\033[0m";
        final String COLORE_CONOSCENZA = "\033[36m"; 
        final String COLORE_EVENTO = "\033[33m";     
        final String[] coloriPedine = {"\033[91m", "\033[92m", "\033[94m", "\033[95m"};

        System.out.println("\n====================================================== TABELLONE DI GIOCO ======================================================");
        
        System.out.print("Giocatori in campo: ");
        for (int i = 0; i < nomiGiocatori.size(); i++) {
            String nome = nomiGiocatori.get(i);
            int id = idGiocatori.get(i);
            int punti = puntiGiocatori.get(i);
            String colore = coloriPedine[i % coloriPedine.length];
            System.out.print(colore + "■ " + nome + " (P" + id + ") [Punti: " + punti + "]  " + RESET);
        }
        System.out.println("\nLegenda: " + COLORE_CONOSCENZA + "+-------+ Conoscenza" + RESET + "  |  " + COLORE_EVENTO + "+-------+ Evento (★)" + RESET + "\n");

        int colonne = 10;
        int righe = NUM_CASELLE / colonne;

        for (int r = 0; r < righe; r++) {
            boolean daSinistraADestra = (r % 2 == 0);
            
            StringBuilder lineaSuperiore = new StringBuilder();
            StringBuilder lineaCentrale = new StringBuilder();
            StringBuilder lineaInferiore = new StringBuilder();

            for (int c = 0; c < colonne; c++) {
                int numeroCasella = daSinistraADestra ? (r * colonne) + c + 1 : (r * colonne) + (colonne - c);


                boolean isEvento = mappaEventi.get(numeroCasella - 1);
                
                String coloreCasella = isEvento ? COLORE_EVENTO : COLORE_CONOSCENZA;
                String marker = isEvento ? "★" : " ";

                StringBuilder pedineStr = new StringBuilder();
                for (int p = 0; p < idGiocatori.size(); p++) {
                    int casellaGiocatore = numCasellaGiocatori.get(p);
                    if (casellaGiocatore == numeroCasella) {
                        String colorePedina = coloriPedine[p % coloriPedine.length];
                        pedineStr.append(colorePedina).append(idGiocatori.get(p)).append(coloreCasella);
                    }
                }
                
                int caratteriReali = pedineStr.toString().replaceAll("\033\\[[0-9;]*m", "").length();
                StringBuilder spaziPadding = new StringBuilder();
                for (int k = caratteriReali; k < 4; k++) {
                    spaziPadding.append(" ");
                }

                String pezzoSup = coloreCasella + "+-------+" + RESET;
                String contenutoInterno = coloreCasella + String.format("%02d", numeroCasella) + marker + RESET + pedineStr.toString() + spaziPadding.toString();
                String pezzoMid = coloreCasella + "|" + RESET + contenutoInterno + coloreCasella + "|" + RESET;
                String pezzoInf = coloreCasella + "+-------+" + RESET;

                lineaSuperiore.append(pezzoSup);
                lineaCentrale.append(pezzoMid);
                lineaInferiore.append(pezzoInf);

                if (c < colonne - 1) {
                    lineaSuperiore.append("    ");
                    lineaCentrale.append(daSinistraADestra ? " -> " : " <- ");
                    lineaInferiore.append("    ");
                }
            }

            System.out.println(lineaSuperiore.toString());
            System.out.println(lineaCentrale.toString());
            System.out.println(lineaInferiore.toString());

            if (r < righe - 1) {
                if (daSinistraADestra) {
                    System.out.printf("%121s%n", "v");
                } else {
                    System.out.printf("%5s%n", "v");
                }
            }
        }
        System.out.println("================================================================================================================================");
    
    }
}