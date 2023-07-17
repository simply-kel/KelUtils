package ru.simplykel.kelutils.client.config;

import net.minecraft.client.MinecraftClient;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class F3Config {
    public static String[] hardwareModes = {
            "hide",
            "reduced",
            "normal"
    };
    // MODIFICATIONS
    public static boolean DISABLE_IRIS = true;
    public static boolean DISABLE_SODIUM = true;
    public static boolean DISABLE_IMMEDIATELYFAST = true;
    public static boolean DISABLE_LITEMATICA = true;
    public static boolean DISABLE_FABRIC = true;
    public static boolean DISABLE_ENTITY_CULLING = true;
    public static boolean DISABLE_VIA_FABRIC = true;
    // VANILLA
    public static String HARDWARE_MODE = hardwareModes[1];
    public static boolean DISABLE_TAGS = true;
    public static boolean DISABLE_TARGET_BLOCK = true;
    public static boolean DISABLE_ACTIVE_RENDERER= true;
    public static boolean DISABLE_DEBUG_HINTS = true;
    public static boolean DISABLE_HELP_SHORTCUT= true;
    public static boolean DISABLE_DISTANT_HORIZONS = true;
    public static boolean SHY_FLUIDS  = true;


    /**
     * Сохранение конфигурации
     */
    public static void save(){
        MinecraftClient mc = MinecraftClient.getInstance();
        final Path configFile = mc.runDirectory.toPath().resolve("KelUtils/f3.json");
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("DISABLE_IRIS", DISABLE_IRIS)
                .put("DISABLE_SODIUM", DISABLE_SODIUM)
                .put("DISABLE_IMMEDIATELYFAST", DISABLE_IMMEDIATELYFAST)
                .put("DISABLE_LITEMATICA", DISABLE_LITEMATICA)
                .put("DISABLE_ENTITY_CULLING", DISABLE_ENTITY_CULLING)
                .put("DISABLE_VIA_FABRIC", DISABLE_VIA_FABRIC)
                .put("DISABLE_FABRIC", DISABLE_FABRIC)

                .put("HARDWARE_MODE", HARDWARE_MODE)
                .put("DISABLE_TAGS", DISABLE_TAGS)
                .put("DISABLE_ACTIVE_RENDERER", DISABLE_ACTIVE_RENDERER)
                .put("DISABLE_TARGET_BLOCK", DISABLE_TARGET_BLOCK)
                .put("DISABLE_DEBUG_HINTS", DISABLE_DEBUG_HINTS)
                .put("DISABLE_HELP_SHORTCUT", DISABLE_HELP_SHORTCUT)
                .put("DISABLE_DISTANT_HORIZONS", DISABLE_DISTANT_HORIZONS)
                .put("SHY_FLUIDS", SHY_FLUIDS);
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
        final Path configFile = mc.runDirectory.toPath().resolve("KelUtils/f3.json");
        try{
            JSONObject jsonConfig = new JSONObject(Files.readString(configFile));

            if(!jsonConfig.isNull("HARDWARE_MODE")) HARDWARE_MODE = jsonConfig.getString("HARDWARE_MODE");
            else HARDWARE_MODE = hardwareModes[1];
            if(!jsonConfig.isNull("DISABLE_TAGS")) DISABLE_TAGS = jsonConfig.getBoolean("DISABLE_TAGS");
            else DISABLE_TAGS = true;
            if(!jsonConfig.isNull("DISABLE_ACTIVE_RENDERER")) DISABLE_ACTIVE_RENDERER = jsonConfig.getBoolean("DISABLE_ACTIVE_RENDERER");
            else DISABLE_ACTIVE_RENDERER = true;
            if(!jsonConfig.isNull("DISABLE_DEBUG_HINTS")) DISABLE_DEBUG_HINTS = jsonConfig.getBoolean("DISABLE_DEBUG_HINTS");
            else DISABLE_DEBUG_HINTS = true;
            if(!jsonConfig.isNull("DISABLE_HELP_SHORTCUT")) DISABLE_HELP_SHORTCUT = jsonConfig.getBoolean("DISABLE_HELP_SHORTCUT");
            else DISABLE_HELP_SHORTCUT = true;
            if(!jsonConfig.isNull("DISABLE_DISTANT_HORIZONS")) DISABLE_DISTANT_HORIZONS = jsonConfig.getBoolean("DISABLE_DISTANT_HORIZONS");
            else DISABLE_DISTANT_HORIZONS = true;
            if(!jsonConfig.isNull("SHY_FLUIDS")) SHY_FLUIDS = jsonConfig.getBoolean("SHY_FLUIDS");
            else SHY_FLUIDS = true;
            if(!jsonConfig.isNull("DISABLE_TARGET_BLOCK")) DISABLE_TARGET_BLOCK = jsonConfig.getBoolean("DISABLE_TARGET_BLOCK");
            else DISABLE_TARGET_BLOCK = true;
            // MODS
            if(!jsonConfig.isNull("DISABLE_IRIS")) DISABLE_IRIS = jsonConfig.getBoolean("DISABLE_IRIS");
            else DISABLE_IRIS = true;
            if(!jsonConfig.isNull("DISABLE_SODIUM")) DISABLE_SODIUM = jsonConfig.getBoolean("DISABLE_SODIUM");
            else DISABLE_SODIUM = true;
            if(!jsonConfig.isNull("DISABLE_IMMEDIATELYFAST")) DISABLE_IMMEDIATELYFAST = jsonConfig.getBoolean("DISABLE_IMMEDIATELYFAST");
            else DISABLE_IMMEDIATELYFAST = true;
            if(!jsonConfig.isNull("DISABLE_LITEMATICA")) DISABLE_LITEMATICA = jsonConfig.getBoolean("DISABLE_LITEMATICA");
            else DISABLE_LITEMATICA = true;
            if(!jsonConfig.isNull("DISABLE_ENTITY_CULLING")) DISABLE_ENTITY_CULLING = jsonConfig.getBoolean("DISABLE_ENTITY_CULLING");
            else DISABLE_ENTITY_CULLING = true;
            if(!jsonConfig.isNull("DISABLE_VIA_FABRIC")) DISABLE_VIA_FABRIC = jsonConfig.getBoolean("DISABLE_VIA_FABRIC");
            else DISABLE_VIA_FABRIC = true;
            if(!jsonConfig.isNull("DISABLE_IRIS")) DISABLE_IRIS = jsonConfig.getBoolean("DISABLE_FABRIC");
            else DISABLE_FABRIC = true;
        } catch (Exception e){
            e.printStackTrace();
            save();
        }

    }
}
