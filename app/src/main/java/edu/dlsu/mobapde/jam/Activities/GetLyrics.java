package edu.dlsu.mobapde.jam.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import edu.dlsu.mobapde.jam.R;

public class GetLyrics extends AppCompatActivity {

    EditText etGetLyrics;
    Button buttonOkLyrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_lyrics);

        etGetLyrics = findViewById(R.id.et_lyrics);
        buttonOkLyrics = findViewById(R.id.button_ok);


    }
}
