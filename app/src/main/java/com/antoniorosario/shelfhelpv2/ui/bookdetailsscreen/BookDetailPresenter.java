package com.antoniorosario.shelfhelpv2.ui.bookdetailsscreen;

import com.antoniorosario.shelfhelpv2.models.Book;


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
