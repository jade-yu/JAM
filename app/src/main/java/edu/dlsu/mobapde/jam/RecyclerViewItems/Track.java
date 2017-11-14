package edu.dlsu.mobapde.jam.RecyclerViewItems;

public class Track {

    private String title;
    private String artist;
    private int albumcover;

    public Track() {};

    public Track(String title, String artist) {
        this.title = title;
        this.artist = artist;
        this.albumcover = -1;
    }

    public Track(String title, String artist, int albumcover) {
        this.title = title;
        this.artist = artist;
        this.albumcover = albumcover;
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
}
