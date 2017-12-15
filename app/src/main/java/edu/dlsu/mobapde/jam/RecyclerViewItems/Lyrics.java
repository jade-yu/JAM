package edu.dlsu.mobapde.jam.RecyclerViewItems;

import java.util.ArrayList;

/**
 * Created by Asus on 08/12/2017.
 */

public class Lyrics {

    public static final String TABLE_NAME = "lyrics";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LYRICS = "lyrics";
    public static final String COLUMN_TRACKID = "trackid";

    private String wholeLyrics;
    private int trackID;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTrackID() {
        return trackID;
    }

    public void setTrackID(int trackID) {
        this.trackID = trackID;
    }

    public Lyrics(String wholeLyrics) {
        this.wholeLyrics = wholeLyrics;
    }

    public Lyrics() {}

    public String getWholeLyrics() {
        return wholeLyrics;
    }

    public void setWholeLyrics(String wholeLyrics) {
        this.wholeLyrics = wholeLyrics;
    }

}
