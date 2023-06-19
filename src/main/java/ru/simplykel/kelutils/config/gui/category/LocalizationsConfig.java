package ru.simplykel.kelutils.config.gui.category;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import ru.simplykel.kelutils.config.Localization;

public class LocalizationsConfig {
    public ConfigCategory getCategory(ConfigBuilder builder){
        ConfigCategory category = builder.getOrCreateCategory(Localization.getText("kelutils.config.localization"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ///
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.localization.title.information")).build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.hud"),
                        Localization.getLocalization("hud", false))
                .setDefaultValue(Localization.getLcnDefault("hud"))
                .setSaveConsumer(newValue -> Localization.setLocalization("hud", newValue))
                .build());
        ///
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.localization.title.item")).build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.item"),
                        Localization.getLocalization("item", false))
                .setDefaultValue(Localization.getLcnDefault("item"))
                .setSaveConsumer(newValue -> Localization.setLocalization("item", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.item.format"),
                        Localization.getLocalization("item.format", false))
                .setDefaultValue(Localization.getLcnDefault("item.format"))
                .setSaveConsumer(newValue -> Localization.setLocalization("item.format", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.item.format.count"),
                        Localization.getLocalization("item.format.count", false))
                .setDefaultValue(Localization.getLcnDefault("hud"))
                .setSaveConsumer(newValue -> Localization.setLocalization("item.format.count", newValue))
                .build());
        ///
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.localization.title.server")).build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.address.hidden"),
                        Localization.getLocalization("address.hidden", false))
                .setDefaultValue(Localization.getLcnDefault("address.hidden"))
                .setSaveConsumer(newValue -> Localization.setLocalization("address.hidden", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.address"),
                        Localization.getLocalization("address", false))
                .setDefaultValue(Localization.getLcnDefault("address"))
                .setSaveConsumer(newValue -> Localization.setLocalization("address", newValue))
                .build());
        ///
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.localization.title.date")).build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.date.format"),
                        Localization.getLocalization("date.format", false))
                .setDefaultValue(Localization.getLcnDefault("date.format"))
                .setSaveConsumer(newValue -> Localization.setLocalization("date.format", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.date"),
                        Localization.getLocalization("date", false))
                .setDefaultValue(Localization.getLcnDefault("date"))
                .setSaveConsumer(newValue -> Localization.setLocalization("date", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.date.time"),
                        Localization.getLocalization("date.time", false))
                .setDefaultValue(Localization.getLcnDefault("date.time"))
                .setSaveConsumer(newValue -> Localization.setLocalization("date.time", newValue))
                .build());
        ///
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.localization.title.time")).build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.time.morning"),
                        Localization.getLocalization("time.morning", false))
                .setDefaultValue(Localization.getLcnDefault("time.morning"))
                .setSaveConsumer(newValue -> Localization.setLocalization("time.morning", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.time.day"),
                        Localization.getLocalization("time.day", false))
                .setDefaultValue(Localization.getLcnDefault("time.day"))
                .setSaveConsumer(newValue -> Localization.setLocalization("time.day", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.time.evening"),
                        Localization.getLocalization("time.evening", false))
                .setDefaultValue(Localization.getLcnDefault("time.evening"))
                .setSaveConsumer(newValue -> Localization.setLocalization("time.evening", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.time.night"),
                        Localization.getLocalization("time.night", false))
                .setDefaultValue(Localization.getLcnDefault("time.night"))
                .setSaveConsumer(newValue -> Localization.setLocalization("time.night", newValue))
                .build());
        ///
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.localization.title.world")).build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.world.overworld"),
                        Localization.getLocalization("world.overworld", false))
                .setDefaultValue(Localization.getLcnDefault("world.overworld"))
                .setSaveConsumer(newValue -> Localization.setLocalization("world.overworld", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.world.nether"),
                        Localization.getLocalization("world.nether", false))
                .setDefaultValue(Localization.getLcnDefault("world.nether"))
                .setSaveConsumer(newValue -> Localization.setLocalization("world.nether", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.world.the_end"),
                        Localization.getLocalization("world.the_end", false))
                .setDefaultValue(Localization.getLcnDefault("world.the_end"))
                .setSaveConsumer(newValue -> Localization.setLocalization("world.the_end", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.world.moon"),
                        Localization.getLocalization("world.moon", false))
                .setDefaultValue(Localization.getLcnDefault("world.moon"))
                .setSaveConsumer(newValue -> Localization.setLocalization("world.moon", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.world.unknown"),
                        Localization.getLocalization("world.unknown", false))
                .setDefaultValue(Localization.getLcnDefault("world.unknown"))
                .setSaveConsumer(newValue -> Localization.setLocalization("world.unknown", newValue))
                .build());

        return category;
    }
}
