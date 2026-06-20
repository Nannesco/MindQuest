package domain.pedine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import domain.regole.RegolePedina;
import domain.caselle.Casella;
import domain.dadi.Dado;
import domain.dadi.DadoFactory;

public class PedinaFactory {

    
    private final Random random = new Random();
    private final List<TipoPedina> pedineDisponibili;
    private final DadoFactory dadoFactory = new DadoFactory();


    public enum TipoPedina {
        SAGGIO, STUDIOSO, SPERIMENTATORE, FORTUNATO
    }


    public PedinaFactory() {
        this.pedineDisponibili = new ArrayList<>();
        this.pedineDisponibili.add(TipoPedina.SAGGIO);
        this.pedineDisponibili.add(TipoPedina.STUDIOSO);
        this.pedineDisponibili.add(TipoPedina.SPERIMENTATORE);
        this.pedineDisponibili.add(TipoPedina.FORTUNATO);
    }



    public Pedina creaPedinaCasuale(Casella casellaIniziale, RegolePedina regole) {
        if (pedineDisponibili.isEmpty()) {
            throw new IllegalStateException("Errore: Tutte le pedine sono già state assegnate!");
        }

        int indiceCasuale = random.nextInt(pedineDisponibili.size());
        TipoPedina scelta = pedineDisponibili.remove(indiceCasuale);

        Dado dadoIniziale = dadoFactory.creaDadoNormale();

        switch (scelta) {
            case SAGGIO -> { 
                return new PedinaSaggio(casellaIniziale, 0, dadoIniziale, regole); 
            }
            case STUDIOSO -> { 
                return new PedinaStudioso(casellaIniziale, 0, dadoIniziale, regole); 
            }
            case SPERIMENTATORE -> { 
                return new PedinaSperimentatore(casellaIniziale, 0, dadoIniziale, regole); 
            }
            case FORTUNATO -> { 
                return new PedinaFortunato(casellaIniziale, 0, dadoIniziale, regole); 
            }
            default -> throw new IllegalArgumentException("Tipo pedina non gestito");
        }
    }
}