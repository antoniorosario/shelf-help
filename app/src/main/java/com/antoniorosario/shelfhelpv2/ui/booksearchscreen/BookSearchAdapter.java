package com.antoniorosario.shelfhelpv2.ui.booksearchscreen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antoniorosario.shelfhelpv2.R;
import com.antoniorosario.shelfhelpv2.models.Book;
import com.antoniorosario.shelfhelpv2.ui.bookdetailsscreen.BookDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class BookSearchAdapter extends RecyclerView.Adapter<BookSearchAdapter.BookHolder> {
    private Context context;
    private List<Book> books = Collections.emptyList();

    public BookSearchAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_item, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(BookHolder holder, int position) {
        Book book = getItem(position);
        holder.bindBook(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void clear() {
        int size = books.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                books.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }

    private Book getItem(int position) {
        return books.get(position);
    }

    //TODO look into potential memory leaks by keeping this class non-static
    public class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView bookTitle;
        private TextView bookAuthor;
        private ImageView bookCoverImage;

        private Book book;

        public BookHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            bookTitle = (TextView) itemView.findViewById(R.id.book_title);
            bookAuthor = (TextView) itemView.findViewById(R.id.book_author);
            bookCoverImage = (ImageView) itemView.findViewById(R.id.book_cover_image);
        }

        @Override
        public void onClick(View view) {
            Book currentBook = getItem(getAdapterPosition());
            Intent intent = BookDetailActivity.newIntentFromSearch(context, currentBook);
            context.startActivity(intent);
        }

        public void bindBook(Book book) {
            this.book = book;

            bookTitle.setText(this.book.getTitle());
            bookAuthor.setText(context.getString(R.string.by) + " " + this.book.getAuthorName());
            Picasso
                    .with(context)
                    .load(this.book.getThumbnailUrl())
                    .error(R.drawable.ic_place_holder)
                    .fit()
                    .into(bookCoverImage);
        }
    }
}
