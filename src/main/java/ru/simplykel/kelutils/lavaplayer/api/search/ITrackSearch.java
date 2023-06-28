package ru.simplykel.kelutils.lavaplayer.api.search;

import ru.simplykel.kelutils.lavaplayer.MusicManager;

import java.util.function.Consumer;

public interface ITrackSearch {
	
	void getTracks(MusicManager mng, String url);
	
}
