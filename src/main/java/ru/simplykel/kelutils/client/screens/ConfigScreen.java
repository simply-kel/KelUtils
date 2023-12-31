package ru.simplykel.kelutils.client.screens;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import ru.simplykel.kelutils.client.Main;
import ru.simplykel.kelutils.client.config.*;
import ru.simplykel.kelutils.client.discord.Bot;
import ru.simplykel.kelutils.client.info.Window;
import ru.simplykel.kelutils.client.screens.config.*;

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
        if(UserConfig.DISCORD_USE) new DiscordConfigs().getCategory(builder);
        new HUDConfigs().getCategory(builder);
        new F3Configs().getCategory(builder);
        if(!CLIENT.isInSingleplayer() && CLIENT.getCurrentServerEntry() != null) {
            new ServerConfigs().getCategory(builder);
        }
        new LocalizationsConfig().getCategory(builder);
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
        DiscordConfig.save();
        HUDConfig.save();
        F3Config.save();
        try{
            Window.setIcon(CLIENT);
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
