package edu.dlsu.mobapde.jam.RecyclerViewItems;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.R;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    ArrayList<Track> data;

    public TrackAdapter(ArrayList<Track> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder {
        TextView tvSong, tvArtist;
        ImageView ivTrack;

        public TrackViewHolder(View itemView) {
            super(itemView);
            tvSong = itemView.findViewById(R.id.tv_song);
            tvArtist = itemView.findViewById(R.id.tv_artist);
            ivTrack = itemView.findViewById(R.id.iv_track);
        }
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false);
        return new TrackViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {

        Track currentTrack = data.get(position);

        holder.tvSong.setText(currentTrack.getTitle());
        holder.tvArtist.setText(currentTrack.getArtist());
        if(currentTrack.getAlbumcover() != -1) {
            holder.ivTrack.setImageResource(currentTrack.getAlbumcover());
        }

        holder.itemView.setTag(currentTrack);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Track t = (Track) view.getTag();
                onItemClickListener.onItemClick(t);
            }
        });
    }

    public interface onItemClickListener {
        public void onItemClick(Track t);
    }

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
