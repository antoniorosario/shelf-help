package com.antoniorosario.shelfhelpv2.ui.mainscreen;

import android.database.Cursor;
import android.support.v4.content.Loader;


public interface BookShelfView {

    void loadBooks(Loader<Cursor> loader, Cursor data);
}
