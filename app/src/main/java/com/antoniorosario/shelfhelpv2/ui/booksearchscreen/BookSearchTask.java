package com.antoniorosario.shelfhelpv2.ui.booksearchscreen;

import android.os.AsyncTask;

import com.antoniorosario.shelfhelpv2.models.Book;
import com.antoniorosario.shelfhelpv2.utils.QueryUtils;

import java.util.List;


public class BookSearchTask extends AsyncTask<String, Void, List<Book>> {

    BookSearchView bookSearchView;

    public BookSearchTask(BookSearchView bookSearchView) {
        this.bookSearchView = bookSearchView;
    }

    @Override
    protected List<Book> doInBackground(String... urls) {
        // Don't perform the request if there are no URLs, or the first URL is null
        if (urls.length < 1 || urls[0] == null) {
            return null;
        }

        List<Book> result = QueryUtils.fetchBookData(urls[0]);
        return result;
    }

    @Override
    protected void onPostExecute(List<Book> data) {
        bookSearchView.showSuccessfulSearchView(data);
    }
}