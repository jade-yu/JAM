package edu.dlsu.mobapde.jam.RecyclerViewItems;

public class Artist {

    private long id;
    private String artist;
    private int albums;
    private int tracks;
    private int icon;

    public Artist() {};

    public Artist(long id, String artist, int albums, int tracks) {
        this.id = id;
        this.artist = artist;
        this.albums = albums;
        this.tracks = tracks;
        this.icon = -1;
    }

    public Artist(long id, String artist, int albums, int tracks, int icon) {
        this.id = id;
        this.artist = artist;
        this.albums = albums;
        this.tracks = tracks;
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getAlbums() {
        return albums;
    }

    public void setAlbums(int albums) {
        this.albums = albums;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
