package domain;

import java.util.Random;

public class DadoNormale implements Dado {

	private int numero;
	final int puntiDado = 0;


	@Override
	public int lancia() {
		
		Random rand = new Random();
		this.numero = rand.nextInt(6) + 1;
		return this.numero;

	}

	@Override
	public String toString() {
		return "Dado Normale";
	}

	@Override
    public int getPuntiDado() {
        return puntiDado;
    }

}