package edu.dlsu.mobapde.jam.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.R;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Album;
import edu.dlsu.mobapde.jam.RecyclerViewItems.AlbumAdapter;

public class AlbumsFragment extends Fragment {

    RecyclerView rvAlbums;

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
        rvAlbums = (RecyclerView) view.findViewById(R.id.rv_tracks);

        ArrayList<Album> albums = getAlbums();

        Log.d("albumsize", albums.size() + "");

        AlbumAdapter albumAdapter = new AlbumAdapter(albums);
        //rvAlbums.setAdapter(albumAdapter);
        rvAlbums.swapAdapter(albumAdapter, true);

        albumAdapter.setOnItemClickListener(new AlbumAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Album a) {
                //TODO onItemClick implementation
                Log.d("clickedalbum", a.getTitle());
            }
        });

        rvAlbums.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    //TODO getAlbums implementation
    public ArrayList<Album> getAlbums() {

        ArrayList<Album> albums = new ArrayList<>();

        //sample only
        albums.add(new Album("After Laughter", "Paramore"));
        albums.add(new Album("Citrine", "Hayley Kiyoko"));
        albums.add(new Album("Riot!", "Paramore"));

        return albums;

    }
}
