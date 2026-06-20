package domain.caselle;

import java.util.function.Consumer;

import domain.contratti.GestoreCasella;

public abstract class Casella {

	private final int numeroCasella;

    public Casella(int numeroCasella) {
        this.numeroCasella = numeroCasella;
    }

    public int getNumeroCasella() {
        return this.numeroCasella;
    }

    public abstract String getMateria();

    public abstract String getDifficolta();

    public int getMolteplicita() {
        return 0; 
    }

    public abstract Consumer<GestoreCasella> ottieniDispatcher();




}