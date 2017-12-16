package edu.dlsu.mobapde.jam.RecyclerViewItems;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.dlsu.mobapde.jam.R;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    ArrayList<Album> data;

    public AlbumAdapter(ArrayList<Album> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        TextView tvAlbum, tvArtist;
        ImageView ivIcon;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            tvAlbum = itemView.findViewById(R.id.tv_albumname);
            tvArtist = itemView.findViewById(R.id.tv_albumartist);
            ivIcon = itemView.findViewById(R.id.iv_album);
        }
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new AlbumViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {

        Album currentAlbum = data.get(position);
        holder.tvAlbum.setText(currentAlbum.getTitle());
        holder.tvArtist.setText(currentAlbum.getArtist());
        if(currentAlbum.getId() != -1) {
            if (currentAlbum.getAlbumart() == null) {

                holder.ivIcon.setImageResource(R.drawable.noalbums);

            } else {
                Drawable img = Drawable.createFromPath(currentAlbum.getAlbumart());
                holder.ivIcon.setImageDrawable(img);
            }
        }

        holder.itemView.setTag(currentAlbum);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Album t = (Album) view.getTag();
                onItemClickListener.onItemClick(t);
            }
        });
    }

    public interface onItemClickListener {
        public void onItemClick(Album t);
    }

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
