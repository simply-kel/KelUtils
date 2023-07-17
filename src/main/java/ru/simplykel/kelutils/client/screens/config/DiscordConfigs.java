package ru.simplykel.kelutils.client.screens.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import ru.simplykel.kelutils.client.config.DiscordConfig;
import ru.simplykel.kelutils.client.config.Localization;
import ru.simplykel.kelutils.client.config.ServerConfig;

public class DiscordConfigs {
    public ConfigCategory getCategory(ConfigBuilder builder){
        ConfigCategory category = builder.getOrCreateCategory(Localization.getText("kelutils.config.discord"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.discord.register_commands"), DiscordConfig.REGISTER_COMMANDS)
                .setTooltip(Localization.getText("kelutils.config.discord.register_commands.tooltip"))
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> DiscordConfig.REGISTER_COMMANDS = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.discord.screenshots_to_pm"), DiscordConfig.SCREENSHOTS_TO_PM)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> DiscordConfig.SCREENSHOTS_TO_PM = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.discord.dead_screenshots"), DiscordConfig.DEAD_SCREENSHOTS)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> DiscordConfig.DEAD_SCREENSHOTS = newValue)
                .build());
        return category;
    }
}
