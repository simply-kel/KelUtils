package ru.simplykel.kelutils.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import ru.simplykel.kelutils.Main;
import ru.simplykel.kelutils.config.DiscordConfig;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.discord.listener.*;

import java.awt.*;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class Bot {
    public static JDA jda;
    public static boolean DISCORD_CONNECTED = false;
    public static boolean takeScreenshotBot = false;
    public static void start() throws InterruptedException {
        String token = DiscordConfig.DISCORD_TOKEN;
        jda = JDABuilder.createDefault(token,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.DIRECT_MESSAGES)
                .enableCache(CacheFlag.ACTIVITY)
                .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.SCHEDULED_EVENTS)
                .setActivity(Activity.playing(Localization.getParsedText("Minecraft %version%")))
                .setStatus(OnlineStatus.IDLE)
                .addEventListeners(new StatusListener(), new CommandListener())
                .build();
        jda.awaitReady();
        CommandListener.registerCommands();
    }
    public static void sendScreenshot(InputStream imageData) throws ExecutionException, InterruptedException {
        if(!DISCORD_CONNECTED) return;
        Main.LOG.info(jda.getUserById(DiscordConfig.USER_ID).getName());
        String fileName = Localization.getLocalization("bot.screenshot.file", true)
                .replace(":", ".")
                .replace("/", "-")
                +".png";
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(new Color(0x264653))
                .setDescription(Localization.getLocalization("bot.screenshot", true))
                .setImage("attachment://"+fileName);
        User user = jda.getUserById(DiscordConfig.USER_ID);
        assert user != null;
        user.openPrivateChannel().submit().get()
                .sendMessageEmbeds(embed.build())
                .addFiles(FileUpload.fromData(imageData, fileName))
                .queue();
    }
}
