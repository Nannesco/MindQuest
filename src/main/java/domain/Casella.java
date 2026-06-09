package domain;

import java.util.function.Consumer;

import controller.GestoreCasella;

public abstract class Casella {

	private final int numeroCasella;

    public Casella(int numeroCasella) {
        this.numeroCasella = numeroCasella;
    }

    public int getNumeroCasella() {
        return this.numeroCasella;
    }

    protected abstract String getMateria();

    protected abstract String getDifficolta();

    public int getMolteplicita() {
        return 0; 
    }

    public abstract Consumer<GestoreCasella> ottieniDispatcher();




}