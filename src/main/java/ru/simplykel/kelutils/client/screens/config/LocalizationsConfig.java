package ru.simplykel.kelutils.client.screens.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import ru.simplykel.kelutils.client.config.Localization;

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
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.localization.title.bossbar")).build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.bossbar"),
                        Localization.getLocalization("bossbar", false))
                .setDefaultValue(Localization.getLcnDefault("bossbar"))
                .setSaveConsumer(newValue -> Localization.setLocalization("bossbar", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.bossbar.music"),
                        Localization.getLocalization("bossbar.music", false))
                .setDefaultValue(Localization.getLcnDefault("bossbar.music"))
                .setSaveConsumer(newValue -> Localization.setLocalization("bossbar.music", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.bossbar.music.pause"),
                        Localization.getLocalization("bossbar.music.pause", false))
                .setDefaultValue(Localization.getLcnDefault("bossbar.music.pause"))
                .setSaveConsumer(newValue -> Localization.setLocalization("bossbar.music.pause", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.bossbar.music.live"),
                        Localization.getLocalization("bossbar.music.live", false))
                .setDefaultValue(Localization.getLcnDefault("bossbar.music.live"))
                .setSaveConsumer(newValue -> Localization.setLocalization("bossbar.music.live", newValue))
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
                .setDefaultValue(Localization.getLcnDefault("item.format.count"))
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
        ///
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.localization.title.music")).build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.music.format"),
                        Localization.getLocalization("music.format", false))
                .setDefaultValue(Localization.getLcnDefault("music.format"))
                .setSaveConsumer(newValue -> Localization.setLocalization("music.format", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.music.format.author"),
                        Localization.getLocalization("music.format.author", false))
                .setDefaultValue(Localization.getLcnDefault("music.format.author"))
                .setSaveConsumer(newValue -> Localization.setLocalization("music.format.author", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.music.pause"),
                        Localization.getLocalization("music.pause", false))
                .setDefaultValue(Localization.getLcnDefault("music.pause"))
                .setSaveConsumer(newValue -> Localization.setLocalization("music.pause", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.music.time.format"),
                        Localization.getLocalization("music.time.format", false))
                .setDefaultValue(Localization.getLcnDefault("music.time.format"))
                .setSaveConsumer(newValue -> Localization.setLocalization("music.time.format", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.music.live"),
                        Localization.getLocalization("music.live", false))
                .setDefaultValue(Localization.getLcnDefault("music.live"))
                .setSaveConsumer(newValue -> Localization.setLocalization("music.live", newValue))
                .build());
        ///
        category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.config.localization.title.window")).build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.menu"),
                        Localization.getLocalization("title.menu", false))
                .setDefaultValue(Localization.getLcnDefault("title.menu"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.menu", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.menu.loading"),
                        Localization.getLocalization("title.menu.loading", false))
                .setDefaultValue(Localization.getLcnDefault("title.menu.loading"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.menu.loading", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.menu.connect"),
                        Localization.getLocalization("title.menu.connect", false))
                .setDefaultValue(Localization.getLcnDefault("title.menu.connect"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.menu.connect", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.menu.disconnect"),
                        Localization.getLocalization("title.menu.disconnect", false))
                .setDefaultValue(Localization.getLcnDefault("title.menu.disconnect"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.menu.disconnect", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.menu.options"),
                        Localization.getLocalization("title.menu.options", false))
                .setDefaultValue(Localization.getLcnDefault("title.menu.options"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.menu.options", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.menu.multiplayer"),
                        Localization.getLocalization("title.menu.multiplayer", false))
                .setDefaultValue(Localization.getLcnDefault("title.menu.multiplayer"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.menu.multiplayer", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.menu.multiplayer.add"),
                        Localization.getLocalization("title.menu.multiplayer.add", false))
                .setDefaultValue(Localization.getLcnDefault("title.menu.multiplayer.add"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.menu.multiplayer.add", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.menu.world"),
                        Localization.getLocalization("title.menu.world", false))
                .setDefaultValue(Localization.getLcnDefault("title.menu.world"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.menu.world", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.menu.world.create"),
                        Localization.getLocalization("title.menu.world.create", false))
                .setDefaultValue(Localization.getLcnDefault("title.menu.world.create"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.menu.world.create", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.menu.world.edit"),
                        Localization.getLocalization("title.menu.world.edit", false))
                .setDefaultValue(Localization.getLcnDefault("title.menu.world.edit"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.menu.world.edit", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.menu.world.optimize"),
                        Localization.getLocalization("title.menu.world.optimize", false))
                .setDefaultValue(Localization.getLcnDefault("title.menu.world.optimize"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.menu.world.optimize", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.multiplayer"),
                        Localization.getLocalization("title.multiplayer", false))
                .setDefaultValue(Localization.getLcnDefault("title.multiplayer"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.multiplayer", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.singleplayer"),
                        Localization.getLocalization("title.singleplayer", false))
                .setDefaultValue(Localization.getLcnDefault("title.singleplayer"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.singleplayer", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.replaymod"),
                        Localization.getLocalization("title.replaymod", false))
                .setDefaultValue(Localization.getLcnDefault("title.replaymod"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.replaymod", newValue))
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.config.localization.window.unknown"),
                        Localization.getLocalization("title.unknown", false))
                .setDefaultValue(Localization.getLcnDefault("title.unknown"))
                .setSaveConsumer(newValue -> Localization.setLocalization("title.unknown", newValue))
                .build());
        return category;
    }
}
