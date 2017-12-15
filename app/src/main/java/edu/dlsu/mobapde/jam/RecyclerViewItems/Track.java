package edu.dlsu.mobapde.jam.RecyclerViewItems;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {


    public static final String TABLE_NAME = "track";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ARTIST = "artist";
    public static final String COLUMN_ALBUM = "album";
    public static final String COLUMN_SAVED = "saved";

    private long id;
    private String title;
    private String artist;
    private int album;
    private int duration;
    private boolean saved;


    public Track() {};

    public Track(long id, String title, String artist, int duration) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = -1;
        this.duration = duration;
    }

    public Track(long id, String title, String artist, int album, int duration) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    public boolean getSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getAlbum() {
        return album;
    }

    public void setAlbum(int album) {
        this.album = album;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeInt(album);
        dest.writeInt(duration);
    }

    protected Track(Parcel in) {
        id = in.readLong();
        title = in.readString();
        artist = in.readString();
        album = in.readInt();
        duration = in.readInt();
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
