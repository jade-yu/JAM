package edu.dlsu.mobapde.jam.Fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.Activities.MainActivity;
import edu.dlsu.mobapde.jam.Activities.PlaySongActivity;
import edu.dlsu.mobapde.jam.R;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Artist;
import edu.dlsu.mobapde.jam.RecyclerViewItems.ArtistAdapter;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Track;
import edu.dlsu.mobapde.jam.RecyclerViewItems.TrackAdapter;

public class ArtistsFragment extends Fragment {

    RecyclerView rvArtists;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rv_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        rvArtists = (RecyclerView) view.findViewById(R.id.rv_main);

        ArrayList<Artist> artists = getArtists();
        ArrayList<String> icons = getArtistIcons(artists);

        ArtistAdapter artistAdapter = new ArtistAdapter(artists, icons);
        rvArtists.swapAdapter(artistAdapter, false);

        artistAdapter.setOnItemClickListener(new ArtistAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Artist a) {
                final ArrayList<Track> tracks = getTracks(a);
                final ArrayList<String> albums = getAlbumArts(tracks);

                TrackAdapter trackAdapter = new TrackAdapter(tracks, albums);
                trackAdapter.setOnItemClickListener(new TrackAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        Track t = tracks.get(position);
                        ((MainActivity) getActivity()).setCurrentTrack(t);
                        ((MainActivity) getActivity()).setTrackList(tracks);
                        ((MainActivity) getActivity()).setCurrentPosition(position);

                        Intent i = new Intent(getActivity().getBaseContext(), PlaySongActivity.class);
                        i.putParcelableArrayListExtra("trackList", tracks);
                        i.putExtra("currentTrack", t);
                        i.putExtra("position", position);

                        startActivity(i);
                    }
                });

                rvArtists.setAdapter(trackAdapter);
            }
        });

        rvArtists.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false));
    }

    public ArrayList<Artist> getArtists() {

        ArrayList<Artist> artists = new ArrayList<>();

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            ContentResolver musicResolver = getActivity().getContentResolver();
            Uri musicUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
            Cursor musicCursor = musicResolver.query(musicUri, null, null, null, MediaStore.Audio.Artists.ARTIST + " ASC");

            if(musicCursor != null && musicCursor.moveToFirst()){
                int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Artists._ID);
                int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
                int albumsColumn = musicCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
                int tracksColumn = musicCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);

                do {
                    long id = musicCursor.getLong(idColumn);
                    String artist = musicCursor.getString(artistColumn);
                    int numalbums = musicCursor.getInt(albumsColumn);
                    int numtracks = musicCursor.getInt(tracksColumn);

                    artists.add(new Artist(id, artist, numalbums, numtracks));
                } while (musicCursor.moveToNext());
            }
        }

        return artists;

    }

    public ArrayList<Track> getTracks(Artist a) {

        ArrayList<Track> tracks = new ArrayList<>();

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            ContentResolver musicResolver = getActivity().getContentResolver();
            Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor musicCursor = musicResolver.query(musicUri, null, MediaStore.Audio.Media.ARTIST_ID + "=?", new String[]{a.getId() + ""}, MediaStore.Audio.Media.TITLE + " ASC");

            if(musicCursor != null && musicCursor.moveToFirst()){
                int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                int durationColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

                do {
                    long id = musicCursor.getLong(idColumn);
                    String title = musicCursor.getString(titleColumn);
                    String artist = musicCursor.getString(artistColumn);
                    int album = musicCursor.getInt(albumColumn);
                    int duration = musicCursor.getInt(durationColumn);

                    tracks.add(new Track(id, title, artist, album, duration));
                } while (musicCursor.moveToNext());
            }
        }

        return tracks;

    }

    public ArrayList<String> getAlbumArts(ArrayList<Track> tracks) {
        ContentResolver musicResolver = getActivity().getContentResolver();
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        ArrayList<String> albumArts = new ArrayList<>();

        for(Track t : tracks) {
            Cursor albumCursor = musicResolver.query(albumUri, null, MediaStore.Audio.Albums._ID + "=?", new String[]{t.getAlbum() + ""}, null);

            if (albumCursor != null && albumCursor.moveToFirst()) {
                int albumartColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                albumArts.add(albumCursor.getString(albumartColumn));
            } else {
                albumArts.add(null);
            }

        }

        return albumArts;
    }

    public ArrayList<String> getArtistIcons(ArrayList<Artist> artists) {
        ContentResolver musicResolver = getActivity().getContentResolver();
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        ArrayList<String> albumArts = new ArrayList<>();

        for(Artist a : artists) {
            Cursor albumCursor = musicResolver.query(albumUri, null, MediaStore.Audio.Albums.ARTIST + "=?", new String[]{a.getArtist()}, null);

            if (albumCursor.moveToFirst()) {
                int albumartColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                boolean flag = false;

                while(albumCursor.moveToNext() && !flag) {
                    albumArts.add(albumCursor.getString(albumartColumn));
                    flag = true;
                }

                if(!flag)
                    albumArts.add(null);
            } else {
                albumArts.add(null);
            }

        }

        return albumArts;
    }
    
}
