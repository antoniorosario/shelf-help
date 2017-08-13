package com.antoniorosario.shelfhelpv2.ui.mainscreen;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.antoniorosario.shelfhelpv2.R;
import com.antoniorosario.shelfhelpv2.ui.bookdetailsscreen.BookDetailActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.antoniorosario.shelfhelpv2.database.ShelfHelpContract.BookEntry;

public class BookShelfCursorAdapter extends CursorRecyclerViewAdapter<BookShelfCursorAdapter.ViewHolder> {

    private Context context;
    private Cursor cursor;

    public BookShelfCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shelf_grid_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        viewHolder.bind(cursor);
    }

    //TODO look into potential memory leaks by keeping this class non-static
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.grid_book_cover_image) ImageView bookCoverImageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            long itemId = BookShelfCursorAdapter.this.getItemId(getAdapterPosition());
            Uri currentBookUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, itemId);
            Intent intent = BookDetailActivity.newIntentFromCursor(context, currentBookUri);
            context.startActivity(intent);
        }

        public void bind(Cursor cursor) {
            int thumbnailUrlIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_THUMBNAIL_URL);

            String currentThumbNailUrl = cursor.getString(thumbnailUrlIndex);
            Picasso
                    .with(context)
                    .load(currentThumbNailUrl)
                    .fit()
                    .into(bookCoverImageView);
        }
    }
}
