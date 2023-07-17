package ru.simplykel.kelutils.client.config;

import net.minecraft.client.MinecraftClient;
import org.json.JSONObject;
import ru.simplykel.kelutils.client.Main;
import ru.simplykel.simplystatus.Client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DiscordConfig {
    public static String DISCORD_TOKEN = "";
    public static String USER_ID = "";
    public static Boolean REGISTER_COMMANDS = true;
    public static Boolean DEAD_SCREENSHOTS = true;
    public static Boolean SCREENSHOTS_TO_PM = true;
    /**
     * Сохранение конфигурации
     */
    public static void save(){
        MinecraftClient mc = MinecraftClient.getInstance();
        final Path configFile = mc.runDirectory.toPath().resolve("KelUtils/discord.json");
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("DISCORD_TOKEN", DISCORD_TOKEN)
                .put("USER_ID", USER_ID)
                .put("REGISTER_COMMANDS", REGISTER_COMMANDS)
                .put("DEAD_SCREENSHOTS", DEAD_SCREENSHOTS)
                .put("SCREENSHOTS_TO_PM", SCREENSHOTS_TO_PM);
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
            // REGISTER COMMANDS
            if(!jsonConfig.isNull("REGISTER_COMMANDS")) REGISTER_COMMANDS = jsonConfig.getBoolean("REGISTER_COMMANDS");
            else REGISTER_COMMANDS = true;
            // DEAD SCREENSHOTS
            if(!jsonConfig.isNull("DEAD_SCREENSHOTS")) DEAD_SCREENSHOTS = jsonConfig.getBoolean("DEAD_SCREENSHOTS");
            else DEAD_SCREENSHOTS = true;
            // SCREENSHOTS TO PM
            if(!jsonConfig.isNull("SCREENSHOTS_TO_PM")) SCREENSHOTS_TO_PM = jsonConfig.getBoolean("SCREENSHOTS_TO_PM");
            else SCREENSHOTS_TO_PM = true;
        } catch (Exception e){
            e.printStackTrace();
            save();
        }

    }
}
