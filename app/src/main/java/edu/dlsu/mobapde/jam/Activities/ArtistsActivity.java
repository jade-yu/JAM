package edu.dlsu.mobapde.jam.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import edu.dlsu.mobapde.jam.R;

public class ArtistsActivity extends AppCompatActivity {

    RecyclerView rv_artists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

        rv_artists = findViewById(R.id.rv_artists);

        //todo put onitemonclicklistener
    }
}
