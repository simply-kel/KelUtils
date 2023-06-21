package ru.simplykel.kelutils.info;

import net.minecraft.client.MinecraftClient;
import ru.simplykel.kelutils.config.Localization;

public class World {
    public static String getTime(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        if(CLIENT.world == null) return "";
        long currentTime = CLIENT.world.getLunarTime() % 24000;
        if (currentTime < 6000 && currentTime > 0) {
            return Localization.getLocalization("time.morning", false);
        } else if (currentTime < 12000 && currentTime > 6000) {
            return Localization.getLocalization("time.day", false);
        } else if (currentTime < 16500 && currentTime > 12000) {
            return Localization.getLocalization("time.evening", false);
        } else if (currentTime > 16500) {
            return Localization.getLocalization("time.night", false);
        } else {
            return "";
        }
    }
    public static String getCodeName(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        return CLIENT.player.getWorld().getRegistryKey().getValue().toString();
    }
    public static String getName(){
        String world = getCodeName();
        if(world.equals("minecraft:the_moon")) return Localization.getLocalization("world.moon", false);
        if(world.equals("minecraft:the_end")) return Localization.getLocalization("world.the_end", false);
        if(world.equals("minecraft:the_nether")) return Localization.getLocalization("world.nether", false);
        if(world.equals("minecraft:overworld")) return Localization.getLocalization("world.overworld", false);
        return Localization.getLocalization("world.unknown", false);
    }
}
