package edu.dlsu.mobapde.jam.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.Fragments.AlbumsFragment;
import edu.dlsu.mobapde.jam.Fragments.ArtistsFragment;
import edu.dlsu.mobapde.jam.Fragments.TracksFragment;
import edu.dlsu.mobapde.jam.R;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Track;
import edu.dlsu.mobapde.jam.Service.MusicService;

public class MainActivity extends AppCompatActivity {

    public static final int TRACKS_TAB = 0;
    public static final int ARTISTS_TAB = 1;
    public static final int ALBUMS_TAB = 2;
    public static final int PLAYLISTS_TAB = 3;
    public static final int FAVES_TAB = 4;

    public static final int REQUEST_CODE_PLAY_SONG = 1;

    EditText etSearch;
    ImageView ivSearch;

    FrameLayout tabContent;

    TabLayout tabLayout;
    TabLayout.Tab tracksTab, artistsTab, albumsTab, playlistsTab, favesTab;

    RelativeLayout llFooter;
    ImageView ivMainAlbum;
    TextView tvMainsong, tvMainartist;
    ImageButton ibBack, ibPlay, ibNext;

    Track currentTrack;
    ArrayList<Track> trackList;
    int currentPosition;

    private MusicService musicService;
    private Intent playIntent;
    private boolean musicBound = false;

    private static boolean active = false;

    public void setCurrentTrack(Track track) {
        currentTrack = track;

        if(currentTrack.getAlbum() != -1) {
            //ivMainAlbum.setImageResource(currentTrack.getAlbum());
        } else {
            ivMainAlbum.setImageResource(R.drawable.noalbums);
        }

        tvMainsong.setText(currentTrack.getTitle());
        tvMainartist.setText(currentTrack.getArtist());

        if(MusicService.isActive()) {
            llFooter.setVisibility(View.VISIBLE);
        }
    }

    public void setTrackList(ArrayList<Track> trackList) {
        this.trackList = trackList;
    }

    public void setCurrentPosition(int pos) {
        currentPosition = pos;
    }

    public static boolean isActive() {
        return active;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        active = true;

        setContentView(R.layout.activity_main);

        etSearch = findViewById(R.id.et_search);
        ivSearch = findViewById(R.id.iv_search);

        tabContent = findViewById(R.id.tabcontent);

        llFooter = findViewById(R.id.footer);
        ivMainAlbum = findViewById(R.id.main_album);
        tvMainsong = findViewById(R.id.tv_mainsong);
        tvMainartist = findViewById(R.id.tv_mainartist);
        ibBack = findViewById(R.id.ib_back);
        ibPlay = findViewById(R.id.ib_play);
        ibNext = findViewById(R.id.ib_next);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo implement search function

                Intent i = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(i);
            }
        });

        View.OnClickListener oclPlaySong = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), PlaySongActivity.class);

                startActivity(i);
            }
        };

        llFooter.setOnClickListener(oclPlaySong);
        ivMainAlbum.setOnClickListener(oclPlaySong);
        tvMainsong.setOnClickListener(oclPlaySong);
        tvMainartist.setOnClickListener(oclPlaySong);

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicService.togglePlay();
                if(musicService.isPlaying()) {
                    ibPlay.setBackgroundResource(R.drawable.btn_pause);
                } else {
                    ibPlay.setBackgroundResource(R.drawable.btn_play);
                }
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicService.previousTrack();
                setCurrentTrack(musicService.getCurrentTrack());
            }
        });

        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicService.nextTrack();
                setCurrentTrack(musicService.getCurrentTrack());
            }
        });

        //show main tabs
        initializeTabs();

        if(MusicService.isActive()) {
            llFooter.setVisibility(View.VISIBLE);
            bindMusicService();
        } else {
            llFooter.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_PLAY_SONG && resultCode == RESULT_OK) {
            currentTrack = data.getParcelableExtra("currentTrack");
            setCurrentTrack(currentTrack);
        }
    }

    public void initializeTabs() {
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        tracksTab = tabLayout.newTab();
        tracksTab.setText("Tracks");

        artistsTab = tabLayout.newTab();
        artistsTab.setText("Artists");
        
        albumsTab = tabLayout.newTab();
        albumsTab.setText("Albums");
        
        playlistsTab = tabLayout.newTab();
        playlistsTab.setText("Playlists");
        //TODO make playlist activity
//        playlistsIntent = new Intent(this, PlaylistsActivity.class);
//        playlistsTab.setContent(playlistsIntent);

        //TODO make faves activity
        favesTab = tabLayout.newTab();
        favesTab.setText("♥");
//        favesIntent = new Intent(this, FavesActivity.class);
//        favesTab.setContent(favesIntent);

        tabLayout.addTab(tracksTab);
        tabLayout.addTab(artistsTab);
        tabLayout.addTab(albumsTab);
        tabLayout.addTab(playlistsTab);
        tabLayout.addTab(favesTab);

        Fragment fragment = new TracksFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.tabcontent, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case TRACKS_TAB:
                        Log.d("onTabSelected", "TRACKS");
                        fragment = new TracksFragment();
                        break;
                    case ARTISTS_TAB:
                        Log.d("onTabSelected", "ARTISTS");
                        fragment = new ArtistsFragment();
                        break;
                    case ALBUMS_TAB:
                        Log.d("onTabSelected", "ALBUMS");
                        fragment = new AlbumsFragment();
                        break;
                    case PLAYLISTS_TAB:
                        Log.d("onTabSelected", "PLAYLISTS");
                        //TODO playlists fragment
                        fragment = new TracksFragment();
                        break;
                    case FAVES_TAB:
                        Log.d("onTabSelected", "FAVES");
                        //TODO faves fragment
                        fragment = new TracksFragment();
                        break;
                }

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.tabcontent, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void bindMusicService() {
        ServiceConnection musicConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
                musicService = binder.getService();

                currentTrack = musicService.getCurrentTrack();
                trackList = musicService.getTrackList();

                musicBound = true;

                setCurrentTrack(currentTrack);

                if(musicService.isPlaying()) {
                    ibPlay.setBackgroundResource(R.drawable.btn_pause);
                } else {
                    ibPlay.setBackgroundResource(R.drawable.btn_play);
                }

                musicService.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        musicService.onCompletion(mp);
                        setCurrentTrack(musicService.getCurrentTrack());
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                musicBound = false;
            }
        };

        if(playIntent == null && !musicBound) {
            playIntent = new Intent(this, MusicService.class);
            startService(playIntent);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
        }
    }

    //TODO fix onresume
    @Override
    protected void onResume() {
        super.onResume();

        if(MusicService.isActive()) {
            bindMusicService();
        }
    }

    @Override
    protected void onDestroy() {
        active = false;
        super.onDestroy();
    }
}
