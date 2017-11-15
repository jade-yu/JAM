package edu.dlsu.mobapde.jam.Fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.dlsu.mobapde.jam.R;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Track;
import edu.dlsu.mobapde.jam.RecyclerViewItems.TrackAdapter;

public class TracksFragment extends Fragment {

    RecyclerView rvTracks;

    public TracksFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView", "onCreateView: started");
        return inflater.inflate(R.layout.rv_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //TODO fix recyclerView implementation
        rvTracks = (RecyclerView) view.findViewById(R.id.rv_tracks);

        ArrayList<Track> tracks = getTracks();

        Log.d("tracksize", tracks.size() + "");

        TrackAdapter trackAdapter = new TrackAdapter(tracks);
        rvTracks.setAdapter(trackAdapter);

        trackAdapter.setOnItemClickListener(new TrackAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Track t) {
                //TODO onItemClick implementation
                Log.d("clickedtitle", t.getTitle());
            }
        });

        rvTracks.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false));
    }

    // Gets the list of songs from the device storage
    public ArrayList<Track> getTracks() {

        ArrayList<Track> tracks = new ArrayList<>();

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            ContentResolver musicResolver = getActivity().getContentResolver();
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

                    tracks.add(new Track(id, title, artist));
                } while (musicCursor.moveToNext());
            }

            Collections.sort(tracks, new Comparator<Track>(){
                public int compare(Track a, Track b){
                    return a.getTitle().compareTo(b.getTitle());
                }
            });
        }

        return tracks;
    }

}
