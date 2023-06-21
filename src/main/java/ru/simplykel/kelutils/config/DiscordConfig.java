package ru.simplykel.kelutils.config;

import net.minecraft.client.MinecraftClient;
import org.json.JSONObject;
import ru.simplykel.kelutils.Main;
import ru.simplykel.simplystatus.Client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DiscordConfig {
    public static String DISCORD_TOKEN = "";
    public static String USER_ID = "";
    /**
     * Сохранение конфигурации
     */
    public static void save(){
        MinecraftClient mc = MinecraftClient.getInstance();
        final Path configFile = mc.runDirectory.toPath().resolve("KelUtils/discord.json");
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("DISCORD_TOKEN", DISCORD_TOKEN)
                .put("USER_ID", USER_ID);
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
            // USER ID
            if(!jsonConfig.isNull("USER_ID")) USER_ID = jsonConfig.getString("USER_ID");
            else if(Main.simplyStatus) USER_ID = Client.USER.userId;
            else USER_ID = "";
        } catch (Exception e){
            e.printStackTrace();
            save();
        }

    }
}
