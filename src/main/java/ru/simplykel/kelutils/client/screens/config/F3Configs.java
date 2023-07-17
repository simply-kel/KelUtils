package ru.simplykel.kelutils.client.screens.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import ru.simplykel.kelutils.client.config.F3Config;
import ru.simplykel.kelutils.client.config.HUDConfig;
import ru.simplykel.kelutils.client.config.Localization;

public class F3Configs {
    public ConfigCategory getCategory(ConfigBuilder builder){
        ConfigCategory category = builder.getOrCreateCategory(Localization.getText("kelutils.config.f3"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        F3Config.load();
        //
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.f3.title.vanilla")).build());
        //
        category.addEntry(entryBuilder.startSelector(Localization.getText("kelutils.config.f3.hardware_mode"), F3Config.hardwareModes, F3Config.HARDWARE_MODE)
                .setDefaultValue(F3Config.hardwareModes[1])
                .setSaveConsumer(newValue -> F3Config.HARDWARE_MODE = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.disable_target_block"), F3Config.DISABLE_TARGET_BLOCK)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.DISABLE_TARGET_BLOCK = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.disable_tags"), F3Config.DISABLE_TAGS)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.DISABLE_TAGS = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.disable_active_renderer"), F3Config.DISABLE_ACTIVE_RENDERER)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.DISABLE_ACTIVE_RENDERER = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.disable_debug_hints"), F3Config.DISABLE_DEBUG_HINTS)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.DISABLE_DEBUG_HINTS = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.disable_help_shortcut"), F3Config.DISABLE_HELP_SHORTCUT)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.DISABLE_HELP_SHORTCUT = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.disable_distant_horizons"), F3Config.DISABLE_DISTANT_HORIZONS)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.DISABLE_DISTANT_HORIZONS = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.shy_fluids"), F3Config.SHY_FLUIDS)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.SHY_FLUIDS = newValue)
                .build());
        ///
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.f3.title.modifications")).build());
        //
        if(FabricLoader.getInstance().getModContainer("iris").isPresent()) category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.disable_iris"), F3Config.DISABLE_IRIS)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.DISABLE_IRIS = newValue)
                .build());
        //
        if(FabricLoader.getInstance().getModContainer("sodium").isPresent()) category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.disable_sodium"), F3Config.DISABLE_SODIUM)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.DISABLE_SODIUM = newValue)
                .build());
        //
        if(FabricLoader.getInstance().getModContainer("immediatelyfast").isPresent()) category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.disable_immediatelyfast"), F3Config.DISABLE_IMMEDIATELYFAST)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.DISABLE_IMMEDIATELYFAST = newValue)
                .build());
        //
        if(FabricLoader.getInstance().getModContainer("litematica").isPresent()) category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.disable_litematica"), F3Config.DISABLE_LITEMATICA)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.DISABLE_LITEMATICA = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.disable_fabric"), F3Config.DISABLE_FABRIC)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.DISABLE_IRIS = newValue)
                .build());
        //
        if(FabricLoader.getInstance().getModContainer("entityculling").isPresent()) category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.disable_entity_culling"), F3Config.DISABLE_ENTITY_CULLING)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.DISABLE_ENTITY_CULLING = newValue)
                .build());
        //
        if(FabricLoader.getInstance().getModContainer("viafabric").isPresent()) category.addEntry(entryBuilder.startBooleanToggle(Localization.getText("kelutils.config.f3.disable_via_fabric"), F3Config.DISABLE_VIA_FABRIC)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> F3Config.DISABLE_VIA_FABRIC = newValue)
                .build());
        return category;
    }
}
