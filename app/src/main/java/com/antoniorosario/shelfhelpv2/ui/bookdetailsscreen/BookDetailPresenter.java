package com.antoniorosario.shelfhelpv2.ui.bookdetailsscreen;

import com.antoniorosario.shelfhelpv2.models.Book;

/**
 * Created by Focus on 10/20/17.
 */

public class BookDetailPresenter {

    private BookDetailView bookDetailView;

    public void setView(BookDetailView bookDetailView) {
        this.bookDetailView = bookDetailView;
    }

    public void addBook() {
        bookDetailView.showAddBookDialog();
    }

    public void shareBook() {
        bookDetailView.showShareBookChooser();
    }

    public void loadBook(Book currentBook) {
        bookDetailView.showLoadedBook(currentBook);
    }

    public void resetBookData() {
        bookDetailView.clearBookData();
    }
}
