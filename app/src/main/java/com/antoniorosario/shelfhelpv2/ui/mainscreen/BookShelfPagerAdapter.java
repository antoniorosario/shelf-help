package com.antoniorosario.shelfhelpv2.ui.mainscreen;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.antoniorosario.shelfhelpv2.R;
import com.antoniorosario.shelfhelpv2.database.ShelfHelpContract;

class BookShelfPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public BookShelfPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return BookShelfFragment.newInstance(String.valueOf(ShelfHelpContract.BookEntry.BOOK_TO_READ));
        } else if (position == 1) {
            return BookShelfFragment.newInstance(String.valueOf(ShelfHelpContract.BookEntry.BOOK_CURRENTLY_READING));
        } else {
            return BookShelfFragment.newInstance(String.valueOf(ShelfHelpContract.BookEntry.BOOK_READ));
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.books_to_read);
            case 1:
                return context.getString(R.string.currently_reading);
            case 2:
                return context.getString(R.string.books_read);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}