package ru.simplykel.kelutils.config.gui.category;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.config.UserConfig;

public class MainConfigs {
    public ConfigCategory getCategory(ConfigBuilder builder){
        ConfigCategory category = builder.getOrCreateCategory(Localization.getText("kelutils.config.client"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ///
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.title.functions")).build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.client.enable_hud_information"), UserConfig.ENABLE_HUD_INFORMATION)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> UserConfig.ENABLE_HUD_INFORMATION = newValue)
                .build());
        ///
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.title.gamma")).build());
        //
        category.addEntry(entryBuilder.startDoubleField(Localization.getText("kelutils.config.client.normal_gamma_volume"), UserConfig.NORMAL_GAMMA_VOLUME)
                .setDefaultValue(0.5)
                .setSaveConsumer(newValue -> UserConfig.NORMAL_GAMMA_VOLUME = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startDoubleField(Localization.getText("kelutils.config.client.current_gamma_volume"), UserConfig.CURRENT_GAMMA_VOLUME)
                .setDefaultValue(0.5)
                .setSaveConsumer(newValue -> UserConfig.CURRENT_GAMMA_VOLUME = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startDoubleField(Localization.getText("kelutils.config.client.select_gamma_volume"), UserConfig.SELECT_GAMMA_VOLUME)
                .setDefaultValue(5.0)
                .setSaveConsumer(newValue -> UserConfig.SELECT_GAMMA_VOLUME = newValue)
                .build());

        return category;
    }
}