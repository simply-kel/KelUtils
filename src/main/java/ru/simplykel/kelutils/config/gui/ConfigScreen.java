package ru.simplykel.kelutils.config.gui;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Icons;
import ru.simplykel.kelutils.Main;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.config.ServerConfig;
import ru.simplykel.kelutils.config.UserConfig;
import ru.simplykel.kelutils.config.gui.category.*;
import ru.simplykel.kelutils.discord.Bot;

public class ConfigScreen {
    public static Screen buildScreen (Screen currentScreen) {
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        UserConfig.load();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(currentScreen)
                .setTitle(Localization.getText("kelutils.name"))
                .setTransparentBackground(true)
                .setSavingRunnable(ConfigScreen::save);
        new MainConfigs().getCategory(builder);
        new LocalizationsConfig().getCategory(builder);
        if(!CLIENT.isInSingleplayer() && CLIENT.getCurrentServerEntry() != null) {
            new ServerConfigs().getCategory(builder);
        }
        return builder.build();
    }
    private static void save(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        Main.LOG.info("[KelUtils] Save user configs...");
        if(Bot.lastChangeDiscordUse != UserConfig.DISCORD_USE){
            if(UserConfig.DISCORD_USE) {
                try {
                    Main.startDiscord();
                } catch (Exception e){
                    e.printStackTrace();
                    UserConfig.DISCORD_USE = false;
                }
            }
            else if(Bot.DISCORD_CONNECTED || Bot.jda != null) Bot.jda.shutdown();
            else Main.LOG.info("Discord bot off");
            Bot.lastChangeDiscordUse = UserConfig.DISCORD_USE;
        }
        UserConfig.save();
        try{
            MinecraftClient.getInstance().getWindow().setIcon(MinecraftClient.getInstance().getDefaultResourcePack(), UserConfig.ICON_SNAPSHOT ? Icons.SNAPSHOT : Icons.RELEASE);
        } catch (Exception e){
            e.printStackTrace();
        }
        CLIENT.options.getGamma().setValue(UserConfig.CURRENT_GAMMA_VOLUME);
        if(!CLIENT.isInSingleplayer() && CLIENT.getCurrentServerEntry() != null){
            Main.LOG.info("[KelUtils] Save server configs...");
            ServerConfig.save();
        }
    }
}
