package ru.simplykel.kelutils.discord.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import ru.simplykel.kelutils.Main;
import ru.simplykel.kelutils.config.DiscordConfig;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.discord.Bot;
import ru.simplykel.kelutils.discord.listener.commands.*;

import java.awt.*;

public class CommandListener extends ListenerAdapter {
    public static void registerCommands(){
        Bot.jda.updateCommands().addCommands().queue();
        Bot.jda.updateCommands().addCommands(
                Commands.slash("screenshot", "Take screenshot").setGuildOnly(false),
                Commands.slash("info", "Get information").setGuildOnly(false),
                Commands.slash("msg", "Send message/command").setGuildOnly(false)
                        .addOption(OptionType.STRING, "text", "Message/command", true),
                Commands.slash("exit", "Exit game").setGuildOnly(false)
        ).queue();
        DiscordConfig.REGISTER_COMMANDS = false;
        DiscordConfig.save();
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e){
        try {
            if(!e.getUser().getId().equals(DiscordConfig.USER_ID)) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0xdd2d4a))
                        .setDescription(Localization.getLocalization("bot.permission", true));
                e.replyEmbeds(embed.build()).setEphemeral(true).queue();
                return;
            }

            switch (e.getName()){
                case "screenshot" -> new Screenshot(e);
                case "info" -> new Information(e);
                case "msg" -> new Message(e);
                case "exit" -> new Exit(e);
            }
        } catch (Exception ex){
            if(ex instanceof ErrorResponseException) Main.LOG.info("[KelUtils/Bot] The command was executed by another client or the response time passed");
            else ex.printStackTrace();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent e){
        try {
            if(!e.getUser().getId().equals(DiscordConfig.USER_ID)) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0xdd2d4a))
                        .setDescription(Localization.getLocalization("bot.permission", true));
                e.replyEmbeds(embed.build()).setEphemeral(true).queue();
                return;
            }

            switch (e.getComponentId()){
                case "screenshot" -> new Screenshot(e);
                case "exit" -> new Exit(e);
                case "upvolume" -> Volume.upVolume(e);
                case "downvolume" -> Volume.downVolume(e);
            }
        } catch (Exception ex){
            if(ex instanceof ErrorResponseException) Main.LOG.info("[KelUtils/Bot] The command was executed by another client or the response time passed");
            else ex.printStackTrace();
        }
    }
}
