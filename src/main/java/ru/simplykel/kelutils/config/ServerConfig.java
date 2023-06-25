package ru.simplykel.kelutils.config;

import net.minecraft.client.MinecraftClient;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServerConfig {
    public static boolean SHOW_ADDRESS = false;
    public static boolean SHOW_NAME_IN_LIST = true;
    public static boolean SHOW_CUSTOM_NAME = false;
    public static boolean SEND_CHAT_TO_PM = false;
    public static String CUSTOM_NAME = "Custom name";

    /**
     * Сохранение конфигурации
     */
    public static void save(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        final Path configFile = CLIENT.runDirectory.toPath().resolve("KelUtils/servers/"+CLIENT.getCurrentServerEntry().address.replace(":", "-")+".json");
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("SHOW_ADDRESS", SHOW_ADDRESS)
                .put("SHOW_NAME_IN_LIST", SHOW_NAME_IN_LIST)
                .put("SHOW_CUSTOM_NAME", SHOW_CUSTOM_NAME)
                .put("CUSTOM_NAME", CUSTOM_NAME)
                .put("SEND_CHAT_TO_PM", SEND_CHAT_TO_PM);
        try {
            Files.createDirectories(configFile.getParent());
            Files.writeString(configFile, jsonConfig.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void clear(){
        SHOW_ADDRESS = false;
        SHOW_CUSTOM_NAME = false;
        SHOW_NAME_IN_LIST = true;
        SEND_CHAT_TO_PM = false;
        CUSTOM_NAME = "Custom name";
    }
    /**
     * Загрузка файла конфигов
     */
    public static void load(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        final Path configFile = CLIENT.runDirectory.toPath().resolve("KelUtils/servers/"+CLIENT.getCurrentServerEntry().address.replace(":", "-")+".json");
        try{
            JSONObject jsonConfig = new JSONObject(Files.readString(configFile));
            if(!jsonConfig.isNull("SHOW_ADDRESS")) SHOW_ADDRESS = jsonConfig.getBoolean("SHOW_ADDRESS");
            else SHOW_ADDRESS = false;
            if(!jsonConfig.isNull("SHOW_NAME_IN_LIST")) SHOW_NAME_IN_LIST = jsonConfig.getBoolean("SHOW_NAME_IN_LIST");
            else SHOW_NAME_IN_LIST = true;
            if(!jsonConfig.isNull("SHOW_CUSTOM_NAME")) SHOW_CUSTOM_NAME = jsonConfig.getBoolean("SHOW_CUSTOM_NAME");
            else SHOW_CUSTOM_NAME = false;
            if(!jsonConfig.isNull("CUSTOM_NAME")) CUSTOM_NAME = jsonConfig.getString("CUSTOM_NAME");
            else CUSTOM_NAME = "";
            if(!jsonConfig.isNull("SEND_CHAT_TO_PM")) SEND_CHAT_TO_PM = jsonConfig.getBoolean("SEND_CHAT_TO_PM");
            else SEND_CHAT_TO_PM = false;
        } catch (Exception e){
            e.printStackTrace();
            clear();
            save();
        }

    }
}
