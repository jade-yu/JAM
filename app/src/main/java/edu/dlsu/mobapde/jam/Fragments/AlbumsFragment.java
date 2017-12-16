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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.Activities.MainActivity;
import edu.dlsu.mobapde.jam.Activities.PlaySongActivity;
import edu.dlsu.mobapde.jam.R;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Album;
import edu.dlsu.mobapde.jam.RecyclerViewItems.AlbumAdapter;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Track;
import edu.dlsu.mobapde.jam.RecyclerViewItems.TrackAdapter;

public class AlbumsFragment extends Fragment {

    RecyclerView rvAlbums;

    final public static Uri sArtworkUri = Uri
            .parse("content://media/external/audio/albumart");

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
        rvAlbums = (RecyclerView) view.findViewById(R.id.rv_main);

        ArrayList<Album> albums = getAlbums();

        AlbumAdapter albumAdapter = new AlbumAdapter(albums);
        //rvAlbums.setAdapter(albumAdapter);
        rvAlbums.swapAdapter(albumAdapter, false);

        albumAdapter.setOnItemClickListener(new AlbumAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Album a) {
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

                rvAlbums.setAdapter(trackAdapter);
                rvAlbums.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false));
            }
        });

        rvAlbums.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    public ArrayList<Album> getAlbums() {

        ArrayList<Album> albums = new ArrayList<>();

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            ContentResolver musicResolver = getActivity().getContentResolver();
            Uri musicUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
            Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

            if (musicCursor != null && musicCursor.moveToFirst()) {
                //get columns
                int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
                int artistColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Albums.ARTIST);
                int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums._ID);
                int albumartColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                //int tracksColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);

                //add songs to list
                do {
                    long albumid = musicCursor.getLong(albumColumn);
                    String title = musicCursor.getString(titleColumn);
                    String artist = musicCursor.getString(artistColumn);
                    String trackArt = musicCursor.getString(albumartColumn);

                    albums.add(new Album(title, artist, albumid, trackArt));
                } while (musicCursor.moveToNext());
            }

        }


        return albums;

    }

    public ArrayList<Track> getTracks(Album a) {

        ArrayList<Track> tracks = new ArrayList<>();

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            ContentResolver musicResolver = getActivity().getContentResolver();
            Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor musicCursor = musicResolver.query(musicUri, null, MediaStore.Audio.Media.ALBUM_ID + "=?", new String[]{a.getId() + ""}, MediaStore.Audio.Media.TITLE + " ASC");

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
}
