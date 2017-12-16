package edu.dlsu.mobapde.jam.RecyclerViewItems;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by Asus on 08/12/2017.
 */

public class Lyrics {

    public static final String TABLE_NAME = "lyrics";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LYRICS = "lyrics";
    public static final String COLUMN_TRACKID = "trackid";
    public static final String COLUMN_TIMESTART = "timestart";

    private String lyric;
    private long trackID;
    private long id;
    private String timestart;

    public String getTimestart() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart = timestart;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTrackID() {
        return trackID;
    }

    public void setTrackID(long trackID) {
        this.trackID = trackID;
    }

    public Lyrics(String lyric) {
        this.lyric = lyric;
    }

    public Lyrics() {}

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

}
