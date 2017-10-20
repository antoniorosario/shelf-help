package com.antoniorosario.shelfhelpv2.ui.bookdetailsscreen;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antoniorosario.shelfhelpv2.R;
import com.antoniorosario.shelfhelpv2.database.ShelfHelpDataSource;
import com.antoniorosario.shelfhelpv2.models.Book;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.antoniorosario.shelfhelpv2.database.ShelfHelpContract.BookEntry;

public class BookDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, BookDetailView {
    private static final String ARG_BOOK = "ARG_BOOK";
    private static final String ARG_BOOK_URI = "ARG_BOOK_URI";
    private static final String DIALOG_ADD = "DIALOG_ADD";

    private static final int BOOK_LOADER_ID = 0;

    @BindView(R.id.book_title) TextView bookTitleTextView;
    @BindView(R.id.author) TextView authorNameTextView;
    @BindView(R.id.publisher) TextView publisherTextView;
    @BindView(R.id.subtitle) TextView subtitleTextView;
    @BindView(R.id.description) TextView descriptionTextView;
    @BindView(R.id.publishedDate) TextView publishedDateTextView;
    @BindView(R.id.cover) ImageView bookCoverImageView;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private BookDetailPresenter bookDetailPresenter;
    private ShelfHelpDataSource dataSource;
    private Book currentBook;
    private Uri currentBookUri;

    private int bookStatus;

    public static BookDetailFragment newInstance(Book book, Uri bookUri) {
        Bundle args = new Bundle();

        args.putParcelable(ARG_BOOK_URI, Parcels.wrap(bookUri));
        args.putParcelable(ARG_BOOK, Parcels.wrap(book));

        BookDetailFragment fragment = new BookDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dataSource = new ShelfHelpDataSource(getActivity());
        currentBook = Parcels.unwrap(getArguments().getParcelable(ARG_BOOK));
        currentBookUri = Parcels.unwrap(getArguments().getParcelable(ARG_BOOK_URI));
        bookDetailPresenter = new BookDetailPresenter();
        bookDetailPresenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        ButterKnife.bind(this, view);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);

        toolbar.setBackgroundResource(R.drawable.background_toolbar_translucent);
        toolbar.setBackgroundResource(R.drawable.background_toolbar_translucent);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Check to see whether or not we're coming from a search or a selection from our database via our viewpager
        if (currentBookUri != null) {
            //Set up the UI for a book we selected from our database
            getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);
            String uri = "@drawable/ic_action_share";
            fab.setImageResource(getResources().getIdentifier(uri, null, getActivity().getPackageName()));
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookDetailPresenter.shareBook();
                }
            });
        } else {
            //Set up the UI for a book we selected from a search
            bookDetailPresenter.loadBook(currentBook);

            String uri = "@drawable/ic_action_add_book";
            fab.setImageResource(getResources().getIdentifier(uri, null, getActivity().getPackageName()));
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Show dialog to ask the user where they want to save their book to.
                    bookDetailPresenter.addBook();
                }
            });
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_book_detail, menu);
        MenuItem updateToReadItem = menu.findItem(R.id.update_to_read);
        MenuItem updateToCurrentlyReadingItem = menu.findItem(R.id.update_to_currently_reading);
        MenuItem deleteBookItem = menu.findItem(R.id.delete_book);

        // If we're coming from a search, don't show options to update book status
        //or delete a book
        if (currentBook != null) {
            updateToReadItem.setVisible(false);
            updateToCurrentlyReadingItem.setVisible(false);
            deleteBookItem.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO eliminate redundant call to dataSource.updateBook()
        switch (item.getItemId()) {
            case R.id.update_to_currently_reading:
                bookStatus = BookEntry.BOOK_CURRENTLY_READING;
                dataSource.updateBook(bookStatus, currentBookUri);
                break;
            case R.id.update_to_read:
                bookStatus = BookEntry.BOOK_READ;
                dataSource.updateBook(bookStatus, currentBookUri);
                break;
            case R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                break;
            case R.id.delete_book:
                //TODO research if its possible to wait for user input before an activity calls finish();
                dataSource.deleteBook(currentBookUri);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        getActivity().finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_TITLE,
                BookEntry.COLUMN_BOOK_AUTHOR_NAME,
                BookEntry.COLUMN_BOOK_THUMBNAIL_URL,
                BookEntry.COLUMN_BOOK_DESCRIPTION,
                BookEntry.COLUMN_BOOK_SUBTITLE,
                BookEntry.COLUMN_PUBLISHED_DATE,
                BookEntry.COLUMN_PUBLISHER,
                BookEntry.COLUMN_BOOK_READ_STATUS
        };
        return new CursorLoader(getActivity(),
                currentBookUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            Book book = dataSource.getBookInfo(cursor);
            bookDetailPresenter.loadBook(book);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        bookDetailPresenter.resetBookData();
    }

    @Override
    public void clearBookData() {
        bookTitleTextView.setText("");
        authorNameTextView.append("");
        subtitleTextView.setText("");
        descriptionTextView.setText("");
        publishedDateTextView.setText("");
        publisherTextView.setText("");
        bookCoverImageView.setImageResource(android.R.color.transparent);
    }

    @Override
    public void showBook(Book book) {
        subtitleTextView.setText(book.getSubtitle());
        bookTitleTextView.setText(book.getTitle());
        descriptionTextView.setText(book.getDescription());
        authorNameTextView.append(book.getAuthorName());
        publisherTextView.append(book.getPublisher());
        publishedDateTextView.append(book.getPublishedDate());
        Picasso
                .with(getActivity())
                .load(book.getThumbnailUrl())
                .fit()
                .into(bookCoverImageView);
    }


    @Override
    public void showAddBookDialog() {
        FragmentManager manager = getFragmentManager();
        AddBookDialogFragment dialog = AddBookDialogFragment.newInstance(currentBook, bookStatus);
        dialog.show(manager, DIALOG_ADD);
    }

    @Override
    public void showShareBookChooser() {
        Book bookToShare = dataSource.getSingleBook(currentBookUri);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_book_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_book_text, bookToShare.getTitle(), bookToShare.getAuthorName()));

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
