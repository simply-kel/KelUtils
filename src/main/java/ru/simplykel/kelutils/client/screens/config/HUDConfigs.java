package ru.simplykel.kelutils.client.screens.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import ru.simplykel.kelutils.client.config.HUDConfig;
import ru.simplykel.kelutils.client.config.Localization;
import ru.simplykel.kelutils.client.config.ServerConfig;

public class HUDConfigs {
    public ConfigCategory getCategory(ConfigBuilder builder){
        ConfigCategory category = builder.getOrCreateCategory(Localization.getText("kelutils.config.hud"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        HUDConfig.load();
        //
        category.addEntry(entryBuilder.startIntField(Localization.getText("kelutils.config.hud.hud"), HUDConfig.HUD)
                .setDefaultValue(2)
                .setSaveConsumer(newValue -> HUDConfig.HUD = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startIntField(Localization.getText("kelutils.config.hud.chat"), HUDConfig.CHAT)
                .setDefaultValue(0)
                .setSaveConsumer(newValue -> HUDConfig.CHAT = newValue)
                .build());
        return category;
    }
}
