package ru.simplykel.kelutils.discord.listener.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.discord.Bot;

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
