package edu.dlsu.mobapde.jam.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.R;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Artist;
import edu.dlsu.mobapde.jam.RecyclerViewItems.ArtistAdapter;

public class ArtistsFragment extends Fragment {

    RecyclerView rvArtists;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView", "onCreateView: started");
        return inflater.inflate(R.layout.activity_tracks, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //TODO fix recyclerView implementation
        rvArtists = (RecyclerView) view.findViewById(R.id.rv_artists);

        ArrayList<Artist> artists = getArtists();

        Log.d("artistsize", artists.size() + "");

        ArtistAdapter artistAdapter = new ArtistAdapter(artists);
        rvArtists.setAdapter(artistAdapter);

        artistAdapter.setOnItemClickListener(new ArtistAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Artist a) {
                //TODO onItemClick implementation
                Log.d("clickedartist", a.getArtist());
            }
        });

        rvArtists.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false));
    }

    //TODO getArtists implementation
    public ArrayList<Artist> getArtists() {

        ArrayList<Artist> artists = new ArrayList<>();

        //sample only
        artists.add(new Artist("Hayley Kiyoko", 3));
        artists.add(new Artist("Paramore", 5));

        return artists;

    }
    
}
