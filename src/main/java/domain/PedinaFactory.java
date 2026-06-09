package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PedinaFactory {


    private static PedinaFactory istanza;
    
    private final Random random = new Random();
    private final List<TipoPedina> pedineDisponibili;
    private final DadoFactory dadoFactory = DadoFactory.getInstance();


    public enum TipoPedina {
        SAGGIO, STUDIOSO, SPERIMENTATORE, FORTUNATO
    }


    private PedinaFactory() {
        this.pedineDisponibili = new ArrayList<>();
        this.pedineDisponibili.add(TipoPedina.SAGGIO);
        this.pedineDisponibili.add(TipoPedina.STUDIOSO);
        this.pedineDisponibili.add(TipoPedina.SPERIMENTATORE);
        this.pedineDisponibili.add(TipoPedina.FORTUNATO);
    }


    public static PedinaFactory getInstance() {
        if (istanza == null) {
            istanza = new PedinaFactory();
        }
        return istanza;
    }


    public Pedina creaPedinaCasuale(Casella casellaIniziale) {
        if (pedineDisponibili.isEmpty()) {
            throw new IllegalStateException("Errore: Tutte le pedine sono già state assegnate!");
        }

        int indiceCasuale = random.nextInt(pedineDisponibili.size());
        TipoPedina scelta = pedineDisponibili.remove(indiceCasuale);

        Dado dadoIniziale = dadoFactory.creaDadoNormale();

        switch (scelta) {
            case SAGGIO -> { 
                return new PedinaSaggio(casellaIniziale, 0, dadoIniziale); 
            }
            case STUDIOSO -> { 
                return new PedinaStudioso(casellaIniziale, 0, dadoIniziale); 
            }
            case SPERIMENTATORE -> { 
                return new PedinaSperimentatore(casellaIniziale, 0, dadoIniziale); 
            }
            case FORTUNATO -> { 
                return new PedinaFortunato(casellaIniziale, 0, dadoIniziale); 
            }
            default -> throw new IllegalArgumentException("Tipo pedina non gestito");
        }
    }


    public void resetFactory() {
        this.pedineDisponibili.clear();
        this.pedineDisponibili.add(TipoPedina.SAGGIO);
        this.pedineDisponibili.add(TipoPedina.STUDIOSO);
        this.pedineDisponibili.add(TipoPedina.SPERIMENTATORE);
        this.pedineDisponibili.add(TipoPedina.FORTUNATO);
    }
}