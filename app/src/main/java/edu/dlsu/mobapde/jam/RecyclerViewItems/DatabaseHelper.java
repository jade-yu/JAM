package edu.dlsu.mobapde.jam.RecyclerViewItems;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Asus on 14/12/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String SCHEMA = "jam";
    public static final String TRACKANDPLAYLIST = "trackandplaylist";
    public static final String TRACKANDLYRICS = "trackandlyrics";
    public static final String COLUMN_TRACKID = "trackid";
    public static final String COLUMN_PLAYLISTID = "playlistid";
    public static final String COLUMN_LYRICID = "lyricid";
    public static final int VERSION = 1;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, SCHEMA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String track = "CREATE TABLE " + Track.TABLE_NAME + " ("
                + Track.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Track.COLUMN_TITLE + " TEXT,"
                + Track.COLUMN_ARTIST + " TEXT,"
                + Track.COLUMN_ALBUM + " TEXT,"
                + Track.COLUMN_SAVED + " INTEGER"
                + ");";

        sqLiteDatabase.execSQL(track);

        String lyrics = "CREATE TABLE " + Lyrics.TABLE_NAME + " ("
                + Lyrics.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Lyrics.COLUMN_LYRICS + " TEXT,"
                + Lyrics.COLUMN_ID + " INTEGER PRIMARY KEY"
                + Lyrics.COLUMN_TIMESTART + "TIME"
                + ");";

        sqLiteDatabase.execSQL(lyrics);

        String playlist = "CREATE TABLE " + Playlist.TABLE_NAME + " ("
                + Playlist.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Playlist.COLUMN_NAME + " TEXT,"
                + Playlist.COLUMN_POSITION + " INTEGER"
                + ");";

        sqLiteDatabase.execSQL(playlist);

        String trackandplaylist = "CREATE TABLE " + TRACKANDPLAYLIST + " ("
                + COLUMN_TRACKID + " INTEGER,"
                + COLUMN_PLAYLISTID + " INTEGER"
                + ");";

        sqLiteDatabase.execSQL(trackandplaylist);

        String trackandlyrics = "CREATE TABLE " + TRACKANDLYRICS + " ("
                + COLUMN_TRACKID + " INTEGER,"
                + COLUMN_LYRICID + " INTEGER"
                + ");";

        sqLiteDatabase.execSQL(trackandlyrics);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String track = "DROP TABLE IF EXISTS " + Track.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(track);
        String lyrics = "DROP TABLE IF EXISTS " + Lyrics.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(lyrics);
        String playlist = "DROP TABLE IF EXISTS " + Playlist.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(playlist);
        String trackandplaylist = "DROP TABLE IF EXISTS " + TRACKANDPLAYLIST + ";";
        sqLiteDatabase.execSQL(trackandplaylist);
        String trackandlyrics = "DROP TABLE IF EXISTS " + TRACKANDLYRICS + ";";
        sqLiteDatabase.execSQL(trackandlyrics);

        onCreate(sqLiteDatabase);
    }

    public long addTrack(Track track){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Track.COLUMN_TITLE, track.getTitle());
        contentValues.put(Track.COLUMN_ARTIST, track.getArtist());
        contentValues.put(Track.COLUMN_ALBUM, track.getAlbumcover());
        if(track.getSaved() == true) {
            contentValues.put(Track.COLUMN_SAVED, 1);
        } else {
            contentValues.put(Track.COLUMN_SAVED, 0);
        }

        long id = db.insert(Track.TABLE_NAME, null, contentValues);
        db.close();
        return id;
    }

    public long addLyrics(Lyrics lyrics){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Lyrics.COLUMN_LYRICS, lyrics.getLyric());
        contentValues.put(Lyrics.COLUMN_TRACKID, lyrics.getTrackID());
        contentValues.put(Lyrics.COLUMN_TIMESTART, lyrics.getTimestart());

        long id = db.insert(Lyrics.TABLE_NAME, null, contentValues);
        db.close();
        return id;
    }

    public long addPlaylist(Playlist playlist){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Playlist.COLUMN_NAME, playlist.getPlaylistname());
        contentValues.put(Playlist.COLUMN_POSITION, playlist.getPosition());

        long id = db.insert(Playlist.TABLE_NAME, null, contentValues);
        db.close();
        return id;
    }

    public void addToPlaylist(long playlistid, long trackid){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYLISTID, playlistid);
        contentValues.put(COLUMN_TRACKID, trackid);

        db.insert(TRACKANDPLAYLIST, null, contentValues);
        db.close();
    }

    public void addLyricstoTrack(long trackid, long lyricid){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LYRICID, lyricid);
        contentValues.put(COLUMN_TRACKID, trackid);

        db.insert(TRACKANDLYRICS, null, contentValues);
        db.close();
    }

    public boolean deleteTrack(long id){
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = db.delete(Track.TABLE_NAME, Track.COLUMN_ID + "=?", new String[]{id+""} );
        db.close();
        return rowsAffected > 0;
    }

    public boolean deleteLyrics(long id){
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = db.delete(Lyrics.TABLE_NAME, Lyrics.COLUMN_ID + "=?", new String[]{id+""} );
        db.close();
        return rowsAffected > 0;
    }

    public boolean deletePlaylist(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = db.delete(Playlist.TABLE_NAME, Playlist.COLUMN_ID + "=?", new String[]{id+""} );
        db.close();
        return rowsAffected > 0;
    }

    public boolean removeTrackfromPlaylist(long playlistid, long trackid){
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = db.delete(TRACKANDPLAYLIST, COLUMN_PLAYLISTID + "=? AND " + COLUMN_TRACKID + "=?", new String[]{String.valueOf(playlistid), String.valueOf(trackid)});
        db.close();
        return rowsAffected > 0;
    }

    public boolean removeLyricsfromTrack(long trackid){
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = db.delete(TRACKANDLYRICS,  COLUMN_TRACKID + "=?", new String[]{trackid+""});
        db.close();
        return rowsAffected > 0;
    }

    public Track getTrack(long id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Track.TABLE_NAME,
                null,
                Track.COLUMN_ID + "=?",
                new String[]{id+""},
                null,
                null,
                null);
        Track t = null;
        if(c.moveToFirst()){
            t = new Track();
            t.setTitle(c.getString(c.getColumnIndex(Track.COLUMN_TITLE)));
            t.setArtist(c.getString(c.getColumnIndex(Track.COLUMN_ARTIST)));
            t.setAlbumcover(c.getInt(c.getColumnIndex(Track.COLUMN_ALBUM)));
            if(c.getInt(c.getColumnIndex(Track.COLUMN_SAVED)) == 1){
                t.setSaved(true);
            }else{
                t.setSaved(false);
            }
            t.setId(id);
        }

        c.close();
        db.close();

        return t;
    }

    public Lyrics getLyrics(long id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Lyrics.TABLE_NAME,
                null,
                Lyrics.COLUMN_ID + "=?",
                new String[]{id+""},
                null,
                null,
                null);
        Lyrics l = null;
        if(c.moveToFirst()){
            l = new Lyrics();
            l.setLyric(c.getString(c.getColumnIndex(Lyrics.COLUMN_LYRICS)));
            l.setTrackID(c.getInt(c.getColumnIndex(Lyrics.COLUMN_ID)));
            l.setTimestart(c.getString(c.getColumnIndex(Lyrics.COLUMN_TIMESTART)));
            l.setId(id);
        }

        c.close();
        db.close();

        return l;
    }

    public Playlist getPlaylist(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Playlist.TABLE_NAME,
                null,
                Playlist.COLUMN_ID + "=?",
                new String[]{id+""},
                null,
                null,
                null);
        Playlist p = null;
        if(c.moveToFirst()){
            p = new Playlist();
            p.setPlaylistname(c.getString(c.getColumnIndex(Playlist.COLUMN_NAME)));
            p.setPosition(c.getInt(c.getColumnIndex(Playlist.COLUMN_POSITION)));
            p.setId(id);
        }

        c.close();
        db.close();

        return p;
    }

    public Cursor getAllTracksCursor(){
        SQLiteDatabase db = getReadableDatabase();

        return db.query(Track.TABLE_NAME, null, null, null, null, null, null );
    }

    public Cursor getAllLyricsCursor(){
        SQLiteDatabase db = getReadableDatabase();

        return db.query(Lyrics.TABLE_NAME, null, null, null, null, null, null );
    }

    public Cursor getAllPlaylistCursor() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(Playlist.TABLE_NAME, null, null, null, null, null, null );
    }

    public Cursor getAllTracksfromPlaylist(long playlistid){
        SQLiteDatabase db = getReadableDatabase();

        return db.query(TRACKANDPLAYLIST, null, COLUMN_PLAYLISTID + "=?", new String[]{playlistid+""}, null, null, null);
    }

    public Cursor getAllLyrics(long trackid){
        SQLiteDatabase db = getReadableDatabase();

        return db.query(TRACKANDLYRICS, null, COLUMN_TRACKID + "=?", new String[]{trackid+""}, null, null, null);
    }
}
