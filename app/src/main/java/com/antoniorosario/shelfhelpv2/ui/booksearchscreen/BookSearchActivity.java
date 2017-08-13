package com.antoniorosario.shelfhelpv2.ui.booksearchscreen;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.antoniorosario.shelfhelpv2.SingleFragmentActivity;


public class BookSearchActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, BookSearchActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return BookSearchFragment.newInstance();
    }
}
