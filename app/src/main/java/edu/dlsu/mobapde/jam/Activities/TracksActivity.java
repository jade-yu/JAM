package edu.dlsu.mobapde.jam.Activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.dlsu.mobapde.jam.R;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Track;
import edu.dlsu.mobapde.jam.RecyclerViewItems.TrackAdapter;

public class TracksActivity extends AppCompatActivity {

    RecyclerView rvTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        rvTracks = (RecyclerView) findViewById(R.id.rv_tracks);

        ArrayList<Track> trackList = getTrackList();

        Log.d("tracksize", trackList.size() + "");

        TrackAdapter trackAdapter = new TrackAdapter(trackList);
        rvTracks.setAdapter(trackAdapter);

        trackAdapter.setOnItemClickListener(new TrackAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Track t) {
                //TODO onItemClick implementation
                Log.d("clickedtitle", t.getTitle());
            }
        });

        rvTracks.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
    }

    // Gets the list of songs from the device storage
    public ArrayList<Track> getTrackList() {

        ArrayList<Track> trackList = new ArrayList<>();

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            ContentResolver musicResolver = getContentResolver();
            Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

            Log.d("cursorsize", musicCursor.getCount() + "");

            if(musicCursor != null && musicCursor.moveToFirst()){
                //get columns
                int titleColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
                int idColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
                int artistColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);

                //add songs to list
                do {
                    long id = musicCursor.getLong(idColumn);
                    String title = musicCursor.getString(titleColumn);
                    String artist = musicCursor.getString(artistColumn);

//                    Log.d("title", title);

                    trackList.add(new Track(id, title, artist));
                } while (musicCursor.moveToNext());
            }

            Collections.sort(trackList, new Comparator<Track>(){
                public int compare(Track a, Track b){
                    return a.getTitle().compareTo(b.getTitle());
                }
            });
        }

        return trackList;
    }

}
