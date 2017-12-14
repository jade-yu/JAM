package edu.dlsu.mobapde.jam.RecyclerViewItems;

public class Album {

    private String title;
    private String artist;
    private long id;
    private String albumart;
    private int tracks;

    public Album() {};

    public Album(String title, String artist) {
        this.title = title;
        this.artist = artist;
        this.id = -1;
    }

    public Album(String title, String artist, long id, String albumart) {
        this.title = title;
        this.artist = artist;
        this.id = id;
        this.albumart = albumart;
    }

    public Album(String title, String artist, long id) {
        this.title = title;
        this.artist = artist;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlbumart() {return albumart;}

    public void setAlbumart(String albumart) {this.albumart = albumart;}

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }
}
