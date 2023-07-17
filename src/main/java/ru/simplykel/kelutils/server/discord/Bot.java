package ru.simplykel.kelutils.server.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import ru.simplykel.kelutils.server.config.DiscordConfig;
import ru.simplykel.kelutils.server.config.Localization;
import ru.simplykel.kelutils.server.discord.listener.CommandListener;
import ru.simplykel.kelutils.server.discord.listener.StatusListener;

public class Bot {
    public static JDA jda;
    public static boolean DISCORD_CONNECTED = false;
    public static void start() throws InterruptedException {
        String token = DiscordConfig.DISCORD_TOKEN;
        jda = JDABuilder.createDefault(token,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.DIRECT_MESSAGES)
                .enableCache(CacheFlag.ACTIVITY)
                .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.SCHEDULED_EVENTS)
                .setActivity(Activity.playing(Localization.getParsedText("Minecraft %version%", true)))
                .setStatus(OnlineStatus.IDLE)
                .addEventListeners(new StatusListener(), new CommandListener())
                .build();
        jda.awaitReady();
        if(DiscordConfig.REGISTER_COMMANDS) CommandListener.registerCommands();
    }
}
