package edu.dlsu.mobapde.jam.RecyclerViewItems;

/**
 * Created by Asus on 15/12/2017.
 */

public class Playlist {

   public static final String TABLE_NAME = "playlist";
   public static final String COLUMN_ID = "_id";
   public static final String COLUMN_NAME = "playlistname";
   public static final String COLUMN_POSITION = "position";

   private long id;
   private String playlistname;
   private int position;

    public Playlist() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlaylistname() {
        return playlistname;
    }

    public void setPlaylistname(String playlistname) {
        this.playlistname = playlistname;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
