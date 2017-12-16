package edu.dlsu.mobapde.jam.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import edu.dlsu.mobapde.jam.R;

public class SearchLyricsActivity extends AppCompatActivity {

    WebView wvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_lyrics);

        wvSearch = findViewById(R.id.wv_search);

        wvSearch.setWebViewClient(new WebViewClient());
        wvSearch.loadUrl("https://google.com");
    }
}
