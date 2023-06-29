package ru.simplykel.kelutils.lavaplayer.sources;

import com.github.topisenpai.lavasrc.yandexmusic.YandexMusicSourceManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.bandcamp.BandcampAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.beam.BeamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.http.HttpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.vimeo.VimeoAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import ru.simplykel.kelutils.Main;
import ru.simplykel.kelutils.config.UserConfig;

public class AudioSources {
	
	public static void registerSources(AudioPlayerManager audioPlayerManager) {
//		final YoutubeAudioSourceManager youtube = new YoutubeAudioSourceManager(true, System.getProperty("musicplayer.lavaplayer.youtube.email"), System.getProperty("musicplayer.lavaplayer.youtube.password"));
//		youtube.setPlaylistPageCount(100);
		if(UserConfig.YANDEX_MUSIC_TOKEN.isBlank()) Main.LOG.warn("Yandex.Music token is empty, there will be no support");
		else audioPlayerManager.registerSourceManager(new YandexMusicSourceManager(UserConfig.YANDEX_MUSIC_TOKEN));
		audioPlayerManager.registerSourceManager(new YoutubeAudioSourceManager(false, UserConfig.YOUTUBE_EMAIL, UserConfig.YOUTUBE_PASSWORD));
		audioPlayerManager.registerSourceManager(SoundCloudAudioSourceManager.createDefault());
		audioPlayerManager.registerSourceManager(new BandcampAudioSourceManager());
		audioPlayerManager.registerSourceManager(new VimeoAudioSourceManager());
		audioPlayerManager.registerSourceManager(new TwitchStreamAudioSourceManager());
		audioPlayerManager.registerSourceManager(new BeamAudioSourceManager());
		audioPlayerManager.registerSourceManager(new HttpAudioSourceManager());
		audioPlayerManager.registerSourceManager(new LocalAudioSourceManager());
	}
}
