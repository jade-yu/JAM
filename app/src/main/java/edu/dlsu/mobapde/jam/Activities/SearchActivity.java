package edu.dlsu.mobapde.jam.Activities;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.dlsu.mobapde.jam.R;

public class SearchActivity extends AppCompatActivity {

    //todo tabs

    EditText etSearch;
    ImageView ivSearch;
    TabLayout tabLayout;
    FrameLayout tabContent;
    LinearLayout footer;
    ImageView ivMainAlbum;
    TextView tvMainsong, tvMainartist;
    ImageButton ibBack, ibPlay, ibNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etSearch = findViewById(R.id.et_search);
        ivSearch = findViewById(R.id.iv_search);
        tabLayout = findViewById(R.id.tablayout);
        tabContent = findViewById(R.id.tabcontent);
        footer = findViewById(R.id.footer);
        ivMainAlbum = findViewById(R.id.main_album);
        tvMainsong = findViewById(R.id.tv_mainsong);
        tvMainartist = findViewById(R.id.tv_mainartist);
        ibBack = findViewById(R.id.ib_back);
        ibPlay = findViewById(R.id.ib_play);
        ibNext = findViewById(R.id.ib_next);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        ivMainAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        tvMainsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        tvMainartist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });

        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo put functions
            }
        });
    }
}
