package services.foundation;

import java.util.List;
import java.util.Map;

import domain.Domanda;
import domain.caselle.Casella;
import domain.contratti.GestoreDomanda;



public class PersistentManager implements GestoreDomanda  {


    private final CasellaRepository  casellaRepo;
    private final DomandaRepository domandaRepo;
    private final RegoleRepository regoleRepo;
    private final GestoreDB db;

    public PersistentManager(String URL, String USER, String PASSWORD){
        db = GestoreDB.getInstance(URL, USER, PASSWORD);
        casellaRepo = new CasellaRepository();
        domandaRepo = new DomandaRepository();
         regoleRepo = new RegoleRepository();
    }


    public Casella getCasellaByID(int posizione) {
        return casellaRepo.getCasellaByID(posizione);
    } 

    public List<Casella> getCaselle() {
        return casellaRepo.getCaselle();
    }

    public int getNumeroCaselle() {
        return casellaRepo.getNumeroCaselle();
    }

    @Override
    public Domanda pescaDomanda(String materia, String difficolta) {
        return domandaRepo.pescaDomanda(materia, difficolta);
    }

    @Override
    public Domanda pescaDomandaCasuale() {
        return domandaRepo.pescaDomandaCasuale();
    }

    @Override
    public Domanda pescaDomandaByDifficolta(String difficolta){
        return domandaRepo.pescaDomandaByDifficolta(difficolta);
    }


    public Map<String, String> caricaRegole() {
        return regoleRepo.caricaRegole();
    }


    public void chiudiConnessione() {
        db.chiudiConnessione();
    }    

    
}
