package ru.simplykel.kelutils.config;

import net.minecraft.client.MinecraftClient;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UserConfig {
    public static boolean ENABLE_HUD_INFORMATION = true;
    public static boolean VIEW_ITEM_OFF_HAND = false;
    public static double NORMAL_GAMMA_VOLUME = 0.5;
    public static double CURRENT_GAMMA_VOLUME = 0.5;
    public static double SELECT_GAMMA_VOLUME = 5.0;
    public static int SELECT_SYSTEM_VOLUME = 1;
    public static boolean ICON_SNAPSHOT = false;
    public static boolean GAMMA_ACTIVATED = false;
    public static String DISCORD_TOKEN = "";
    public static boolean DISCORD_USE = false;
    ///
    public static int CURRENT_MUSIC_VOLUME = 2;
    public static int SELECT_MUSIC_VOLUME = 1;
    public static String LAST_REQUEST_MUSIC = "";
    public static String YANDEX_MUSIC_TOKEN = "";
    public static String YOUTUBE_EMAIL = "";
    public static String YOUTUBE_PASSWORD = "";

    /**
     * Сохранение конфигурации
     */
    public static void save(){
        MinecraftClient mc = MinecraftClient.getInstance();
        final Path configFile = mc.runDirectory.toPath().resolve("KelUtils/config.json");
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("ENABLE_HUD_INFORMATION", ENABLE_HUD_INFORMATION)
                .put("VIEW_ITEM_OFF_HAND", VIEW_ITEM_OFF_HAND)
                .put("CURRENT_GAMMA_VOLUME", CURRENT_GAMMA_VOLUME)
                .put("NORMAL_GAMMA_VOLUME", NORMAL_GAMMA_VOLUME)
                .put("SELECT_GAMMA_VOLUME", SELECT_GAMMA_VOLUME)
                .put("GAMMA_ACTIVATED", GAMMA_ACTIVATED)
                .put("SELECT_SYSTEM_VOLUME", SELECT_SYSTEM_VOLUME)
                .put("ICON_SNAPSHOT", ICON_SNAPSHOT)
                .put("DISCORD_TOKEN", DISCORD_TOKEN)
                .put("DISCORD_USE", DISCORD_USE)
                .put("SELECT_MUSIC_VOLUME", SELECT_MUSIC_VOLUME)
                .put("CURRENT_MUSIC_VOLUME", CURRENT_MUSIC_VOLUME)
                .put("LAST_REQUEST_MUSIC", LAST_REQUEST_MUSIC)
                .put("YANDEX_MUSIC_TOKEN", YANDEX_MUSIC_TOKEN)
                .put("YOUTUBE_EMAIL", YOUTUBE_EMAIL)
                .put("YOUTUBE_PASSWORD", YOUTUBE_PASSWORD);
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
            if(!jsonConfig.isNull("ENABLE_HUD_INFORMATION")) ENABLE_HUD_INFORMATION = jsonConfig.getBoolean("ENABLE_HUD_INFORMATION");
            else ENABLE_HUD_INFORMATION = true;

            if(!jsonConfig.isNull("VIEW_ITEM_OFF_HAND")) VIEW_ITEM_OFF_HAND = jsonConfig.getBoolean("VIEW_ITEM_OFF_HAND");
            else VIEW_ITEM_OFF_HAND = false;

            if(!jsonConfig.isNull("GAMMA_ACTIVATED")) GAMMA_ACTIVATED = jsonConfig.getBoolean("GAMMA_ACTIVATED");
            else GAMMA_ACTIVATED = false;

            if(!jsonConfig.isNull("CURRENT_GAMMA_VOLUME")) CURRENT_GAMMA_VOLUME = jsonConfig.getDouble("CURRENT_GAMMA_VOLUME");
            else CURRENT_GAMMA_VOLUME = 0.5;

            if(!jsonConfig.isNull("NORMAL_GAMMA_VOLUME")) NORMAL_GAMMA_VOLUME = jsonConfig.getDouble("NORMAL_GAMMA_VOLUME");
            else NORMAL_GAMMA_VOLUME = 0.5;

            if(!jsonConfig.isNull("SELECT_GAMMA_VOLUME")) SELECT_GAMMA_VOLUME = jsonConfig.getDouble("SELECT_GAMMA_VOLUME");
            else SELECT_GAMMA_VOLUME = 5.0;

            if(!jsonConfig.isNull("SELECT_SYSTEM_VOLUME")) SELECT_SYSTEM_VOLUME = jsonConfig.getInt("SELECT_SYSTEM_VOLUME");
            else SELECT_SYSTEM_VOLUME = 1;

            if(!jsonConfig.isNull("DISCORD_USE")) DISCORD_USE = jsonConfig.getBoolean("DISCORD_USE");
            else DISCORD_USE = true;

            if(!jsonConfig.isNull("DISCORD_TOKEN")) DISCORD_TOKEN = jsonConfig.getString("DISCORD_TOKEN");
            else DISCORD_TOKEN = "";

            if(!jsonConfig.isNull("ICON_SNAPSHOT")) ICON_SNAPSHOT = jsonConfig.getBoolean("ICON_SNAPSHOT");
            else ICON_SNAPSHOT = false;

            if(!jsonConfig.isNull("CURRENT_MUSIC_VOLUME")) CURRENT_MUSIC_VOLUME = jsonConfig.getInt("CURRENT_MUSIC_VOLUME");
            else CURRENT_MUSIC_VOLUME = 2;

            if(!jsonConfig.isNull("SELECT_MUSIC_VOLUME")) SELECT_MUSIC_VOLUME = jsonConfig.getInt("SELECT_MUSIC_VOLUME");
            else SELECT_MUSIC_VOLUME = 1;


            if(!jsonConfig.isNull("LAST_REQUEST_MUSIC")) LAST_REQUEST_MUSIC = jsonConfig.getString("LAST_REQUEST_MUSIC");
            else LAST_REQUEST_MUSIC = "";
            if(!jsonConfig.isNull("YANDEX_MUSIC_TOKEN")) YANDEX_MUSIC_TOKEN = jsonConfig.getString("YANDEX_MUSIC_TOKEN");
            else YANDEX_MUSIC_TOKEN = "";

            if(!jsonConfig.isNull("YOUTUBE_EMAIL")) YOUTUBE_EMAIL = jsonConfig.getString("YOUTUBE_EMAIL");
            else YOUTUBE_EMAIL = "";
            if(!jsonConfig.isNull("YOUTUBE_PASSWORD")) YOUTUBE_PASSWORD = jsonConfig.getString("YOUTUBE_PASSWORD");
            else YOUTUBE_PASSWORD = "";
        } catch (Exception e){
            e.printStackTrace();
            save();
        }

    }
}
