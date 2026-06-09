package services.foundation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;


public class RegoleRepository {
        private static RegoleRepository instance;
    
        private RegoleRepository() {

        }
    
        public static RegoleRepository getInstance() {
            if (instance == null) {
                instance = new RegoleRepository();
            }
            return instance;
        }
    
        public Map<String, String> caricaRegole() {
        Connection connessione = GestoreDB.getInstance().getConnessione();
        if (connessione == null) {
            System.err.println("\nNessuna connessione attiva col database.");
            return null;
        }

        HashMap<String, String> datiDB = new HashMap<>();
        String query = "SELECT * FROM configurazione";


    try (PreparedStatement statement = connessione.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {


        while (resultSet.next()) {
            String chiave = resultSet.getString("chiave");
            String valore = resultSet.getString("valore");
            datiDB.put(chiave, valore);
        }

        } catch (SQLException e) {
            System.err.println("Errore durante l'estrazione delle regole: " + e.getMessage());
        }


            return datiDB;
        }

}
