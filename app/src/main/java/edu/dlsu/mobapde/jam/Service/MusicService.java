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
    private boolean playing = false;

    private static boolean active = false;
    private static final int NOTIFY_ID = 1;

    private final IBinder musicBind = new MusicBinder();

    public void onCreate() {
        super.onCreate();

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

    public MediaPlayer getMediaPlayer() {
        return player;
    }

    public void initializeMusicPlayer() {
        player = new MediaPlayer();

        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);

        tracks = new ArrayList<>();
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public void playTrack() {
        //initializeMusicPlayer();
        //player.reset();

        Track track = tracks.get(currentPosition);
        Log.d("playTrack position", "" + currentPosition);
        Uri trackUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, track.getId());

        active = true;

        try {
            player.reset();

            player.setDataSource(getApplicationContext(), trackUri);
            Log.d("playTrack: ", track.getTitle());
        } catch(Exception e) {
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }

        player.prepareAsync();
        playing = true;
        startNotification();
    }

    public void togglePlay() {
        if(player.isPlaying()) {
            player.pause();
            playing = false;
            stopForeground(true);
        } else {
            player.start();
            playing = true;
            startNotification();
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

    public void startNotification() {
        Intent notIntent = new Intent(this, PlaySongActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0, notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.appicon)
                .setTicker(getCurrentTrack().getTitle())
                .setOngoing(true)
                .setContentTitle("Playing")
                .setContentText(getCurrentTrack().getTitle());
        Notification not = builder.build();

        startForeground(NOTIFY_ID, not);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MusicService", "onBind");
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //player.reset();
        //player.release();
        //active = false;
        Log.d("MusicService", "onUnbind");
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        currentPosition++;
        Log.d("currentPosition", "changed to " + currentPosition);
        playTrack();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onDestroy() {
        active = false;
        stopForeground(true);
    }

    public static boolean isActive() {
        return active;
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isSame(Track t, ArrayList<Track> list, int p) {
        boolean flag = true;

        if(!tracks.isEmpty()) {
            if(p == currentPosition
                    && t.getId() == getCurrentTrack().getId()
                    && tracks.size() == list.size()) {
                for(int i = 0 ; i < tracks.size() && flag ; i++) {
                    if(tracks.get(i).getId() != list.get(i).getId()) {
                        flag = false;
                    }
                }
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }

        return flag;
    }

}
