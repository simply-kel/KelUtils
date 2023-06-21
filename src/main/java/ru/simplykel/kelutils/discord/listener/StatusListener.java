package ru.simplykel.kelutils.discord.listener;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.SessionDisconnectEvent;
import net.dv8tion.jda.api.events.session.SessionRecreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.simplykel.kelutils.Main;
import ru.simplykel.kelutils.discord.Bot;

public class StatusListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent e){
        Main.LOG.info("[KelUtils] Bot started!");
        Bot.DISCORD_CONNECTED = true;
    }
    @Override
    public void onSessionDisconnect(SessionDisconnectEvent e){
        Main.LOG.info("[KelUtils] Bot disconnected!");
        Bot.DISCORD_CONNECTED = false;
    }
    @Override
    public void onSessionRecreate(SessionRecreateEvent e){
        Main.LOG.info("[KelUtils] Bot started!");
        Bot.DISCORD_CONNECTED = true;
    }

}
