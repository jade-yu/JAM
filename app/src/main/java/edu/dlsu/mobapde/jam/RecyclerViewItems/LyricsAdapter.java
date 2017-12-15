package edu.dlsu.mobapde.jam.RecyclerViewItems;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.R;

/**
 * Created by Asus on 08/12/2017.
 */

public class LyricsAdapter extends RecyclerView.Adapter<LyricsAdapter.LyricsViewHolder> {

    ArrayList<String> lineLyrics = new ArrayList<>();

    public LyricsAdapter(String lyrics) {
        toArrayList(lyrics);

    }

    public void toArrayList(String s) {


    }

    @Override
    public LyricsAdapter.LyricsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_lyric, parent, false);
        return new LyricsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LyricsAdapter.LyricsViewHolder holder, int position) {

        String currentLyrics = lineLyrics.get(position);

        holder.tvLyrics.setText(currentLyrics);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class LyricsViewHolder extends RecyclerView.ViewHolder {
        TextView tvLyrics;

        public LyricsViewHolder(View itemView) {
            super(itemView);
            tvLyrics = itemView.findViewById(R.id.tv_lyrics);

        }
    }
}
