package ru.simplykel.kelutils.config.gui.category;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.config.ServerConfig;

public class ServerConfigs {
    public ConfigCategory getCategory(ConfigBuilder builder){
        ConfigCategory category = builder.getOrCreateCategory(Localization.getText("kelutils.config.server"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ServerConfig.load();
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.server.show_address"), ServerConfig.SHOW_ADDRESS)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ServerConfig.SHOW_ADDRESS = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.server.show_name_in_list"), ServerConfig.SHOW_NAME_IN_LIST)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> ServerConfig.SHOW_NAME_IN_LIST = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.server.show_custom_name"), ServerConfig.SHOW_CUSTOM_NAME)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ServerConfig.SHOW_CUSTOM_NAME = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startStrField(Localization.getText("kelutils.config.server.custom_name"), ServerConfig.CUSTOM_NAME)
                .setDefaultValue("Custom name")
                .setSaveConsumer(newValue -> ServerConfig.CUSTOM_NAME = newValue)
                .build());
        return category;
    }
}
