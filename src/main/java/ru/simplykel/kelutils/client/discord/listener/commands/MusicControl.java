package ru.simplykel.kelutils.client.discord.listener.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import ru.simplykel.kelutils.client.Main;
import ru.simplykel.kelutils.client.config.Localization;

import java.awt.*;

public class MusicControl {
    public MusicControl(SlashCommandInteractionEvent e){
        EmbedBuilder message = new EmbedBuilder()
                .setTitle(Localization.getLocalization("bot.music.embed.title", true))
                .setColor(Main.music.getAudioPlayer().getPlayingTrack() == null ? new Color(0xFF155B) : Main.music.getAudioPlayer().isPaused() ? new Color(0xFF7300) : new Color(0x3AFF70));
        if(Main.music.getAudioPlayer().getPlayingTrack() == null) message.setDescription(
                Localization.getLocalization("bot.music.embed.null", true)
        );
        else message.setDescription(
                Localization.getLocalization("bot.music.embed.playing", true)
        );
        if(Main.music.getAudioPlayer().isPaused()) message.setFooter(
                Localization.getLocalization("bot.music.embed.pause", true)
        );
        else message.setFooter(
                Localization.getLocalization("bot.music.embed.playing.time", true)
        );
        if(Main.music.getTrackManager().queue.size() != 0){
            if(Main.music.getTrackManager().lastTrack != null) message.addField(Localization.getLocalization("bot.music.embed.next.title", true),
                    Localization.getMusicParseText(Main.music.getTrackManager().lastTrack,
                            Localization.getLocalization("bot.music.embed.next", true)), false
            );
        }
        e.replyEmbeds(message.build())
                .addActionRow(
                        Button.primary("music_update", Emoji.fromUnicode("♻")),
                        Button.secondary("music_down", Emoji.fromUnicode("🔉")),
                        Main.music.getAudioPlayer().isPaused() ? Button.success("music_pause", Emoji.fromUnicode("⏸"))
                                .withDisabled(Main.music.getAudioPlayer().getPlayingTrack() == null)
                        : Button.secondary("music_pause", Emoji.fromUnicode("▶"))
                                .withDisabled(Main.music.getAudioPlayer().getPlayingTrack() == null),
                        Button.secondary("music_up", Emoji.fromUnicode("🔊")),
                        Button.secondary("music_skip", Emoji.fromUnicode("⏭")).withDisabled(
                                Main.music.getTrackManager().lastTrack == null
                        )
                )
                .addActionRow(
                        Main.music.getTrackManager().isRepeating() ?
                                Button.success("music_repeat", Emoji.fromUnicode("🔁")) :
                                Button.danger("music_repeat", Emoji.fromUnicode("🔁")),
                        Button.secondary("music_reset", Emoji.fromUnicode("❌")),
                        Button.secondary("music_shuffle", Emoji.fromUnicode("🔀"))
                )
                .setEphemeral(false)
                .queue();
    }
    public static void downVolume(ButtonInteractionEvent e){
        Main.music.setVolume(Main.music.getVolume()-1);
        e.reply(Localization.getLocalization("bot.volume.down", true)).setEphemeral(true).queue();
    }
    public static void upVolume(ButtonInteractionEvent e){
        Main.music.setVolume(Main.music.getVolume()+1);
        e.reply(Localization.getLocalization("bot.volume.up", true)).setEphemeral(true).queue();
    }
}
