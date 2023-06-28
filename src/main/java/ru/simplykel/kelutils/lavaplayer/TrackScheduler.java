package ru.simplykel.kelutils.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import ru.simplykel.kelutils.Main;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter
{
    private boolean repeating = false;
    final AudioPlayer player;
    public final Queue<AudioTrack> queue;
    public AudioTrack lastTrack;

    /**
     * @param player The audio player this scheduler uses
     */
    public TrackScheduler(AudioPlayer player)
    {
        this.player = player;
        this.queue = new LinkedList<>();
    }
    /**
     *
     */
    public void skipBySeconds(long seconds) throws Exception {
        if(player.getPlayingTrack() == null){
            throw new Exception("Playing track is null!");
        }
        if(player.getPlayingTrack().getPosition() + seconds > player.getPlayingTrack().getDuration()){
            nextTrack();
            return;
        }
        player.getPlayingTrack().setPosition(player.getPlayingTrack().getPosition() + (seconds*1000));
    }
    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public void queue(AudioTrack track)
    {

        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.
        if (!player.startTrack(track, true))
        {
            queue.offer(track);
        }
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack()
    {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        player.startTrack(queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason)
    {
        this.lastTrack = track;
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext)
        {
            if (repeating)
                player.startTrack(lastTrack.makeClone(), false);
            else
                nextTrack();
        }

    }
    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track){
        Main.LOG.info("Start track: "+track.getInfo().title);
    }

    public boolean isRepeating()
    {
        return repeating;
    }

    public void setRepeating(boolean repeating)
    {
        this.repeating = repeating;
    }

    public void shuffle()
    {
        Collections.shuffle((List<?>) queue);
    }
}
