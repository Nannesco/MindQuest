package config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import domain.Casella;
import domain.CasellaEvento;
import domain.Tabellone;
import domain.PedinaFactory;
import domain.Pedina;
import domain.Giocatore;
import domain.Regole;
import services.foundation.CasellaRepository;
import services.foundation.RegoleRepository;

public class SetupGioco {

    private final CasellaRepository casellaRepo = CasellaRepository.getInstance();
    private final RegoleRepository regoleRepo = RegoleRepository.getInstance();

    public SetupGioco() {
        // Carica le regole dal DB/Repository all'atto della creazione del setup
        Regole.inizializza(regoleRepo.caricaRegole());
    }

    // Crea il tabellone leggendo i dati dal CasellaRepository
    public Tabellone creaTabellone() {
        int numeroCaselle = casellaRepo.getNumeroCaselle();
        List<Casella> caselle = casellaRepo.getCaselle();

    
        if (caselle.isEmpty()) {
            throw new IllegalStateException(
                "Impossibile avviare il gioco: la tabella 'casella' del database è vuota o irraggiungibile.\n" +
                "Verifica che:\n" +
                "  1. XAMPP -> MySQL sia avviato\n" +
                "  2. Le credenziali in DBConfig.java siano corrette\n" +
                "  3. Il database 'domande' esista e contenga dati nella tabella 'casella'"
            );
        }
        return new Tabellone(numeroCaselle, caselle);
    }


    //Fabbrica la lista dei giocatori, assegna le pedine e ne mescola l'ordine

    public List<Giocatore> creaEOrdinaGiocatori(List<String> nomiRicevuti) {
        List<Giocatore> listaGiocatori = new ArrayList<>();
        PedinaFactory pedinaFactory = PedinaFactory.getInstance();
        pedinaFactory.resetFactory();
        
        // Casella iniziale fittizia (posizione 0)
        Casella casellaIniziale = new CasellaEvento(0, null);
        
        for (int i = 0; i < nomiRicevuti.size(); i++) {
            Pedina pedina = pedinaFactory.creaPedinaCasuale(casellaIniziale);
            Giocatore giocatore = new Giocatore(nomiRicevuti.get(i), i + 1, pedina);
            listaGiocatori.add(giocatore);
        }
        
        // Mescola l'ordine dei turni in modo casuale
        Collections.shuffle(listaGiocatori);
        return listaGiocatori;
    }
}
