package com.antoniorosario.shelfhelpv2.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ShelfHelpContract {

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.antoniorosario.shelfhelpv2";
    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BOOKS = "books";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ShelfHelpContract() {
    }

    public static final class BookEntry implements BaseColumns {

        /**
         * The content URI to access the book data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        public static final String _ID = BaseColumns._ID;

        public static final String TABLE_NAME = "book";

        public static final String COLUMN_BOOK_TITLE = "title";

        public static final String COLUMN_BOOK_AUTHOR_NAME = "author_name";

        public static final String COLUMN_BOOK_SUBTITLE = "subtitle";

        public static final String COLUMN_BOOK_DESCRIPTION = "description";

        public static final String COLUMN_PUBLISHER = "publisher";

        public static final String COLUMN_PUBLISHED_DATE = "published_date";

        public static final String COLUMN_BOOK_THUMBNAIL_URL = "thumbnail_url";

        public static final String COLUMN_BOOK_READ_STATUS = "book_read_status";

        public static final int BOOK_TO_READ = 0;
        public static final int BOOK_CURRENTLY_READING = 1;
        public static final int BOOK_READ = 2;
    }

    //TODO Create AuthorEntry + PublisherEntry
}
