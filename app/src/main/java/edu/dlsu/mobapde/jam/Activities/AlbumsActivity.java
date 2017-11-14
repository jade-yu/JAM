package edu.dlsu.mobapde.jam.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.R;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Album;

public class AlbumsActivity extends AppCompatActivity {

    RecyclerView rv_albums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        rv_albums = findViewById(R.id.rv_albums);

        //todo add onitemclicklistener

    }
}
