package domain;

import java.util.Map;


public final class Regole {


    private static int sogliaVittoria = 70;



    private static int puntiDomandaFacile = 3;
    private static int malusDomandaFacile = 3;
    private static int puntiDomandaDifficile = 5;
    private static int malusDomandaDifficile = 1;


    private static int penalitaTrappola = 3;
    private static int puntiInPalioSfida = 10;


    private static int saggioBonusExtra = 2;
    private static int studiosoStreakRichiesta = 5;
    private static int sperimentatoreBonusDifficile = 5;
    private static int fortunatoTiroTrigger = 6;
    private static int fortunatoPuntiBonus = 5;

    

    private Regole() {

    }

    public static void inizializza(Map<String, String> datiDb) {

        sogliaVittoria = Integer.parseInt(datiDb.getOrDefault("soglia_vittoria", "70"));
        


        puntiDomandaFacile = Integer.parseInt(datiDb.getOrDefault("punti_facile", "3"));
        malusDomandaFacile = Integer.parseInt(datiDb.getOrDefault("malus_facile", "3"));
        puntiDomandaDifficile = Integer.parseInt(datiDb.getOrDefault("punti_difficile", "5"));
        malusDomandaDifficile = Integer.parseInt(datiDb.getOrDefault("malus_difficile", "1"));



        penalitaTrappola = Integer.parseInt(datiDb.getOrDefault("penalita_trappola", "3"));
        puntiInPalioSfida = Integer.parseInt(datiDb.getOrDefault("punti_sfida", "10"));


        saggioBonusExtra = Integer.parseInt(datiDb.getOrDefault("saggio_bonus", "2"));
        studiosoStreakRichiesta = Integer.parseInt(datiDb.getOrDefault("studioso_streak", "5"));
        sperimentatoreBonusDifficile = Integer.parseInt(datiDb.getOrDefault("sperimentatore_bonus", "5"));
        fortunatoTiroTrigger = Integer.parseInt(datiDb.getOrDefault("fortunato_trigger", "6"));
        fortunatoPuntiBonus = Integer.parseInt(datiDb.getOrDefault("fortunato_bonus", "5"));



    }

    public static int getSogliaVittoria() {
        return sogliaVittoria;
    }


    public static int getPuntiDomandaFacile() {
        return puntiDomandaFacile;
    }

    public static int getMalusDomandaFacile() {
        return malusDomandaFacile;
    }

    public static int getPuntiDomandaDifficile() {
        return puntiDomandaDifficile;
    }

    public static int getMalusDomandaDifficile() {
        return malusDomandaDifficile;
    }

    public static int getPenalitaTrappola() {
        return penalitaTrappola;
    }


    public static int getPuntiInPalioSfida() {
        return puntiInPalioSfida;
    }

    public static int getSaggioBonusExtra() {
        return saggioBonusExtra;
    }

    public static int getStudiosoStreakRichiesta() {
        return studiosoStreakRichiesta;
    }

    public static int getSperimentatoreBonusDifficile() {
        return sperimentatoreBonusDifficile;
    }

    public static int getFortunatoTiroTrigger() {
        return fortunatoTiroTrigger;
    }

    public static int getFortunatoPuntiBonus() {
        return fortunatoPuntiBonus;
    }

}
