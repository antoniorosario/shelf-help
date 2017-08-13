package com.antoniorosario.shelfhelpv2.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class ShelfHelpProvider extends ContentProvider {

    private static final int BOOKS = 100;
    private static final int BOOK_ID = 101;
    private static final String LOG_TAG = ShelfHelpProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ShelfHelpContract.CONTENT_AUTHORITY, ShelfHelpContract.PATH_BOOKS, BOOKS);

        sUriMatcher.addURI(ShelfHelpContract.CONTENT_AUTHORITY, ShelfHelpContract.PATH_BOOKS + "/#", BOOK_ID);
    }

    private ShelfHelpDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new ShelfHelpDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                cursor = database.query(ShelfHelpContract.BookEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ID:
                selection = ShelfHelpContract.BookEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(ShelfHelpContract.BookEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(
                getContext().getContentResolver(),
                ShelfHelpContract.BookEntry.CONTENT_URI);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                return ShelfHelpContract.BookEntry.CONTENT_LIST_TYPE;
            case BOOK_ID:
                return ShelfHelpContract.BookEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                return insertBook(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertBook(Uri uri, ContentValues values) {

        String bookTitle = values.getAsString(ShelfHelpContract.BookEntry.COLUMN_BOOK_TITLE);
        if (bookTitle == null) {
            throw new IllegalArgumentException("Book requires a title");
        }

        String authorName = values.getAsString(ShelfHelpContract.BookEntry.COLUMN_BOOK_AUTHOR_NAME);
        if (authorName == null) {
            throw new IllegalArgumentException("Book requires an author name");
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long id = database.insert(ShelfHelpContract.BookEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOK_ID:
                selection = ShelfHelpContract.BookEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateBooks(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateBooks(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Check that the title is not null
        if (values.containsKey(ShelfHelpContract.BookEntry.COLUMN_BOOK_TITLE)) {
            String name = values.getAsString(ShelfHelpContract.BookEntry.COLUMN_BOOK_TITLE);
            if (name == null) {
                throw new IllegalArgumentException("Book requires a title");
            }
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int rowsUpdated = database.update(ShelfHelpContract.BookEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(ShelfHelpContract.BookEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case BOOK_ID:
                // Delete a single row given by the ID in the URI
                selection = ShelfHelpContract.BookEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ShelfHelpContract.BookEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }
}
