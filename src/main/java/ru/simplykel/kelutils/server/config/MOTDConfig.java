package ru.simplykel.kelutils.server.config;

import lombok.Getter;
import net.minecraft.SharedConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.simplykel.kelutils.server.Main;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
public class MOTDConfig {
    // Чтобы можно было менять в 1 месте и не вспоминать где нужно изменить.
    public static final int defaultLineCount = 58;
    public static final Path configFile = Main.server.getRunDirectory().toPath().resolve("KelUtils/motd.json");
    public static String line1 = "KelUtils";
    public static String line2 = "Minecraft " + SharedConstants.getGameVersion().getName();
    public static String day = "Day";
    public static String night = "Night";
    public static String morning = "Morning";
    public static String evening = "Evening";
    public static int lineCount = defaultLineCount;
    public static Boolean useRandomLine2 = false;
    public static JSONArray randomLine2 = new JSONArray().put("Hello, world!").put("Hi!").put("This server used KelUtils");

    public static void load() {
            try {
                JSONObject json = new JSONObject(Files.readString(configFile, StandardCharsets.UTF_8));
                for (String key : json.keySet()) {
                    switch (key) {
                        case "line1" -> line1 = json.getString(key);
                        case "line2" -> line2 = json.getString(key);
                        case "day" -> day = json.getString(key);
                        case "night" -> night = json.getString(key);
                        case "morning" -> morning = json.getString(key);
                        case "evening" -> evening = json.getString(key);
                        case "countLine" -> lineCount = json.getInt(key);
                        case "useRandomLine2" -> useRandomLine2 = json.getBoolean(key);
                        case "randomLine2" -> randomLine2 = json.getJSONArray(key);
                        default -> {}
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                save();
                Main.LOG.info("[KelUtils/MOTD] An error occurred while loading the configuration. Attempting to save configuration");
            }

    }

    public static void save() {
        JSONObject json = new JSONObject();
        json.put("line1", "A Minecraft Server")
                .put("line2", "Minecraft " + SharedConstants.getGameVersion().getName())
                .put("day", "Day")
                .put("night", "Night")
                .put("morning", "Morning")
                .put("evening", "Evening")
                .put("countLine", defaultLineCount)
                .put("useRandomLine2", false)
                .put("randomLine2", new JSONArray().put("Hello, world!").put("Hi!"));
        try {
            Files.createDirectories(configFile.getParent());
            Files.writeString(configFile, json.toString(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
