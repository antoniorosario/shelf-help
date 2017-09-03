package com.antoniorosario.shelfhelpv2.ui.booksearchscreen;

public class BookSearchPresenter {

    private BookSearchView bookSearchView;

    public void setView(BookSearchView bookSearchView) {
        this.bookSearchView = bookSearchView;
    }

    public void executeTask(String query) {
        new BookSearchTask(bookSearchView).execute(query);
        bookSearchView.showSearchingView();
    }
}


