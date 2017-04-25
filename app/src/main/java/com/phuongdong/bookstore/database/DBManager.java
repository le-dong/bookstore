package com.phuongdong.bookstore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by phuongdong on 4/14/17.
 */

public class DBManager {

    private DatabaseHelper databaseHelper;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DBManager(Context context){
        this.context = context;
    }

    public DBManager open() throws SQLException{
        databaseHelper = new DatabaseHelper(this.context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }


    //handle crud table author
    public void insertAuthor(String codeAuthor, String nameAuthor) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.CODE_AUTHOR, codeAuthor);
        contentValue.put(DatabaseHelper.NAME_AUTHOR, nameAuthor);
        sqLiteDatabase.insert(DatabaseHelper.TABLE_AUTHOR, null, contentValue);
    }

    public Cursor fetchAuthor() {
        String[] columns = new String[] { DatabaseHelper._ID_AUTHOR, DatabaseHelper.CODE_AUTHOR, DatabaseHelper.NAME_AUTHOR };
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_AUTHOR, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int updateAuthor(long _id, String codeAuthor, String nameAuthor) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CODE_AUTHOR, codeAuthor);
        contentValues.put(DatabaseHelper.NAME_AUTHOR, nameAuthor);
        int i = sqLiteDatabase.update(DatabaseHelper.TABLE_AUTHOR, contentValues, DatabaseHelper._ID_AUTHOR + " = " + _id, null);
        return i;
    }

    public void deleteAuthor(long _id) {
        sqLiteDatabase.delete(DatabaseHelper.TABLE_AUTHOR, DatabaseHelper._ID_AUTHOR + " = " + _id, null);
        sqLiteDatabase.delete(DatabaseHelper.TABLE_BOOK, DatabaseHelper.AUTHOR_ID + " = " + _id, null);
    }

    //handle crud table book
    public void insertBook(String codeBook, String nameBook, String datePublished, String authorId) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.CODE_BOOK, codeBook);
        contentValue.put(DatabaseHelper.NAME_BOOK, nameBook);
        contentValue.put(DatabaseHelper.DATE_PUBLISHED, datePublished);
        contentValue.put(DatabaseHelper.AUTHOR_ID, authorId);
        sqLiteDatabase.insert(DatabaseHelper.TABLE_BOOK, null, contentValue);
    }

    public Cursor fetchBook() {
        String[] columns = new String[] { DatabaseHelper._ID_BOOK, DatabaseHelper.CODE_BOOK, DatabaseHelper.NAME_BOOK, DatabaseHelper.DATE_PUBLISHED, DatabaseHelper.AUTHOR_ID };
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_BOOK, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int updateBook(long _id, String codeBook, String nameBook, String datePublished, String authorId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CODE_BOOK, codeBook);
        contentValues.put(DatabaseHelper.NAME_BOOK, nameBook);
        contentValues.put(DatabaseHelper.DATE_PUBLISHED, datePublished);
        contentValues.put(DatabaseHelper.AUTHOR_ID, authorId);
        int i = sqLiteDatabase.update(DatabaseHelper.TABLE_BOOK, contentValues, DatabaseHelper._ID_AUTHOR + " = " + _id, null);
        return i;
    }

    public void deleteBook(long _id) {
        sqLiteDatabase.delete(DatabaseHelper.TABLE_BOOK, DatabaseHelper._ID_BOOK + " = " + _id, null);
    }
}
