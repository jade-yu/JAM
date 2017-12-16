package edu.dlsu.mobapde.jam.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.dlsu.mobapde.jam.Database.DatabaseHelper;
import edu.dlsu.mobapde.jam.R;
import edu.dlsu.mobapde.jam.RecyclerViewItems.Track;

public class GetLyrics extends AppCompatActivity {

    EditText etGetLyrics;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_lyrics);

        etGetLyrics = findViewById(R.id.et_lyrics);
        btnOk = findViewById(R.id.button_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Track t = getIntent().getParcelableExtra("currentTrack");

                Intent i = new Intent();
                String lyrics = etGetLyrics.getText().toString();
//                i.putExtra("lyrics", lyrics);
//                Log.d("lyricss", "Valure" + lyrics);

                DatabaseHelper db = new DatabaseHelper(getBaseContext());
                db.addLyrics(lyrics, t.getId());

                setResult(RESULT_OK, i);
//                Log.d("GetLyrics", "lyrics ok");
                finish();
            }
        });
    }
}
