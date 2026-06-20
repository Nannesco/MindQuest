package domain;

import java.util.*;

import domain.regole.RegolePedina;
import domain.caselle.Casella;
import domain.caselle.CasellaEvento;
import domain.dto.InfoGiocatori;
import domain.pedine.Pedina;
import domain.pedine.PedinaFactory;

public class Lobby {

    private final List<Giocatore> giocatori;
    private final List<String> nomiOrdinati;
    private final List<Integer> idGiocatori;
    private final Map<String, String> nomePedina;

    public Lobby(List<String> nomi, RegolePedina regole) {
        PedinaFactory factory = new PedinaFactory();
        Casella casellaIniziale = new CasellaEvento(0, null);

        List<Giocatore> lista = new ArrayList<>();
        for (int i = 0; i < nomi.size(); i++) {
            Pedina pedina = factory.creaPedinaCasuale(casellaIniziale, regole);
            lista.add(new Giocatore(nomi.get(i), i + 1, pedina));
        }
        Collections.shuffle(lista);

        this.giocatori = Collections.unmodifiableList(lista);
        this.nomiOrdinati = lista.stream().map(Giocatore::getUsername).toList();
        this.idGiocatori = lista.stream().map(Giocatore::getIdGiocatore).toList();

        Map<String, String> mappa = new HashMap<>();
        for (Giocatore g : lista) mappa.put(g.getUsername(), g.getPedina().toString());
        this.nomePedina = Collections.unmodifiableMap(mappa);

    }

    public Giocatore trovaGiocatorePerNome(String nome) {
        for (Giocatore g : giocatori)
            if (g.getUsername().equals(nome)) return g;
        return null;
    }

    public ArrayList<String> getNomiAvversariEscluso(Giocatore escluso) {
        ArrayList<String> lista = new ArrayList<>();
        for (Giocatore g : giocatori)
        if (!g.equals(escluso)) lista.add(g.getUsername());
    return lista;
    }

    public Giocatore getGiocatoreByIndex(int index){
        return giocatori.get(index);
    }

    public InfoGiocatori getInfo(){
        return new InfoGiocatori(nomiOrdinati, idGiocatori, calcolaPosizioni(), calcolaPunti());
    }

    private List<Integer> calcolaPosizioni(){
        List<Integer> posizioni = new ArrayList<>();
        for (Giocatore g : giocatori){
            posizioni.add(g.getPedina().getCasellaCorrente().getNumeroCasella());
        }
        return posizioni;
    }

    private List<Integer> calcolaPunti(){
        List<Integer> punti = new ArrayList<>();
        for (Giocatore g : giocatori){
            punti.add(g.getPedina().getPuntiConoscenza());
        }
        return punti;
    }

    public List<Giocatore> getGiocatori() { 
        return giocatori; 
    }

    public int getNumeroGiocatori() {
        return giocatori.size();
    
    }

    public List<String> getNomiOrdinati() { 
        return nomiOrdinati; 
    }

    public List<Integer> getIdGiocatori() { 
        return idGiocatori; 
    }

    public String getNomePedina(String nome) { 
        return nomePedina.get(nome); 
    }
}
