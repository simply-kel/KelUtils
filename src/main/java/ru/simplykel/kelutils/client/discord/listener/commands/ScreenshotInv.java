package ru.simplykel.kelutils.client.discord.listener.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import ru.simplykel.kelutils.client.config.Localization;
import ru.simplykel.kelutils.client.discord.Bot;

public class ScreenshotInv {
    public ScreenshotInv(SlashCommandInteractionEvent e){
        Bot.takeScreenshotInvBot = true;
        e.reply(Localization.getLocalization("bot.screenshot.try", true)).setEphemeral(false).queue();
    }
    public ScreenshotInv(ButtonInteractionEvent e){
        Bot.takeScreenshotInvBot = true;
        e.reply(Localization.getLocalization("bot.screenshot.try", true)).setEphemeral(false).queue();
    }
}
