package com.example.paul.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by Paul on 2016-10-10.
 */
public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Chats.db";
    public static final String TABLE_NAME = "chatMessages";
    public static final int dbVersion = 1;
    public static final int KEY_ID = 0;
    public static final String KEY_MESSAGE = "MESSAGE";


    public ChatDatabaseHelper(ChatWindow context) {
        super(context, DATABASE_NAME, null, dbVersion);

    }

    @Override
    public void onCreate(SQLiteDatabase db) { // declaring an SQLLiteDatabase object named db

        db.execSQL("create table " +TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_MESSAGE+" text)" );
        Log.i("ChatDataBaseHelper","Calling OnCreate");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDataBaseHelper","Calling OnUpgrade, oldVersion=" + oldVersion + "newVersion" + newVersion);
    }

    public boolean insertData(String message){
        SQLiteDatabase db = this.getWritableDatabase(); // creating the database
        ContentValues contentValues = new ContentValues();

        try {

            contentValues.put(KEY_MESSAGE,message); //used for inputting rows into the database.
            db.insert(TABLE_NAME, null, contentValues); // insert the data from the contentValues object.

            return true;
        }catch(Exception e){
            return false;
        }
    }

    // Cursor is a storage object that contains rows from a query.
    // method to return the messages inside the database
    public Cursor getMessages(){
        SQLiteDatabase db = this.getWritableDatabase(); // opens the database for read or write. getReadableDatabase() for read only
        Cursor msg = db.rawQuery("select * from " + TABLE_NAME,null);
        return msg;

    }


}
