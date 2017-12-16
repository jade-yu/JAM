//package edu.dlsu.mobapde.jam.Fragments;
//
//import android.Manifest;
//import android.app.Fragment;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//
//import edu.dlsu.mobapde.jam.Activities.MainActivity;
//import edu.dlsu.mobapde.jam.Activities.PlaySongActivity;
//import edu.dlsu.mobapde.jam.Database.DatabaseHelper;
//import edu.dlsu.mobapde.jam.R;
//import edu.dlsu.mobapde.jam.RecyclerViewItems.Playlist;
//import edu.dlsu.mobapde.jam.RecyclerViewItems.Playlist;
//import edu.dlsu.mobapde.jam.RecyclerViewItems.PlaylistAdapter;
//
//public class PlaylistsFragment extends Fragment {
//
//    RecyclerView rvPlaylists;
//    PlaylistAdapter playlistAdapter;
//
//    public PlaylistsFragment() {
//
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.rv_main, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        rvPlaylists = (RecyclerView) view.findViewById(R.id.rv_main);
//
//        ArrayList<Playlist> playlists = getPlaylists();
//
//        playlistAdapter = new PlaylistAdapter(playlists);
//        rvPlaylists.swapAdapter(playlistAdapter, false);
//
//        playlistAdapter.setOnItemClickListener(new PlaylistAdapter.onItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
////                Log.d("clickedtitle", p.getTitle());
////                Log.d("onItemClick", position + "");
//                Playlist p = playlists.get(position);
//
//                //TODO set onitemclick function
//            }
//        });
//
//        rvPlaylists.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false));
//    }
//
//    public ArrayList<Playlist> getPlaylists() {
//
//        DatabaseHelper db = new DatabaseHelper(getActivity().getBaseContext());
//
//        return db.getPlaylists();
//    }
//
//}