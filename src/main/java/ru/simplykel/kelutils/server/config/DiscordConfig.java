package ru.simplykel.kelutils.server.config;

import net.minecraft.client.MinecraftClient;
import org.json.JSONObject;
import ru.simplykel.kelutils.client.Main;
import ru.simplykel.simplystatus.Client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DiscordConfig {
    public static String DISCORD_TOKEN = "";
    public static String CHANNEL_ID = "";
    public static Boolean REGISTER_COMMANDS = true;
    /**
     * Сохранение конфигурации
     */
    public static void save(){
        MinecraftClient mc = MinecraftClient.getInstance();
        final Path configFile = mc.runDirectory.toPath().resolve("KelUtils/discord.json");
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("DISCORD_TOKEN", DISCORD_TOKEN)
                .put("CHANNEL_ID", CHANNEL_ID)
                .put("REGISTER_COMMANDS", REGISTER_COMMANDS);
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
        final Path configFile = mc.runDirectory.toPath().resolve("KelUtils/discord.json");
        try{
            JSONObject jsonConfig = new JSONObject(Files.readString(configFile));
            // DISCORD TOKEN
            if(!jsonConfig.isNull("DISCORD_TOKEN")) DISCORD_TOKEN = jsonConfig.getString("DISCORD_TOKEN");
            else DISCORD_TOKEN = "";
            // CHANNEL ID
            if(!jsonConfig.isNull("CHANNEL_ID")) CHANNEL_ID = jsonConfig.getString("CHANNEL_ID");
            else CHANNEL_ID = "";
            // REGISTER COMMANDS
            if(!jsonConfig.isNull("REGISTER_COMMANDS")) REGISTER_COMMANDS = jsonConfig.getBoolean("REGISTER_COMMANDS");
            else REGISTER_COMMANDS = true;
        } catch (Exception e){
            e.printStackTrace();
            save();
        }

    }
}
