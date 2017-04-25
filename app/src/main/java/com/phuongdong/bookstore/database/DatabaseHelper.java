package com.phuongdong.bookstore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by phuongdong on 4/14/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_AUTHOR = "AUTHOR";
    public static final String TABLE_BOOK = "BOOK";

    // Table AUTHOR Columns
    public static final String _ID_AUTHOR = "_id";
    public static final String CODE_AUTHOR= "codeAuthor";
    public static final String NAME_AUTHOR = "nameAuthor";

    // Table BOOK Columns
    public static final String _ID_BOOK = "_id";
    public static final String CODE_BOOK= "codeBook";
    public static final String NAME_BOOK = "nameBook";
    public static final String DATE_PUBLISHED = "datePublished";
    public static final String AUTHOR_ID= "authorId";

    // Database Information
    public static final String DB_NAME = "BOOK_STORE.DB";

    // database version
    public static final int DB_VERSION = 1;

    // Creating table author query
    private static final String CREATE_TABLE_AUTHOR = "create table " + TABLE_AUTHOR + "(" + _ID_AUTHOR
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CODE_AUTHOR + " TEXT NOT NULL, " + NAME_AUTHOR + " TEXT NOT NULL);";

    // Creating table book query
    private static final String CREATE_TABLE_BOOK = "create table " + TABLE_BOOK + "(" + _ID_BOOK
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CODE_BOOK + " TEXT NOT NULL, " + NAME_BOOK + " TEXT NOT NULL, " + DATE_PUBLISHED
            + " TEXT NOT NULL, " + AUTHOR_ID + " INTEGER NOT NULL, FOREIGN KEY(" + AUTHOR_ID + ") REFERENCES " + TABLE_AUTHOR + "(" + _ID_AUTHOR + "));";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_AUTHOR);
        db.execSQL(CREATE_TABLE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);
        onCreate(db);
    }
}
