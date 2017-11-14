package edu.dlsu.mobapde.jam.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.dlsu.mobapde.jam.R;

public class MainActivity extends AppCompatActivity {

    EditText et_search;
    ImageView iv_search;
    RelativeLayout footer;
    ImageView main_album;
    TextView tv_mainsong;
    TextView tv_mainartist;
    ImageButton ib_back;
    ImageButton ib_play;
    ImageButton ib_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_search = findViewById(R.id.et_search);
        iv_search = findViewById(R.id.iv_search);
        footer = findViewById(R.id.footer);
        main_album = findViewById(R.id.main_album);
        tv_mainsong = findViewById(R.id.tv_mainsong);
        tv_mainartist = findViewById(R.id.tv_mainartist);
        ib_back = findViewById(R.id.ib_back);
        ib_play = findViewById(R.id.ib_play);
        ib_next = findViewById(R.id.ib_next);

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        main_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        tv_mainsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        tv_mainartist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        ib_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        ib_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });
    }
}
