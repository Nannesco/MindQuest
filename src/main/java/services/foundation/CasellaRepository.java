package services.foundation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import domain.caselle.Casella;
import domain.caselle.CasellaConoscenza;
import domain.caselle.CasellaEvento;
import domain.strategie.AttivazioneMaledizioneStrategy;
import domain.strategie.CambioDadoStrategy;
import domain.strategie.FineGiocoStrategy;
import domain.strategie.ModificaPuntiStrategy;
import domain.strategie.SfidaUnoControUnoStrategy;

public class CasellaRepository {




    protected CasellaRepository() {

    }

    public Casella getCasellaByID(int posizione) {
        Connection connessione = GestoreDB.getInstance().getConnessione();
        if (connessione == null) {
            System.err.println("\nNessuna connessione attiva col database.");
            return null;
        }

        Casella casella = null;
        String query = "SELECT * FROM casella WHERE ID = ?";

        try (PreparedStatement statement = connessione.prepareStatement(query)) {
            statement.setInt(1, posizione);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                boolean isEvento = resultSet.getBoolean("isEvento");


            if (isEvento) {
                String tipoEvento = resultSet.getString("TipoEvento");

                casella = mappaCasellaEvento(posizione, tipoEvento);
            } else {
                casella = new CasellaConoscenza(posizione, resultSet.getString("Difficolta"), resultSet.getInt("MOLTEPLICITA"), resultSet.getString("Materia"));
            }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'estrazione della casella: " + e.getMessage());
        }

        return casella;
    } 


    public List<Casella> getCaselle() {
    Connection connessione = GestoreDB.getInstance().getConnessione();
    if (connessione == null) {
        System.err.println("\nNessuna connessione attiva col database.");
        return Collections.emptyList();
    }

    List<Casella> caselle = new ArrayList<>();

    String query = "SELECT * FROM casella ORDER BY ID ASC";

    try (PreparedStatement statement = connessione.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {


        while (resultSet.next()) {
        int posizione = resultSet.getInt("ID");
        boolean isEvento = resultSet.getBoolean("isEvento");

        Casella casella;
        if (isEvento) {
            String tipoEvento = resultSet.getString("TipoEvento");

            casella = mappaCasellaEvento(posizione, tipoEvento);
        } else {
            casella = new CasellaConoscenza(posizione, resultSet.getString("Difficolta"), resultSet.getInt("MOLTEPLICITA"), resultSet.getString("Materia"));
        }
        caselle.add(casella);
    }
    } catch (SQLException e) {
        System.err.println("Errore durante l'estrazione delle caselle: " + e.getMessage());
    }
    return caselle;
}


private Casella mappaCasellaEvento(int posizione, String tipoEvento) {
    if (tipoEvento == null) return null;
    
    switch (tipoEvento.toLowerCase()) {
        case "1v1": 
            return new CasellaEvento(posizione, new SfidaUnoControUnoStrategy());
        case "cambiodado": 
            return new CasellaEvento(posizione, new CambioDadoStrategy());
        case "modificapunti": 
            return new CasellaEvento(posizione, new ModificaPuntiStrategy());
        case "attivazionemaledizione": 
            return new CasellaEvento(posizione, new AttivazioneMaledizioneStrategy());
        case "fine": 
            return new CasellaEvento(posizione, new FineGiocoStrategy());
        default: 
            return null;
    }
}



    public int getNumeroCaselle() {
        Connection connessione = GestoreDB.getInstance().getConnessione();
        if (connessione == null) {
            System.err.println("\nNessuna connessione attiva col database.");
            return 0;
        }

        int numeroCaselle = 0;
        String query = "SELECT COUNT(*) AS total FROM casella";

        try (PreparedStatement statement = connessione.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                numeroCaselle = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'estrazione del numero di caselle: " + e.getMessage());
        }

        return numeroCaselle;
    }
}
