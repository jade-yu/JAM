package edu.dlsu.mobapde.jam.Service;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import java.util.Random;
import android.app.Notification;
import android.app.PendingIntent;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.Activities.PlaySongActivity;
import edu.dlsu.mobapde.jam.R;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Track;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer player;
    private ArrayList<Track> tracks;
    private int currentPosition;
    private static final int NOTIFY_ID = 1;

    private final IBinder musicBind = new MusicBinder();

    public void onCreate() {
        super.onCreate();

        player = new MediaPlayer();
        tracks = new ArrayList<>();

        initializeMusicPlayer();
    }

    public ArrayList<Track> getTrackList() {
        return tracks;
    }

    public Track getCurrentTrack() {
        return tracks.get(currentPosition);
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public void setCurrentPosition(int index){
        currentPosition = index;
    }

    public void initializeMusicPlayer() {
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public void playTrack() {
        //player.reset();

        Track track = tracks.get(currentPosition);
        Uri trackUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, track.getId());

        try {
            player.reset();
            player.setDataSource(getApplicationContext(), trackUri);
            Log.d("playTrack: ", track.getTitle());
        } catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }

        player.prepareAsync();
    }

    public void togglePlay() {
        if(player.isPlaying()) {
            player.pause();
        } else {
            player.start();
        }
    }

    public void previousTrack() {
        if(currentPosition == 0) {
            currentPosition = tracks.size() - 1;
        } else {
            currentPosition--;
        }

        playTrack();
    }

    public void nextTrack() {
        if(currentPosition == tracks.size() - 1) {
            currentPosition = 0;
        } else {
            currentPosition++;
        }

        playTrack();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MusicService", "onBind");
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        Log.d("MusicService", "onUnbind");
        return false;
    }

    //TODO Listener methods
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        currentPosition++;
        playTrack();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();

        Intent notIntent = new Intent(this, PlaySongActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.btn_play)
                .setTicker(getCurrentTrack().getTitle())
                .setOngoing(true)
                .setContentTitle("Playing").setContentText(getCurrentTrack().getTitle());
        Notification not = builder.build();

        startForeground(NOTIFY_ID, not);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }
}
