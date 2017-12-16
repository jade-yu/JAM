package edu.dlsu.mobapde.jam.RecyclerViewItems;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.R;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    ArrayList<Artist> data;
    ArrayList<String> icons;

    public ArtistAdapter(ArrayList<Artist> data, ArrayList<String> icons) {
        this.data = data;
        this.icons = icons;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {
        TextView tvArtist, tvAlbums;
        ImageView ivIcon;

        public ArtistViewHolder(View itemView) {
            super(itemView);
            tvArtist = itemView.findViewById(R.id.tv_itemartist);
            tvAlbums = itemView.findViewById(R.id.tv_albumnum);
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);
        return new ArtistViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {

        Artist currentArtist = data.get(position);
        holder.tvArtist.setText(currentArtist.getArtist());

        String albumtext = currentArtist.getAlbums() + " album";
        if(currentArtist.getAlbums() > 1) {
            albumtext += "s";
        }
        albumtext += ", " + currentArtist.getTracks() + " track";
        if(currentArtist.getTracks() > 1) {
            albumtext += "s";
        }

        holder.tvAlbums.setText(albumtext);

        if (icons.get(position) == null) {
            Log.d("onBindViewHolder", currentArtist.getArtist() + " : null album");
            holder.ivIcon.setImageResource(R.drawable.noalbums);
        } else {
            Log.d("onBindViewHolder", currentArtist.getArtist() + " : " + icons.get(position));
            Drawable img = Drawable.createFromPath(icons.get(position));
            holder.ivIcon.setImageDrawable(img);
        }

        holder.itemView.setTag(currentArtist);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Artist t = (Artist) view.getTag();
                onItemClickListener.onItemClick(t);
            }
        });
    }

    public interface onItemClickListener {
        public void onItemClick(Artist t);
    }

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
