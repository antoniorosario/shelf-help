package com.antoniorosario.shelfhelpv2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.antoniorosario.shelfhelpv2.models.Book;

import java.util.ArrayList;
import java.util.List;

public class ShelfHelpDataSource {

    private Context context;

    public ShelfHelpDataSource(Context context) {
        this.context = context;
    }

    public void addBook(Book currentBook, int bookStatus) {
        ContentValues values = new ContentValues();
        values.put(ShelfHelpContract.BookEntry.COLUMN_BOOK_TITLE, currentBook.getTitle().trim());
        values.put(ShelfHelpContract.BookEntry.COLUMN_BOOK_AUTHOR_NAME, currentBook.getAuthorName().trim());
        values.put(ShelfHelpContract.BookEntry.COLUMN_BOOK_THUMBNAIL_URL, currentBook.getThumbnailUrl().trim());
        values.put(ShelfHelpContract.BookEntry.COLUMN_BOOK_DESCRIPTION, currentBook.getDescription().trim());
        values.put(ShelfHelpContract.BookEntry.COLUMN_BOOK_SUBTITLE, currentBook.getSubtitle().trim());
        values.put(ShelfHelpContract.BookEntry.COLUMN_PUBLISHED_DATE, currentBook.getPublishedDate().trim());
        values.put(ShelfHelpContract.BookEntry.COLUMN_PUBLISHER, currentBook.getPublisher().trim());
        values.put(ShelfHelpContract.BookEntry.COLUMN_BOOK_READ_STATUS, bookStatus);
        Uri newUri = context.getContentResolver().insert(ShelfHelpContract.BookEntry.CONTENT_URI, values);

        if (newUri != null) {
            Toast.makeText(context, "Book saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateBook(int bookStatus, Uri currentBookUri) {
        ContentValues values = new ContentValues();
        values.put(ShelfHelpContract.BookEntry.COLUMN_BOOK_READ_STATUS, bookStatus);
        context.getContentResolver().update(currentBookUri, values, null, null);
        if (bookStatus == ShelfHelpContract.BookEntry.BOOK_CURRENTLY_READING) {
            Toast.makeText(context, "Congratulations on starting a new book!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Congratulations on finishing a book!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteBook(Uri currentBookUri) {
        int rowsDeleted = context.getContentResolver().delete(currentBookUri, null, null);
        if (rowsDeleted > 0) {
            Toast.makeText(context, "Book deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public Book getBookInfo(Cursor cursor) {
        int bookTitleIndex = cursor.getColumnIndex(ShelfHelpContract.BookEntry.COLUMN_BOOK_TITLE);
        int authorNameIndex = cursor.getColumnIndex(ShelfHelpContract.BookEntry.COLUMN_BOOK_AUTHOR_NAME);
        int thumbnailUrlIndex = cursor.getColumnIndex(ShelfHelpContract.BookEntry.COLUMN_BOOK_THUMBNAIL_URL);
        int subtitleIndex = cursor.getColumnIndex(ShelfHelpContract.BookEntry.COLUMN_BOOK_SUBTITLE);
        int descriptionIndex = cursor.getColumnIndex(ShelfHelpContract.BookEntry.COLUMN_BOOK_DESCRIPTION);
        int publishedDateIndex = cursor.getColumnIndex(ShelfHelpContract.BookEntry.COLUMN_PUBLISHED_DATE);
        int publisherIndex = cursor.getColumnIndex(ShelfHelpContract.BookEntry.COLUMN_PUBLISHER);

        String currentBookTitle = cursor.getString(bookTitleIndex);
        String currentAuthorName = cursor.getString(authorNameIndex);
        String currentThumbnailUrl = cursor.getString(thumbnailUrlIndex);
        String currentSubtitle = cursor.getString(subtitleIndex);
        String currentDescription = cursor.getString(descriptionIndex);
        String currentPublishedDate = cursor.getString(publishedDateIndex);
        String currentPublisher = cursor.getString(publisherIndex);

        return new Book(currentBookTitle, currentAuthorName, currentThumbnailUrl, currentSubtitle, currentPublisher
                , currentDescription, currentPublishedDate);
    }

    public Book getSingleBook(Uri bookUri) {

        List<Book> books = new ArrayList<>();

        String[] projection = {
                ShelfHelpContract.BookEntry._ID,
                ShelfHelpContract.BookEntry.COLUMN_BOOK_TITLE,
                ShelfHelpContract.BookEntry.COLUMN_BOOK_AUTHOR_NAME,
        };

        Cursor cursor = context.getContentResolver().query
                (bookUri, projection, null, null, null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    Book book = new Book(
                            cursor.getString(cursor.getColumnIndex(ShelfHelpContract.BookEntry.COLUMN_BOOK_TITLE)),
                            cursor.getString(cursor.getColumnIndex(ShelfHelpContract.BookEntry.COLUMN_BOOK_AUTHOR_NAME)),
                            null, null, null, null, null
                    );
                    books.add(book);
                }
            } finally {
                cursor.close();
            }
        }
        return books.get(0);
    }

    //TODO use this method to get a count of books per shelf
    public List<Book> getAllBooks(String bookStatus) {

        List<Book> books = new ArrayList<>();

        String[] projection = {
                ShelfHelpContract.BookEntry._ID,
                ShelfHelpContract.BookEntry.COLUMN_BOOK_TITLE,
                ShelfHelpContract.BookEntry.COLUMN_BOOK_THUMBNAIL_URL,
                ShelfHelpContract.BookEntry.COLUMN_BOOK_READ_STATUS
        };

        String selection = ShelfHelpContract.BookEntry.COLUMN_BOOK_READ_STATUS + " = ?";
        String[] selectionArgs = {bookStatus};
        Cursor cursor = context.getContentResolver().query
                (ShelfHelpContract.BookEntry.CONTENT_URI, projection, selection, selectionArgs, null);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    Book book = new Book(
                            cursor.getString(cursor.getColumnIndex(ShelfHelpContract.BookEntry.COLUMN_BOOK_TITLE)),
                            null,
                            cursor.getString(cursor.getColumnIndex(ShelfHelpContract.BookEntry.COLUMN_BOOK_THUMBNAIL_URL)),
                            null, null, null, null);
                    books.add(book);
                }
            } finally {
                cursor.close();
            }
        }
        return books;
    }
}
