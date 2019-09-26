package com.example.scoredatabaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "MyDatabaseHelper";
    //Define facts about the database to set it up including its name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scores.db";

    //Define tables in the database
    public static final String TABLE_SCORES = "scores";             // table name
    public static final String COLUMN_ID = "_id";                   // unique id for the entry
    public static final String COLUMN_SCORE_NAME = "scorename";
    public static final String COLUMN_SCORE = "score";

    //constructor
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
            int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    /*
    Here we create the specific query needed to create the table.  The syntax is EXTREMELY important.
    The execSQL method is called to execute this request to create the database as defined by the query.
     */

    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_SCORES + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SCORE_NAME + " TEXT, " +  COLUMN_SCORE + " INTEGER )" + ";";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_SCORES;
        db.execSQL(query);
        onCreate(db);
    }


    public void addScore(Scores score) {
        // ContentValues is like a datastructure that allows you to attach values
        // it is similar to how we would put items into an intent

        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE_NAME, score.get_name());
        values.put(COLUMN_SCORE, score.get_score());
        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_SCORES, null, values);  // inserts these values into this table

        Log.d(TAG, "Tried to insert score, result was " + result);

        db.close();             // need to close the database when we are done modifying it.
    }

    // Right now this method will remove all entries with this name
    public void removeScore(String scoreName, int scoreNum) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_SCORES + " WHERE " + COLUMN_SCORE_NAME + " = '" +
                scoreName + "'" + " AND " + COLUMN_SCORE + " = '" + scoreNum + "'";
        db.execSQL(query);
        db.close();
    }



    // This method creates a String representation of all the database elements
    // this is simply for quick viewing of our database contents

    public String databasetoString() {
        String dbstring = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SCORES + " WHERE 1";
        // This means to select all from the database

        // The cursor will extract the entries from the database
        Cursor c = db.rawQuery(query, null);

        // Move the cursor to the first position and then move through the db to the last
        c.moveToFirst();
        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_SCORE_NAME)) != null ) {
                dbstring += c.getString(c.getColumnIndex(COLUMN_SCORE_NAME)) + ", ";
                dbstring += c.getString(c.getColumnIndex(COLUMN_SCORE));
                dbstring += "\n";
            }
            c.moveToNext();
        }

        db.close();
        return dbstring;

    }
}

