package ru.simplykel.kelutils.server.discord.listener;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.simplykel.kelutils.server.Main;
import ru.simplykel.kelutils.server.config.DiscordConfig;
import ru.simplykel.kelutils.server.discord.Bot;

public class CommandListener extends ListenerAdapter {
    public static void registerCommands(){
        Bot.jda.updateCommands().addCommands().queue();
//        Bot.jda.updateCommands().addCommands(
//        ).queue();
        DiscordConfig.REGISTER_COMMANDS = false;
        DiscordConfig.save();
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e){
        try {

            switch (e.getName()){
            }
        } catch (Exception ex){
            if(ex instanceof ErrorResponseException) Main.LOG.info("[KelUtils/Bot] The command was executed by another server or the response time passed");
            else ex.printStackTrace();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent e){
        try {
            switch (e.getComponentId()){
            }
        } catch (Exception ex){
            if(ex instanceof ErrorResponseException) Main.LOG.info("[KelUtils/Bot] The command was executed by another server or the response time passed");
            else ex.printStackTrace();
        }
    }
}
