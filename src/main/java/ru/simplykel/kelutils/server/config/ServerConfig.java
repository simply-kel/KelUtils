package ru.simplykel.kelutils.server.config;

import org.json.JSONObject;
import ru.simplykel.kelutils.server.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServerConfig {
    public static boolean ENABLE_MOTD = true;
    public static boolean ENABLE_DISCORD = false;

    /**
     * Сохранение конфигурации
     */
    public static void save(){
        final Path configFile = Main.server.getRunDirectory().toPath().resolve("KelUtils/config.json");
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("ENABLE_MOTD", ENABLE_MOTD)
                .put("ENABLE_DISCORD", ENABLE_DISCORD);
        try {
            Files.createDirectories(configFile.getParent());
            Files.writeString(configFile, jsonConfig.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загрузка файла конфигов
     */
    public static void load(){
        final Path configFile = Main.server.getRunDirectory().toPath().resolve("KelUtils/config.json");
        try{
            JSONObject jsonConfig = new JSONObject(Files.readString(configFile));
            if(!jsonConfig.isNull("ENABLE_MOTD")) ENABLE_MOTD = jsonConfig.getBoolean("ENABLE_MOTD");
            else ENABLE_MOTD = true;

            if(!jsonConfig.isNull("ENABLE_DISCORD")) ENABLE_DISCORD = jsonConfig.getBoolean("ENABLE_DISCORD");
            else ENABLE_DISCORD = false;
        } catch (Exception e){
            e.printStackTrace();
            save();
        }

    }
}
