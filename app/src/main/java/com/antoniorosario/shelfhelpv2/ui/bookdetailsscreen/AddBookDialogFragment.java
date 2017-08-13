package com.antoniorosario.shelfhelpv2.ui.bookdetailsscreen;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.antoniorosario.shelfhelpv2.R;
import com.antoniorosario.shelfhelpv2.database.ShelfHelpDataSource;
import com.antoniorosario.shelfhelpv2.models.Book;

import org.parceler.Parcels;

import static com.antoniorosario.shelfhelpv2.database.ShelfHelpContract.BookEntry;

/* Creates an Dialog to confirm users choice to add a book
 * and verify where what shelf they want to add it to.
 */
public class AddBookDialogFragment extends DialogFragment {
    private static final String ARG_BOOK = "ARG_BOOK";
    private static final String ARG_BOOK_STATUS = "ARG_BOOK_STATUS";

    private ShelfHelpDataSource dataSource;
    private Book currentBook;

    private int bookStatus = 0;

    public static AddBookDialogFragment newInstance(Book book, int bookStatus) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_BOOK, Parcels.wrap(book));
        args.putInt(ARG_BOOK_STATUS, bookStatus);

        AddBookDialogFragment fragment = new AddBookDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dataSource = new ShelfHelpDataSource(getActivity());

        currentBook = Parcels.unwrap(getArguments().getParcelable(ARG_BOOK));
        bookStatus = getArguments().getInt(ARG_BOOK_STATUS);

        return new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.select_shelf))
                .setSingleChoiceItems(R.array.array_book_status_options, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                bookStatus = BookEntry.BOOK_TO_READ;
                                break;
                            case 1:
                                bookStatus = BookEntry.BOOK_CURRENTLY_READING;
                                break;
                            case 2:
                                bookStatus = BookEntry.BOOK_READ;
                                break;
                        }
                    }
                })
                .setPositiveButton(getString(R.string.add_bok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dataSource.addBook(currentBook, bookStatus);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                }).create();
    }
}
