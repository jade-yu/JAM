package edu.dlsu.mobapde.jam.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    EditText etSearch;
    ImageView ivSearch;

    FrameLayout tabContent;

    TabLayout tabLayout;
    TabLayout.Tab tracksTab, artistsTab, albumsTab, playlistsTab, favesTab;

    LinearLayout llFooter;
    ImageView ivMainAlbum;
    TextView tvMainsong, tvMainartist;
    ImageButton ibBack, ibPlay, ibNext;

    Track currentTrack;
    ArrayList<Track> trackList;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //show main tabs
        initializeTabs();

        //show footer if a song is playing
        if(currentTrack == null) {
            llFooter.setVisibility(View.GONE);
        }

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
                i.putParcelableArrayListExtra("trackList", trackList);
                i.putExtra("currentTrack", currentTrack);
                i.putExtra("position", currentPosition);

                startActivity(i);
            }
        };

        llFooter.setOnClickListener(oclPlaySong);
        ivMainAlbum.setOnClickListener(oclPlaySong);
        tvMainsong.setOnClickListener(oclPlaySong);
        tvMainartist.setOnClickListener(oclPlaySong);

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });
    }

    public void setCurrentTrack(Track track) {
        if(MusicService.isActive()) {
            llFooter.setVisibility(View.VISIBLE);
        }

        currentTrack = track;

        if(currentTrack.getAlbumcover() != -1) {
            ivMainAlbum.setImageResource(currentTrack.getAlbumcover());
        }
        tvMainsong.setText(currentTrack.getTitle());
        tvMainartist.setText(currentTrack.getArtist());
    }

    public void setCurrentPosition(int pos) {
        currentPosition = pos;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TODO get track info to update footer
        //TODO implement listener to update footer
//        updateTrack();
    }

    public void updateTrack(Track t, ArrayList<Track> list, int pos) {
        currentTrack = t;
        trackList = list;
        currentPosition = pos;
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
                        Log.d("tabselected", "onTabSelected: TRACKS");
                        fragment = new TracksFragment();
                        break;
                    case ARTISTS_TAB:
                        Log.d("tabselected", "onTabSelected: ARTISTS");
                        fragment = new ArtistsFragment();
                        break;
                    case ALBUMS_TAB:
                        Log.d("tabselected", "onTabSelected: ALBUMS");
                        fragment = new AlbumsFragment();
                        break;
                    case PLAYLISTS_TAB:
                        Log.d("tabselected", "onTabSelected: PLAYLISTS");
                        //TODO playlists fragment
                        fragment = new TracksFragment();
                        break;
                    case FAVES_TAB:
                        Log.d("tabselected", "onTabSelected: FAVES");
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
}
