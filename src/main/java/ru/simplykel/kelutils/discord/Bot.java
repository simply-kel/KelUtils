package ru.simplykel.kelutils.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
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
    public static boolean lastChangeDiscordUse = false;
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
        if(DiscordConfig.REGISTER_COMMANDS) CommandListener.registerCommands();
    }
    public static void sendScreenshot(InputStream imageData) throws ExecutionException, InterruptedException {
        if(!DISCORD_CONNECTED) return;
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
        Main.LOG.info(jda.getUserById(DiscordConfig.USER_ID).getName());
        user.openPrivateChannel().submit().get()
                .sendMessageEmbeds(embed.build())
                .addFiles(FileUpload.fromData(imageData, fileName))
                .queue();
        if(MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().world != null)
            MinecraftClient.getInstance().player.sendMessage(Localization.getText("kelutils.bot.screenshot.message"), false);
    }
    public static void sendScreenshotDeath(InputStream imageData, Text msg) throws ExecutionException, InterruptedException {
        if(!DISCORD_CONNECTED) return;
        String fileName = Localization.getLocalization("bot.screenshot.file", true)
                .replace(":", ".")
                .replace("/", "-")
                +".png";
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(new Color(0xA7364D))
                .setDescription(Localization.getLocalization("bot.screenshot", true)+"\n"+Localization.getParsedText("> %x% %y% %z%")+"\n"+Localization.toString(msg))
                .setImage("attachment://"+fileName);
        User user = jda.getUserById(DiscordConfig.USER_ID);
        assert user != null;
        Main.LOG.info(jda.getUserById(DiscordConfig.USER_ID).getName());
        user.openPrivateChannel().submit().get()
                .sendMessageEmbeds(embed.build())
                .addFiles(FileUpload.fromData(imageData, fileName))
                .queue();
        if(MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().world != null)
            MinecraftClient.getInstance().player.sendMessage(Localization.getText("kelutils.bot.screenshot.message"), false);
    }
    public static void sendMessageFromGame(Text msg) throws ExecutionException, InterruptedException {
        User user = jda.getUserById(DiscordConfig.USER_ID);
        assert user != null;
        Main.LOG.info(jda.getUserById(DiscordConfig.USER_ID).getName());
        EmbedBuilder embed = new EmbedBuilder().setColor(new Color(0x77DCE3))
                .setFooter(Localization.getLocalization("bot.chat.send", true))
                .setDescription(Localization.toString(msg));
        user.openPrivateChannel().submit().get()
                .sendMessageEmbeds(embed.build())
                .queue();
    }
}
