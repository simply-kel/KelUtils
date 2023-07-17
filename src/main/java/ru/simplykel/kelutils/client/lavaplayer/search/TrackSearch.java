package ru.simplykel.kelutils.client.lavaplayer.search;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import ru.simplykel.kelutils.client.Main;
import ru.simplykel.kelutils.client.lavaplayer.MusicManager;
import ru.simplykel.kelutils.client.lavaplayer.TrackScheduler;

public class TrackSearch {
	
	private final AudioPlayerManager audioPlayerManager;
	private final MusicManager musicManagers;
	
	public TrackSearch(AudioPlayerManager audioplayermanager, AudioPlayer audioPlayer, TrackScheduler trackScheduler) {
		this.audioPlayerManager = audioplayermanager;
		this.musicManagers = new MusicManager(audioPlayer, trackScheduler);
	}

	public void getTracks(String url)
	{
		final String trackUrl = url;
		audioPlayerManager.loadItemOrdered(musicManagers, trackUrl, new AudioLoadResultHandler()
		{
			@Override
			public void trackLoaded(AudioTrack track)
			{
				musicManagers.scheduler.queue(track);
				Main.LOG.info("Add track: "+track.getInfo().title);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist)
			{
				Main.LOG.info("Add playlist: "+playlist.getName());
				List<AudioTrack> tracks = playlist.getTracks();
				tracks.forEach(musicManagers.scheduler::queue);
			}

			@Override
			public void noMatches()
			{
				Main.LOG.error("Nothing found by " + trackUrl);
			}

			@Override
			public void loadFailed(FriendlyException exception)
			{
				Main.LOG.error(exception.getMessage());
			}
		});
	}
}
