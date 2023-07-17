package ru.simplykel.kelutils.client.screens;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.json.JSONObject;
import ru.simplykel.kelutils.client.Main;
import ru.simplykel.kelutils.client.config.Localization;
import ru.simplykel.kelutils.client.config.UserConfig;
import ru.simplykel.kelutils.client.lavaplayer.playlist.PlaylistObject;

import java.nio.file.Files;
import java.nio.file.Path;

public class MusicScreen {
    public static Screen buildScreen (Screen currentScreen) {
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        UserConfig.load();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(currentScreen)
                .setTitle(Localization.getText("kelutils.name"))
                .setTransparentBackground(true)
                .setSavingRunnable(MusicScreen::save);
        ConfigCategory category = builder.getOrCreateCategory(Localization.getText("kelutils.music"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.music.url"),
                        "")
                .setDefaultValue("")
                .setSaveConsumer(newValue -> UserConfig.LAST_REQUEST_MUSIC = newValue)
                .build());
        //
        category.addEntry(entryBuilder.startStrField(
                        Localization.getText("kelutils.music.url.copy"),
                        UserConfig.LAST_REQUEST_MUSIC)
                .setDefaultValue(UserConfig.LAST_REQUEST_MUSIC)
                .build());
        if(Main.music.getTrackManager().queue.size() != 0){
            category.addEntry(entryBuilder.startTextDescription(Localization.getText("kelutils.music.queue")).build());
            for(AudioTrack track : Main.music.getTrackManager().queue){
                if(track != null) category.addEntry(entryBuilder.startTextDescription(
                        Localization.toText(
                                "«" + track.getInfo().author + "» " + track.getInfo().title + " " + (track.getInfo().isStream ? Localization.getLocalization("music.live", true) : Localization.getTimestamp(track.getDuration()))
                        )
                ).build());
            }
        }
        return builder.build();
    }
    private static void save(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        UserConfig.save();
        if(!UserConfig.LAST_REQUEST_MUSIC.isBlank()){
            if(UserConfig.LAST_REQUEST_MUSIC.startsWith("playlist:")){
                String name = UserConfig.LAST_REQUEST_MUSIC.replace("playlist:", "");
                PlaylistObject playlist;
                JSONObject jsonPlaylist = new JSONObject();

                final Path configFile = CLIENT.runDirectory.toPath().resolve("KelUtils/playlists/"+name+".json");
                try {
                    jsonPlaylist = new JSONObject(Files.readString(configFile));
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                playlist = new PlaylistObject(jsonPlaylist);
                for(int i = 0; i<playlist.urls.size(); i++){
                    Main.music.getTrackSearch().getTracks(playlist.urls.get(i));
                }
                if(CLIENT.player != null) CLIENT.player.sendMessage(Localization.toText(
                        Localization.toString(Localization.getText("kelutils.music.add.playlist"))
                                .replace("%playlist_name%", playlist.title)
                ));
            } else {
                Main.music.getTrackSearch().getTracks(UserConfig.LAST_REQUEST_MUSIC);
                if(CLIENT.player != null) CLIENT.player.sendMessage(Localization.getText("kelutils.music.add"));
            }
        }else if(CLIENT.player != null) CLIENT.player.sendMessage(Localization.getText("kelutils.music.add.blank"));
    }
}
