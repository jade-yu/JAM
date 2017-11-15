package edu.dlsu.mobapde.jam.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.RecyclerViewItems.Track;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer player;
    private ArrayList<Track> tracks;
    private int currentPosition;

    public void onCreate() {
        super.onCreate();
        currentPosition = 0;

        player = new MediaPlayer();
        initializeMusicPlayer();
    }

    public void initializeMusicPlayer() {
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    //TODO onBind function
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //TODO Listener methods
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}
