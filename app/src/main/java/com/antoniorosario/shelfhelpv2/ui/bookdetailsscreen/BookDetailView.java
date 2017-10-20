package com.antoniorosario.shelfhelpv2.ui.bookdetailsscreen;

import com.antoniorosario.shelfhelpv2.models.Book;

/**
 * Created by Focus on 10/20/17.
 */

public interface BookDetailView {
    void showAddBookDialog();
    void showShareBookChooser();
    void showBook(Book book);
    void clearBookData();
}
