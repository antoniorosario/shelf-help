package com.antoniorosario.shelfhelpv2.ui.bookdetailsscreen;

import com.antoniorosario.shelfhelpv2.models.Book;


public interface BookDetailView {
    void showAddBookDialog();
    void showShareBookChooser();
    void showLoadedBook(Book book);
    void clearBookData();
}
