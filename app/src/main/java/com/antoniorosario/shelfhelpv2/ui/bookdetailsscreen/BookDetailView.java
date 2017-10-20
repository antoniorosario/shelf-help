package com.antoniorosario.shelfhelpv2.ui.bookdetailsscreen;

import com.antoniorosario.shelfhelpv2.models.Book;

/**
 * Created by Focus on 10/20/17.
 */

public interface BookDetailView {
    void showAddBookDialog();
    void showShareBookChooser();
    void showLoadedBook(Book book);
    void clearBookData();
}
