package com.antoniorosario.shelfhelpv2.ui.booksearchscreen;

public class BookSearchPresenter {

    private static final String GOOGLE_BOOKS_BASE_URL = "https://www.googleapis.com";
    private static final String API_KEY = "AIzaSyA9wJxYq_xwO2G8GFInxR1UqubGa5x24Lw";

    private BookSearchView bookSearchView;

    public void setView(BookSearchView bookSearchView) {
        this.bookSearchView = bookSearchView;
    }

    //TODO Replace AsyncTask with Retrofit
    public void executeTask(String query) {
        new BookSearchTask(bookSearchView).execute(query);
        bookSearchView.showSearchingView();
    }

}

