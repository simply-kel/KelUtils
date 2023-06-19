package ru.simplykel.kelutils.config.gui;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import ru.simplykel.kelutils.Main;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.config.ServerConfig;
import ru.simplykel.kelutils.config.UserConfig;
import ru.simplykel.kelutils.config.gui.category.*;

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
        UserConfig.save();
        CLIENT.options.getGamma().setValue(UserConfig.CURRENT_GAMMA_VOLUME);
        if(!CLIENT.isInSingleplayer() && CLIENT.getCurrentServerEntry() != null){
            Main.LOG.info("[KelUtils] Save server configs...");
            ServerConfig.save();
        }
    }
}
