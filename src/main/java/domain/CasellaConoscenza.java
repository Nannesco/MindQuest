package domain;

import java.util.function.Consumer;

import controller.GestoreCasella;

public class CasellaConoscenza extends Casella {

    private final String difficolta;
	private final int molteplicita;
    private final String materia;

    public CasellaConoscenza(int numeroCasella, String difficolta, int molteplicita, String materia) {
        super(numeroCasella);
        this.difficolta = difficolta;
        this.molteplicita = molteplicita;
        this.materia = materia;
    }

    public String getDifficolta() {
        return difficolta;
    }

    @Override
    public int getMolteplicita() {
        return molteplicita;
    }

    public String getMateria() {
        return materia;
    }

    @Override
    public Consumer<GestoreCasella> ottieniDispatcher() {
        return g -> g.gestisciConoscenza(this.molteplicita);
    }

}