package com.antoniorosario.shelfhelpv2.ui.booksearchscreen;


import com.antoniorosario.shelfhelpv2.models.Book;

import java.util.List;

public interface BookSearchView {


    void showDeviceIsOfflineView();
    void showOfflineSearchView();
    void showSuccessfulSearchView(List<Book> data);
    void showActiveSearch();
    void showSearchingView();
}
