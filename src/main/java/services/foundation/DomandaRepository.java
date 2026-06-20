package services.foundation;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import domain.Domanda;

public class DomandaRepository {

    private final Set<Integer> domandeUscite;

    protected DomandaRepository() {
        this.domandeUscite = new HashSet<>();
    }


    public void resettaMemoriaDomande() {
        this.domandeUscite.clear();
    }

    private Domanda pesca(String queryBase, Object... params) {
        Connection connessione = GestoreDB.getInstance().getConnessione();
        if (connessione == null) {
            System.err.println("\nNessuna connessione attiva col database.");
            return null;
        }

        Domanda domandaPescata = null;
        StringBuilder queryCompleta = new StringBuilder(queryBase);

        if (!domandeUscite.isEmpty()) {
            queryCompleta.append(" AND ID NOT IN (");
            int size = domandeUscite.size();
            for (int i = 0; i < size; i++) {
                queryCompleta.append("?");
                if (i < size - 1) queryCompleta.append(",");
            }
            queryCompleta.append(")");
        }

        queryCompleta.append(" ORDER BY RANDOM() LIMIT 1");

        try (PreparedStatement statement = connessione.prepareStatement(queryCompleta.toString())) {
            int index = 1;

            for (Object param : params) {
                statement.setObject(index++, param);
            }

            if (!domandeUscite.isEmpty()) {
                for (Integer idGiaUscito : domandeUscite) {
                    statement.setInt(index++, idGiaUscito);
                }
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    
                    // MAPPATURA DIRETTA DEL RECORD NELL'OGGETTO DOMANDA
                    String testo = resultSet.getString("Testo");
                    
                    ArrayList<String> opzioni = new ArrayList<>(Arrays.asList(
                        resultSet.getString("OpzioneA"),
                        resultSet.getString("OpzioneB"),
                        resultSet.getString("OpzioneC"),
                        resultSet.getString("OpzioneD")
                    ));
                    
                    String rispStr = resultSet.getString("RispostaCorretta");
                    char rispostaCorretta = (rispStr != null && !rispStr.isEmpty()) ? rispStr.charAt(0) : ' ';
                    
                    String difficolta = resultSet.getString("Difficolta");
                    String materia = resultSet.getString("Materia");

        
                    domandaPescata = new Domanda(testo, opzioni, rispostaCorretta, difficolta, materia);

                    this.domandeUscite.add(id);
                }
            }
        } catch (SQLException e) {
            System.err.println("\nErrore durante l'estrazione della domanda.");
            e.printStackTrace();
        }

        if (domandaPescata == null && !this.domandeUscite.isEmpty()) {
            System.out.println("\n[DB] Domande esaurite per questa categoria. Rimescolo il mazzo...");
            this.domandeUscite.clear();
            return pesca(queryBase, params); 
        }

        return domandaPescata;
    }

    public Domanda pescaDomanda(String materia, String difficolta) {
        String queryBase = "SELECT ID, Testo, OpzioneA, OpzioneB, OpzioneC, OpzioneD, RispostaCorretta, Difficolta, Materia FROM domanda WHERE materia = ? AND difficolta = ?";
        return pesca(queryBase, materia, difficolta);
    }

    public Domanda pescaDomandaCasuale() {
        String queryBase = "SELECT ID, Testo, OpzioneA, OpzioneB, OpzioneC, OpzioneD, RispostaCorretta, Difficolta, Materia FROM domanda WHERE 1=1";
        return pesca(queryBase);
    }

    public Domanda pescaDomandaByDifficolta(String difficolta) {
        String queryBase = "SELECT ID, Testo, OpzioneA, OpzioneB, OpzioneC, OpzioneD, RispostaCorretta, Difficolta, Materia FROM domanda WHERE Difficolta = ?";
        return pesca(queryBase, difficolta);
    } 
}