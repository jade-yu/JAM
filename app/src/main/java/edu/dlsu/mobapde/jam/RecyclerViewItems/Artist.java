package edu.dlsu.mobapde.jam.RecyclerViewItems;

public class Artist {

    private String artist;
    private int albums;
    private int icon;

    public Artist() {};

    public Artist(String artist, int albums) {
        this.artist = artist;
        this.albums = albums;
        this.icon = -1;
    }

    public Artist(String artist, int albums, int icon) {
        this.artist = artist;
        this.albums = albums;
        this.icon = icon;
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
