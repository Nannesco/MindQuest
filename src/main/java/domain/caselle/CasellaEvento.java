package domain.caselle;

import java.util.function.Consumer;

import domain.contratti.GestoreCasella;
import domain.stati.StatoCasellaEvento;
import domain.strategie.StrategiaEvento;

public class CasellaEvento extends Casella {
    
    private final StrategiaEvento strategiaBase;


    public CasellaEvento(int numeroCasella, StrategiaEvento strategia) {
        super(numeroCasella);
        this.strategiaBase = strategia;
    }

    public StrategiaEvento getStrategia(StatoCasellaEvento stato) {
        return stato.getStrategiaDaEseguire(this.strategiaBase);
    }


    public String getTipoEvento() {
        return this.strategiaBase.getNomeEvento();
    }

    @Override
    public String getMateria() {
        return null; 
    }

    @Override
    public String getDifficolta() {
        return null; 
    }

     @Override
    public Consumer<GestoreCasella> ottieniDispatcher() {
        return g -> g.gestisciEvento(this.getTipoEvento());
    }

}