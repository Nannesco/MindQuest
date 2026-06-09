package controller;

/*
Interfaccia implementata da GiocoController.
 Permette a SetupController ed EventoController di richiamare
 GameController senza dipendenze dirette tra i sotto-controller.
 */
interface Coordinatore {
    void avviaTurnoCorrente(); // usata da SetupController al termine del setup
    void concludiTurno();  // usata da EventoController al termine dell'evento
}
