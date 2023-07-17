package ru.simplykel.kelutils.client.config;

import net.minecraft.client.MinecraftClient;
import org.json.JSONObject;
import ru.simplykel.kelutils.client.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HUDConfig {
    public static int HUD = 2;
    public static int CHAT = 0;

    /**
     * Сохранение конфигурации
     */
    public static void save(){
        MinecraftClient mc = MinecraftClient.getInstance();
        final Path configFile = mc.runDirectory.toPath().resolve("KelUtils/hud.json");
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("HUD", HUD)
                .put("CHAT", CHAT);
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
        final Path configFile = mc.runDirectory.toPath().resolve("KelUtils/hud.json");
        try{
            JSONObject jsonConfig = new JSONObject(Files.readString(configFile));
            if(!jsonConfig.isNull("HUD")) HUD = jsonConfig.getInt("HUD");
            else HUD = 2;
            if(!jsonConfig.isNull("CHAT")) CHAT = jsonConfig.getInt("CHAT");
            else CHAT = 0;
        } catch (Exception e){
            e.printStackTrace();
            save();
        }

    }
}
