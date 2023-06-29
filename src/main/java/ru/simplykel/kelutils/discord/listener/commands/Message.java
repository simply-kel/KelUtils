package ru.simplykel.kelutils.discord.listener.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.discord.Bot;

public class Message {
    public Message(SlashCommandInteractionEvent e){
        String command = e.getOption("text").getAsString();
        MinecraftClient mc = MinecraftClient.getInstance();
        if(mc.player == null && mc.world == null){
            e.reply(Localization.getLocalization("bot.message.fail", true)).setEphemeral(false).queue();
        } else {
            ClientPlayerEntity player = mc.player;
            if (command.startsWith("/")) {
                command = command.substring(1);
                player.networkHandler.sendChatCommand(command);
                e.reply(Localization.getLocalization("bot.message.command", true)).setEphemeral(false).queue();
            } else {
                player.networkHandler.sendChatMessage(command);
                e.reply(Localization.getLocalization("bot.message", true)).setEphemeral(false).queue();
            }
        }
    }
}
