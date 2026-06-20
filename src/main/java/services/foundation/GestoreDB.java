package services.foundation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestoreDB {

    private static GestoreDB istanzaUnica;
    private final String urlDatabase;
    private final String username;
    private final String password;
    private Connection connessione;

    private GestoreDB(String urlDatabase, String username, String password) {
        this.urlDatabase = urlDatabase; 
        this.username = username;
        this.password = password;
        connetti();
    }

    protected static GestoreDB getInstance(String url, String user, String pass) {
        if (istanzaUnica == null) {
            istanzaUnica = new GestoreDB(url, user, pass);
        }
        return istanzaUnica;
    }

    protected static GestoreDB getInstance() {
        if (istanzaUnica == null) {
            throw new IllegalStateException("Il database non è ancora stato inizializzato!");
        }
        return istanzaUnica;
    }

    private void connetti() {
        try {
            this.connessione = DriverManager.getConnection(urlDatabase, username, password); // Per MySQL
            //this.connessione = DriverManager.getConnection(urlDatabase); // Per SQLite, non richiede username e password
            //System.out.println("\nConnessione al database stabilita con successo!");
        } catch (SQLException e) {
            System.err.println("\nErrore: impossibile connettersi al database!");
            e.printStackTrace();
        }
    }

    protected Connection getConnessione() {
        return this.connessione;
    }


    protected void chiudiConnessione() {
        try {
            if (this.connessione != null && !this.connessione.isClosed()) {
                this.connessione.close();
                //System.out.println("\nConnessione al database chiusa correttamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}