package edu.dlsu.mobapde.jam.RecyclerViewItems;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.R;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    ArrayList<Artist> data;

    public ArtistAdapter(ArrayList<Artist> data) {
        this.data = data;
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
        holder.tvAlbums.setText(currentArtist.getAlbums() + " album");
        if(currentArtist.getAlbums() > 1) {
            holder.tvAlbums.append("s");
        }
        if(currentArtist.getIcon() != -1) {
            holder.ivIcon.setImageResource(currentArtist.getIcon());
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
