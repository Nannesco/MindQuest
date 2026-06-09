package config;

public class DBConfig {
    //public static final String DB_URL = "jdbc:mysql://localhost:3306/domande"; (usato per MySQL)
    public static final String DB_URL = "jdbc:sqlite:MindQuestDB.sqlite";
    public static final String DB_USER = "root"; // Non necessario per SQLite
    public static final String DB_PASSWORD = ""; // Non necessario per SQLite
}

