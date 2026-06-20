package domain.dadi;

import java.util.Random;

public class DadoIntellettuale implements Dado {
    final int puntiDado = 1;

	public DadoIntellettuale() {
    }

    @Override
    public int lancia() {
        Random rand = new Random();
        
        int tiro1 = rand.nextInt(6) + 1;
        int tiro2 = rand.nextInt(6) + 1;
        
        int risultato = Math.min(tiro1, tiro2);


        return risultato;
        
    }

    @Override
    public int getPuntiDado() {
        return puntiDado;
    }



    @Override
    public String toString() {
        return "Dado Intellettuale";
    }
}