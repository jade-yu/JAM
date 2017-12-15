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
    private int albumcover;
    private int duration;
    private int saved;


    public Track() {};

    public Track(long id, String title, String artist, int duration) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.albumcover = -1;
        this.duration = duration;
    }

    public Track(long id, String title, String artist, int albumcover, int duration) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.albumcover = albumcover;
        this.duration = duration;
    }

    public int getSaved() {
        return saved;
    }

    public void setSaved(int saved) {
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

    public int getAlbumcover() {
        return albumcover;
    }

    public void setAlbumcover(int albumcover) {
        this.albumcover = albumcover;
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
        dest.writeInt(albumcover);
        dest.writeInt(duration);
    }

    protected Track(Parcel in) {
        id = in.readLong();
        title = in.readString();
        artist = in.readString();
        albumcover = in.readInt();
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
