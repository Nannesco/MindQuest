package controller.contratti;

public interface EventoSubject {
    void addObserver(EventoObserver observer);
    void eseguiEvento();
}
