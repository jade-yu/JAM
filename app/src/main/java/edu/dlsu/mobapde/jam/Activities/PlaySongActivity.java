package edu.dlsu.mobapde.jam.Activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.R;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Track;
import edu.dlsu.mobapde.jam.Service.MusicService;
import edu.dlsu.mobapde.jam.Service.MusicService.MusicBinder;

public class PlaySongActivity extends AppCompatActivity {

    ImageView btnGoBack;

    TextView tvTracksong, tvTrackartist, tvLyrics;
    ImageView ivAlbumshow;

    ImageButton ibBacktrack, ibPlaytrack, ibNexttrack;

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

        currentTrack = getIntent().getParcelableExtra("currentTrack");
        trackList = getIntent().getParcelableArrayListExtra("trackList");

//        Log.d("Playsong tracksize", trackList.size() + "");

        tvTracksong.setText(currentTrack.getTitle());
        tvTrackartist.setText(currentTrack.getArtist());

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO pass current track info
                finish();
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

        if(playIntent == null){
            Log.d("debug", "playIntent not null");
            //TODO fix playing of song; reaches up to here
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    //TODO fix playing of song; does not reach here
    public void playSong() {
        musicService.setCurrentPosition(getIntent().getIntExtra("position", 0));
        Log.d("playSong", "started");
        musicService.playTrack();
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        //TODO fix playing of song; does not reach here
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder = (MusicBinder) service;

            //get service
            musicService = binder.getService();

            //pass list
            musicService.setTracks(trackList);
            musicBound = true;
            Log.d("onServiceConnected", "started");
            playSong();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    protected void onDestroy() {
        super.onDestroy();
        unbindService(musicConnection);
    };

}
