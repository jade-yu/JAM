package edu.dlsu.mobapde.jam.Activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.R;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Track;
import edu.dlsu.mobapde.jam.Service.MusicService;
import edu.dlsu.mobapde.jam.Service.MusicService.MusicBinder;

public class PlaySongActivity extends AppCompatActivity {

    ImageView btnGoBack;

    TextView tvTracksong, tvTrackartist, tvLyrics, tvTrackstart, tvTrackend;
    ImageView ivAlbumshow;

    ImageButton ibBacktrack, ibPlaytrack, ibNexttrack;

    SeekBar sbProgress;
    Handler seekHandler;

    Track currentTrack;
    ArrayList<Track> trackList;

    private MusicService musicService;
    private Intent playIntent;
    private boolean musicBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        btnGoBack = findViewById(R.id.btn_goback);

        tvTracksong = findViewById(R.id.tv_tracksong);
        tvTrackartist = findViewById(R.id.tv_trackartist);
        tvLyrics = findViewById(R.id.tv_lyrics);
        ivAlbumshow = findViewById(R.id.iv_albumshow);

        ibBacktrack = findViewById(R.id.ib_backtrack);
        ibPlaytrack = findViewById(R.id.ib_playtrack);
        ibNexttrack = findViewById(R.id.ib_nexttrack);

        sbProgress = findViewById(R.id.sb_progress);
        tvTrackstart = findViewById(R.id.tv_trackstart);
        tvTrackend = findViewById(R.id.tv_trackend);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("currentTrack", musicService.getCurrentTrack());

                setResult(RESULT_OK, i);
                finish();
            }
        });

        ibPlaytrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.togglePlay();
            }
        });

        ibBacktrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.previousTrack();
                playSong();
            }
        });

        ibNexttrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.nextTrack();
                playSong();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.MEDIA_CONTENT_CONTROL);
//        Log.d("onStart", "permission check");

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MEDIA_CONTENT_CONTROL}, 1);
//            Log.d("permission check", "media content control");
        }

        if(playIntent == null && !musicBound) {
//            Log.d("debug", "playIntent null");
            playIntent = new Intent(this, MusicService.class);
            startService(playIntent);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
        }
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder = (MusicBinder) service;

            //get service
            musicService = binder.getService();

            //pass list
            if(getIntent().getIntExtra("position", -1) != -1) {
//                Log.d("position", getIntent().getIntExtra("position", -1) + "");
                currentTrack = getIntent().getParcelableExtra("currentTrack");
                trackList = getIntent().getParcelableArrayListExtra("trackList");
                musicService.setTracks(trackList);
                musicService.setCurrentPosition(getIntent().getIntExtra("position", -1));
            } else {
//                Log.d("position", "-1");
                currentTrack = musicService.getCurrentTrack();
                trackList = musicService.getTrackList();
            }

            musicBound = true;

            musicService.playTrack();

            playSong();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public void playSong() {
        currentTrack = musicService.getCurrentTrack();
        tvTracksong.setText(currentTrack.getTitle());
        tvTrackartist.setText(currentTrack.getArtist());

//        Log.d("playSong", "track duration " + currentTrack.getDuration());

        sbProgress.setMax(currentTrack.getDuration());
        tvTrackend.setText(milliSecondsToTimer(currentTrack.getDuration()));

        seekHandler = new Handler();
        updateSeekBar();

//        Log.d("playSong", "started");

        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public boolean playing;

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(musicService.getMediaPlayer() != null){
                    musicService.getMediaPlayer().seekTo(seekBar.getProgress());
                    if(playing) {
                        musicService.getMediaPlayer().start();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                playing = musicService.getMediaPlayer().isPlaying();
                if(playing){
                    musicService.getMediaPlayer().pause();
                }
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                /*if(musicService.getMediaPlayer() != null && fromUser){
                    musicService.getMediaPlayer().seekTo(progress);
                }*/
            }
        });

        musicService.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                musicService.onCompletion(mp);
                playSong();
            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
        }
    };

    private void updateSeekBar() {
        sbProgress.setProgress(musicService.getMediaPlayer().getCurrentPosition());
        tvTrackstart.setText(milliSecondsToTimer(musicService.getMediaPlayer().getCurrentPosition()));
        seekHandler.postDelayed(runnable, 1000);
    }

    private String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("currentTrack", musicService.getCurrentTrack());

        setResult(RESULT_OK, i);
        super.onBackPressed();
    }

    protected void onDestroy() {
        unbindService(musicConnection);

        Log.d("PlaySongActivity", "music connection unbind successful");

        super.onDestroy();
    }

    public void songFinished() {
        playSong();
    }

}
