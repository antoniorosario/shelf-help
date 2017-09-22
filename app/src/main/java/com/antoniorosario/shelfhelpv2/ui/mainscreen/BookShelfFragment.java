package com.antoniorosario.shelfhelpv2.ui.mainscreen;

import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.antoniorosario.shelfhelpv2.R;
import com.antoniorosario.shelfhelpv2.database.ShelfHelpContract.BookEntry;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookShelfFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, BookShelfView {

    private static final String BOOK_STATUS = "BOOK_STATUS";
    private static final int BOOK_LOADER_ID = 0;
    private static final int PORTRAIT_SPAN_COUNT = 4;
    private static final int LANDSCAPE_SPAN_COUNT = 7;

    @BindView(R.id.shelf_book_grid) RecyclerView bookShelf;
    @BindView(R.id.shelf_empty_view) RelativeLayout emptyView;

    private BookShelfCursorAdapter bookShelfCursorAdapter;

    //Whether the user is currently reading, has read, or wants to read a particular book.
    private String bookStatus;

    public BookShelfFragment() {
        // Required empty public constructor
    }

    public static BookShelfFragment newInstance(String bookStatus) {
        Bundle args = new Bundle();
        args.putString(BOOK_STATUS, bookStatus);
        BookShelfFragment fragment = new BookShelfFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookStatus = getArguments().getString(BOOK_STATUS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_book_shelf, container, false);
        ButterKnife.bind(this, view);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            bookShelf.setLayoutManager(new GridLayoutManager(getActivity(), PORTRAIT_SPAN_COUNT));
        } else {
            bookShelf.setLayoutManager(new GridLayoutManager(getActivity(), LANDSCAPE_SPAN_COUNT));
        }

        bookShelf.setHasFixedSize(true);

        bookShelfCursorAdapter = new BookShelfCursorAdapter(getActivity(), null);

        bookShelf.setAdapter(bookShelfCursorAdapter);
        getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_TITLE,
                BookEntry.COLUMN_BOOK_THUMBNAIL_URL,
                BookEntry.COLUMN_BOOK_READ_STATUS
        };

        String selection = BookEntry.COLUMN_BOOK_READ_STATUS + " = ?";
        String[] selectionArgs = {bookStatus};

        return new CursorLoader(getActivity(),
                BookEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        loadBooks(loader, data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        bookShelfCursorAdapter.swapCursor(null);
    }

    @Override
    public void loadBooks(Loader<Cursor> loader, Cursor data) {
        if (data == null || !(data.getCount() > 0)) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
        bookShelfCursorAdapter.swapCursor(data);
    }
}