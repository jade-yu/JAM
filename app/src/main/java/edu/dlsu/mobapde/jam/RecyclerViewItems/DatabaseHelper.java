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
                + Track.COLUMN_SAVED + " TEXT"
                + ");";

        sqLiteDatabase.execSQL(track);

        String lyrics = "CREATE TABLE " + Lyrics.TABLE_NAME + " ("
                + Lyrics.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Lyrics.COLUMN_LYRICS + " TEXT,"
                + Lyrics.COLUMN_ID + " ID"
                + ");";

        sqLiteDatabase.execSQL(lyrics);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String track = "DROP TABLE IF EXISTS " + Track.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(track);
        String lyrics = "DROP TABLE IF EXISTS " + Lyrics.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(lyrics);

        onCreate(sqLiteDatabase);
    }

    public long addTrack(Track track){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Track.COLUMN_TITLE, track.getTitle());
        contentValues.put(Track.COLUMN_ARTIST, track.getArtist());
        contentValues.put(Track.COLUMN_ALBUM, track.getAlbumcover());
        contentValues.put(Track.COLUMN_SAVED, track.getDuration());

        long id = db.insert(Track.TABLE_NAME, null, contentValues);
        db.close();
        return id;
    }

    public long addLyrics(Lyrics lyrics){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Lyrics.COLUMN_LYRICS, lyrics.getWholeLyrics());
        contentValues.put(Lyrics.COLUMN_TRACKID, lyrics.getTrackID());

        long id = db.insert(Lyrics.TABLE_NAME, null, contentValues);
        db.close();
        return id;
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
            t.setDuration(c.getInt(c.getColumnIndex(Track.COLUMN_SAVED)));
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
            l.setWholeLyrics(c.getString(c.getColumnIndex(Lyrics.COLUMN_LYRICS)));
            l.setTrackID(c.getInt(c.getColumnIndex(Lyrics.COLUMN_ID)));
            l.setId(id);
        }

        c.close();
        db.close();

        return l;
    }

    public Cursor getAllTracksCursor(){
        SQLiteDatabase db = getReadableDatabase();

        return db.query(Track.TABLE_NAME, null, null, null, null, null, null );
    }

    public Cursor getAllLyricsCursor(){
        SQLiteDatabase db = getReadableDatabase();

        return db.query(Lyrics.TABLE_NAME, null, null, null, null, null, null );
    }
}
