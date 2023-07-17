package ru.simplykel.kelutils.client.discord.listener.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.minecraft.client.MinecraftClient;
import ru.simplykel.kelutils.client.config.Localization;

public class Exit {
    public Exit(SlashCommandInteractionEvent e){
        e.reply(Localization.getLocalization("bot.exit", true)).setEphemeral(false).queue();
        MinecraftClient.getInstance().stop();
    }
    public Exit(ButtonInteractionEvent e){
        e.reply(Localization.getLocalization("bot.exit", true)).setEphemeral(false).queue();
        MinecraftClient.getInstance().stop();
    }
}
