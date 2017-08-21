package com.antoniorosario.shelfhelpv2.ui.bookdetailsscreen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.antoniorosario.shelfhelpv2.ui.SingleFragmentActivity;
import com.antoniorosario.shelfhelpv2.models.Book;

import org.parceler.Parcels;

public class BookDetailActivity extends SingleFragmentActivity {
    private static final String EXTRA_BOOK = "EXTRA_BOOK";

    // Intent for selecting a book from a search
    public static Intent newIntentFromSearch(Context packageContext, Book book) {
        Intent intent = new Intent(packageContext, BookDetailActivity.class);
        intent.putExtra(EXTRA_BOOK, Parcels.wrap(book));
        return intent;
    }

    // Intent for selecting a book from an item in our database
    public static Intent newIntentFromCursor(Context packageContext, Uri bookUri) {
        Intent intent = new Intent(packageContext, BookDetailActivity.class);
        intent.setData(bookUri);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();

        //Book  data we return via content URI from our database
        Uri currentBookUri = intent.getData();
        // Book object we return via search
        Book currentBook = Parcels.unwrap(intent.getParcelableExtra(EXTRA_BOOK));

        return BookDetailFragment.newInstance(currentBook, currentBookUri);
    }
}