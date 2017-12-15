package edu.dlsu.mobapde.jam.Activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.R;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Lyrics;
import edu.dlsu.mobapde.jam.RecyclerViewItems.LyricsAdapter;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Track;
import edu.dlsu.mobapde.jam.Service.MusicService;
import edu.dlsu.mobapde.jam.Service.MusicService.MusicBinder;

public class PlaySongActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_GET_LYRICS = 1;

    ImageView btnGoBack;

    RecyclerView rvLyrics;

    TextView tvTracksong, tvTrackartist, tvTrackstart, tvTrackend;
    ImageView ivAlbumshow;

    ImageButton ibBacktrack, ibPlaytrack, ibNexttrack;
    Button btnAdd;

    SeekBar sbProgress;
    Handler seekHandler;

    Track currentTrack;
    ArrayList<Track> trackList;

    ArrayList<Lyrics> lyricsList;

    private MusicService musicService;
    private Intent playIntent;
    private boolean musicBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        lyricsList = new ArrayList<>();

        btnGoBack = findViewById(R.id.btn_goback);

        rvLyrics = findViewById(R.id.rv_lyrics);

        tvTracksong = findViewById(R.id.tv_tracksong);
        tvTrackartist = findViewById(R.id.tv_trackartist);
        ivAlbumshow = findViewById(R.id.iv_albumshow);

        ibBacktrack = findViewById(R.id.ib_backtrack);
        ibPlaytrack = findViewById(R.id.ib_playtrack);
        ibNexttrack = findViewById(R.id.ib_nexttrack);

        sbProgress = findViewById(R.id.sb_progress);
        tvTrackstart = findViewById(R.id.tv_trackstart);
        tvTrackend = findViewById(R.id.tv_trackend);

        btnAdd = findViewById(R.id.btn_addlyrics);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicService.isPlaying()) {
                    musicService.togglePlay();
                    ibPlaytrack.setBackgroundResource(R.drawable.btn_play);
                }

                Intent i = new Intent(getBaseContext(), GetLyrics.class);
                i.putExtra("currentTrack", currentTrack);

                startActivityForResult(i, REQUEST_CODE_GET_LYRICS);
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ibPlaytrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.togglePlay();
                if(musicService.isPlaying()) {
                    ibPlaytrack.setBackgroundResource(R.drawable.btn_pause);
                } else {
                    ibPlaytrack.setBackgroundResource(R.drawable.btn_play);
                }
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

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MEDIA_CONTENT_CONTROL}, 1);
        }

        if(playIntent == null && !musicBound) {
            Log.d("debug", "playIntent null");
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

                if(!musicService.isSame(currentTrack, trackList, getIntent().getIntExtra("position", -1))) {
                    musicService.setTracks(trackList);
                    musicService.setCurrentPosition(getIntent().getIntExtra("position", -1));

                    musicService.playTrack();
                }
            } else {
//                Log.d("position", "-1");
                currentTrack = musicService.getCurrentTrack();
                trackList = musicService.getTrackList();
            }

            musicBound = true;

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
//        ivAlbumshow.setImageResource();

        //TODO find album art from track
        //TODO show album art in playSong
//        if(currentTrack.getAlbum().getId() != -1) {
//            if (currentAlbum.getAlbumart() == null) {
//
//                holder.ivIcon.setImageResource(R.drawable.noalbums);
//
//            } else {
//                Drawable img = Drawable.createFromPath(currentAlbum.getAlbumart());
//                holder.ivIcon.setImageDrawable(img);
//            }
//        }

//        Log.d("playSong", "track duration " + currentTrack.getDuration());

        sbProgress.setMax(currentTrack.getDuration());
        tvTrackend.setText(milliSecondsToTimer(currentTrack.getDuration()));

        seekHandler = new Handler();
        updateSeekBar();

        if(musicService.isPlaying()) {
            ibPlaytrack.setBackgroundResource(R.drawable.btn_pause);
        }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GET_LYRICS && resultCode == RESULT_OK) {
            Log.d("PlaySongActivity", "onActivityResult: ok");
            //TODO get lyrics from db
            //setCurrentTrack(currentTrack);
            //TODO display lyrics

            btnAdd.setVisibility(View.GONE);

            String[] inputLyrics = data.getStringExtra("lyrics").split("//n");

            Log.d("check lyrics", "Vlaalue" + inputLyrics[0]);

            for (int i = 0; i < inputLyrics.length; i++) {
                Lyrics line = new Lyrics();
                line.setLyric(inputLyrics[i]);
                lyricsList.add(line);
            }

            LyricsAdapter lyricsAdapter = new LyricsAdapter(lyricsList);

            rvLyrics.setAdapter(lyricsAdapter);
            rvLyrics.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        }
    }

    @Override
    public void onBackPressed() {
        if(MainActivity.isActive()) {
            Intent i = new Intent();
            i.putExtra("currentTrack", musicService.getCurrentTrack());

            setResult(RESULT_OK, i);
        } else {
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            startActivity(i);
        }

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
