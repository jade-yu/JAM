package edu.dlsu.mobapde.jam.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import edu.dlsu.mobapde.jam.R;

public class MainActivity extends AppCompatActivity {

    EditText etSearch;
    ImageView ivSearch;

    TabHost tabHost;
    TabHost.TabSpec tracksTab, artistsTab, albumsTab, playlistsTab, favesTab;

    RelativeLayout rlFooter;
    ImageView ivMainAlbum;
    TextView tvMainsong, tvMainartist;
    ImageButton ibBack, ibPlay, ibNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = findViewById(R.id.et_search);
        ivSearch = findViewById(R.id.iv_search);

        rlFooter = findViewById(R.id.footer);
        ivMainAlbum = findViewById(R.id.main_album);
        tvMainsong = findViewById(R.id.tv_mainsong);
        tvMainartist = findViewById(R.id.tv_mainartist);
        ibBack = findViewById(R.id.ib_back);
        ibPlay = findViewById(R.id.ib_play);
        ibNext = findViewById(R.id.ib_next);

        initializeTabs();

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        ivMainAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        tvMainsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        tvMainartist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

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

    //TODO fix initialization, change to TabLayout
    public void initializeTabs() {

        Intent tracksIntent, artistsIntent, albumsIntent, playlistsIntent, favesIntent;

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        tracksTab = tabHost.newTabSpec("tracks");
        tracksTab.setIndicator("Tracks");
        tracksIntent = new Intent(this, TracksActivity.class);
        tracksTab.setContent(tracksIntent);
        tabHost.addTab(tracksTab);

        artistsTab = tabHost.newTabSpec("artists");
        artistsTab.setIndicator("Artists");
        artistsIntent = new Intent(this, ArtistsActivity.class);
        artistsTab.setContent(artistsIntent);
        
        albumsTab = tabHost.newTabSpec("albums");
        albumsTab.setIndicator("Albums");
        albumsIntent = new Intent(this, AlbumsActivity.class);
        albumsTab.setContent(albumsIntent);
        
        playlistsTab = tabHost.newTabSpec("playlists");
        playlistsTab.setIndicator("Playlists");
        //TODO make playlist activity
//        playlistsIntent = new Intent(this, PlaylistsActivity.class);
//        playlistsTab.setContent(playlistsIntent);

        //TODO make faves activity
        favesTab = tabHost.newTabSpec("faves");
        favesTab.setIndicator("♥");
//        favesIntent = new Intent(this, FavesActivity.class);
//        favesTab.setContent(favesIntent);

        tabHost.addTab(artistsTab);
        tabHost.addTab(albumsTab);
        tabHost.addTab(playlistsTab);
        tabHost.addTab(favesTab);
    }
}
