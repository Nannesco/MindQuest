package config;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.io.Reader;

public record DBConfig (String url, String user, String password) {

    public static DBConfig load() {
        try (Reader reader = new InputStreamReader(
                DBConfig.class.getResourceAsStream("/db_config.json"))) {
            return new Gson().fromJson(reader, DBConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Impossibile caricare db_config.json", e);
        }
    }
}

