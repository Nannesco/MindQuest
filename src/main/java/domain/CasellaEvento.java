package domain;

import java.util.function.Consumer;

public class CasellaEvento extends Casella {
    
    private final StrategiaEvento strategiaBase;

    private static StatoCasellaEvento statoCondiviso = new StatoNormale();

    public CasellaEvento(int numeroCasella, StrategiaEvento strategia) {
        super(numeroCasella);
        this.strategiaBase = strategia;
    }

    public StrategiaEvento getStrategia() {
        return statoCondiviso.getStrategiaDaEseguire(this.strategiaBase);
    }

    public static void setStatoCondiviso(StatoCasellaEvento nuovoStato) {
        statoCondiviso = nuovoStato;
    }

    public static void avanzaRoundGlobale() {
        statoCondiviso = statoCondiviso.avanzaRound();
    }


    public String getTipoEvento() {
        return this.strategiaBase.getNomeEvento();
    }

    @Override
    protected String getMateria() {
        return null; 
    }

    @Override
    protected String getDifficolta() {
        return null; 
    }

     @Override
    public Consumer<GestoreCasella> ottieniDispatcher() {
        return g -> g.gestisciEvento(this.getTipoEvento());
    }

}