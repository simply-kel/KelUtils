package ru.simplykel.kelutils.lavaplayer;

import javax.sound.sampled.DataLine.Info;

import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import com.sedmelluq.discord.lavaplayer.format.Pcm16AudioDataFormat;
import com.sedmelluq.discord.lavaplayer.player.AudioConfiguration.ResamplingQuality;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import ru.simplykel.kelutils.lavaplayer.api.output.IOutputConsumer;
import ru.simplykel.kelutils.lavaplayer.output.AudioOutput;
import ru.simplykel.kelutils.lavaplayer.search.TrackSearch;
import ru.simplykel.kelutils.lavaplayer.sources.AudioSources;

public class MusicPlayer {
	
	private final AudioPlayerManager audioPlayerManager;
	private final AudioDataFormat audioDataFormat;
	private final AudioPlayer audioPlayer;
	private final AudioOutput audioOutput;
	
	private final TrackSearch trackSearch;
	private final TrackScheduler trackManager;
	
	private IOutputConsumer outputConsumer;
	
	public MusicPlayer() {
		audioPlayerManager = new DefaultAudioPlayerManager();
		audioDataFormat = new Pcm16AudioDataFormat(2, 48000, 960, true);
		audioPlayer = audioPlayerManager.createPlayer();
		audioOutput = new AudioOutput(this);

		trackManager = new TrackScheduler(audioPlayer);
		trackSearch = new TrackSearch(audioPlayerManager, audioPlayer, trackManager);
		audioPlayer.setVolume(2);
		setup();
	}
	
	private void setup() {
		audioPlayerManager.setFrameBufferDuration(1000);
		audioPlayerManager.setPlayerCleanupThreshold(Long.MAX_VALUE);
		
		audioPlayerManager.getConfiguration().setResamplingQuality(ResamplingQuality.HIGH);
		audioPlayerManager.getConfiguration().setOpusEncodingQuality(10);
		audioPlayerManager.getConfiguration().setOutputFormat(audioDataFormat);
		
		AudioSources.registerSources(audioPlayerManager);
	}
	
	public AudioPlayerManager getAudioPlayerManager() {
		return audioPlayerManager;
	}
	
	public AudioDataFormat getAudioDataFormat() {
		return audioDataFormat;
	}
	
	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
	
	public IOutputConsumer getOutputConsumer() {
		return outputConsumer;
	}
	
//	@Override
	public TrackScheduler getTrackManager() {
		return trackManager;
	}
	
//	@Override
	public TrackSearch getTrackSearch() {
		return trackSearch;
	}
	
//	@Override
	public void startAudioOutput() {
		audioOutput.start();
	}
	
//	@Override
	public void setMixer(String name) {
		audioOutput.setMixer(name);
	}
	
//	@Override
	public String getMixer() {
		return audioOutput.getMixer();
	}
	
//	@Override
	public Info getSpeakerInfo() {
		return audioOutput.getSpeakerInfo();
	}
	
//	@Override
	public void setVolume(int volume) {
		audioPlayer.setVolume(volume);
	}
	
//	@Override
	public int getVolume() {
		return audioPlayer.getVolume();
	}
	
//	@Override
	public void setOutputConsumer(IOutputConsumer consumer) {
		outputConsumer = consumer;
	}
}
