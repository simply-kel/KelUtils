package ru.simplykel.kelutils.lavaplayer;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import ru.simplykel.kelutils.Main;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.config.UserConfig;

public class MusicScreen {
    public static Screen buildScreen (Screen currentScreen) {
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        UserConfig.load();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(currentScreen)
                .setTitle(Localization.getText("kelutils.name"))
                .setTransparentBackground(true)
                .setSavingRunnable(MusicScreen::save);
        ConfigCategory category = builder.getOrCreateCategory(Localization.getText("kelutils.music"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.music.url"),
                        "")
                .setDefaultValue("")
                .setSaveConsumer(newValue -> UserConfig.LAST_REQUEST_MUSIC = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.music.url.copy"),
                        UserConfig.LAST_REQUEST_MUSIC)
                .setDefaultValue(UserConfig.LAST_REQUEST_MUSIC)
                .build());
        return builder.build();
    }
    private static void save(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        UserConfig.save();
        if(!UserConfig.LAST_REQUEST_MUSIC.isBlank()){
            Main.music.getTrackSearch().getTracks(UserConfig.LAST_REQUEST_MUSIC);
            if(CLIENT.player != null) CLIENT.player.sendMessage(Localization.getText("kelutils.music.add"));
        }else if(CLIENT.player != null) CLIENT.player.sendMessage(Localization.getText("kelutils.music.add.blank"));
    }
}
