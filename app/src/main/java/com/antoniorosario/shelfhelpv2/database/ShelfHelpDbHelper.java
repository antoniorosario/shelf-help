package com.antoniorosario.shelfhelpv2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class ShelfHelpDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "book.db";
    private static final int DATABASE_VERSION = 1;

    //TODO Create table for author and publisher
    private static final String SQL_CREATE_BOOK_TABLE = "CREATE TABLE " + ShelfHelpContract.BookEntry.TABLE_NAME + "(" +
            ShelfHelpContract.BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ShelfHelpContract.BookEntry.COLUMN_BOOK_TITLE + " TEXT NOT NULL, " +
            ShelfHelpContract.BookEntry.COLUMN_BOOK_AUTHOR_NAME + " TEXT, " +
            ShelfHelpContract.BookEntry.COLUMN_BOOK_SUBTITLE + " TEXT, " +
            ShelfHelpContract.BookEntry.COLUMN_BOOK_DESCRIPTION + " TEXT," +
            ShelfHelpContract.BookEntry.COLUMN_PUBLISHER + " TEXT, " +
            ShelfHelpContract.BookEntry.COLUMN_PUBLISHED_DATE + " TEXT, " +
            ShelfHelpContract.BookEntry.COLUMN_BOOK_THUMBNAIL_URL + " TEXT, " +
            ShelfHelpContract.BookEntry.COLUMN_BOOK_READ_STATUS + " INTEGER NOT NULL);";

    public ShelfHelpDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
