package ru.simplykel.kelutils.client.screens.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import ru.simplykel.kelutils.client.Main;
import ru.simplykel.kelutils.client.config.Localization;
import ru.simplykel.kelutils.client.config.UserConfig;

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
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.client.enable_bossbar_information"), UserConfig.ENABLE_BOSSBAR_INFORMATION)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> UserConfig.ENABLE_BOSSBAR_INFORMATION = newValue)
                .build());
        category.addEntry(entryBuilder.startSelector(Localization.getText("kelutils.config.client.bossbar_type"), Main.bossBarTypes, UserConfig.BOSSBAR_TYPE)
                .setDefaultValue(Main.bossBarTypes[0])
                .setSaveConsumer(newVolume -> UserConfig.BOSSBAR_TYPE = newVolume)
                .build());
        //
        category.addEntry(entryBuilder.startIntField(Localization.getText("kelutils.config.client.time_zone"), UserConfig.TIME_ZONE)
                .setDefaultValue(3)
                .setMin(-12)
                .setMax(14)
                .setSaveConsumer(newValue -> UserConfig.TIME_ZONE = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.client.use_custom_title"), UserConfig.USE_CUSTOM_TITLE)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> UserConfig.USE_CUSTOM_TITLE = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.client.discord_use"), UserConfig.DISCORD_USE)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> UserConfig.DISCORD_USE = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startStrField(Localization.getText("kelutils.config.client.all_items_count"), UserConfig.ALL_ITEMS_COUNT)
                .setDefaultValue("minecraft:stone")
                .setSaveConsumer(newValue -> UserConfig.ALL_ITEMS_COUNT = newValue)
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
        ///
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.title.system")).build());
        //
        category.addEntry(entryBuilder.startIntField(Localization.getText("kelutils.config.client.current_system_volume"), UserConfig.SELECT_SYSTEM_VOLUME)
                .setDefaultValue(1)
                .setSaveConsumer(newValue -> UserConfig.SELECT_SYSTEM_VOLUME = newValue)
                .build());
        //
        if(Main.is120Update) {
            category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.client.icon_snapshot"), UserConfig.ICON_SNAPSHOT)
                    .setDefaultValue(false)
                    .setSaveConsumer(newValue -> UserConfig.ICON_SNAPSHOT = newValue)
                    .build());
        }

        return category;
    }
}
