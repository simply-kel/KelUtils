package ru.simplykel.kelutils.client.discord.listener.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import ru.simplykel.kelutils.client.config.Localization;
import ru.simplykel.kelutils.client.discord.Bot;

public class Screenshot {
    public Screenshot(SlashCommandInteractionEvent e){
        Bot.takeScreenshotBot = true;
        e.reply(Localization.getLocalization("bot.screenshot.try", true)).setEphemeral(false).queue();
    }
    public Screenshot(ButtonInteractionEvent e){
        Bot.takeScreenshotBot = true;
        e.reply(Localization.getLocalization("bot.screenshot.try", true)).setEphemeral(false).queue();
    }
}
