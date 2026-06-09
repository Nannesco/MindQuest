package domain;
import java.util.ArrayList;
import java.util.Random;

public class DadoFactory {
    

    private static DadoFactory istanza;
    
    private final ArrayList<Dado> dadiDisponibili;
    private final Random random = new Random();

  
    private DadoFactory() {
        this.dadiDisponibili = new ArrayList<>();
        this.dadiDisponibili.add(new DadoNormale());
        this.dadiDisponibili.add(new DadoAvventuroso());
        this.dadiDisponibili.add(new DadoIntellettuale());
    }


    public static DadoFactory getInstance() {
        if (istanza == null) {
            istanza = new DadoFactory();
        }
        return istanza;
    }

    public Dado creaDadoNormale() {
        return new DadoNormale();
    }

    public Dado creaDadoDiverso(Dado dadoAttuale) {
        ArrayList<Dado> dadiRimanenti = new ArrayList<>(dadiDisponibili);
        dadiRimanenti.removeIf(d -> d.getClass().equals(dadoAttuale.getClass()));
        return dadiRimanenti.get(random.nextInt(dadiRimanenti.size()));
    }
}
