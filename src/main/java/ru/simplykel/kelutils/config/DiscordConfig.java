package ru.simplykel.kelutils.config;

import net.minecraft.client.MinecraftClient;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DiscordConfig {
    public static String DISCORD_TOKEN = "";

    /**
     * Сохранение конфигурации
     */
    public static void save(){
        MinecraftClient mc = MinecraftClient.getInstance();
        final Path configFile = mc.runDirectory.toPath().resolve("KelUtils/config.json");
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("DISCORD_TOKEN", DISCORD_TOKEN);
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
        MinecraftClient mc = MinecraftClient.getInstance();
        final Path configFile = mc.runDirectory.toPath().resolve("KelUtils/config.json");
        try{
            JSONObject jsonConfig = new JSONObject(Files.readString(configFile));
            if(!jsonConfig.isNull("DISCORD_TOKEN")) DISCORD_TOKEN = jsonConfig.getString("DISCORD_TOKEN");
            else DISCORD_TOKEN = "";
        } catch (Exception e){
            e.printStackTrace();
            save();
        }

    }
}
