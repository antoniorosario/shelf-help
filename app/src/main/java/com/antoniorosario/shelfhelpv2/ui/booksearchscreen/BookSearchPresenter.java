package com.antoniorosario.shelfhelpv2.ui.booksearchscreen;

import android.support.annotation.NonNull;

import com.antoniorosario.shelfhelpv2.api.GoogleBooksApi;
import com.antoniorosario.shelfhelpv2.models.BaseJsonResponse;
import com.antoniorosario.shelfhelpv2.models.Book;
import com.antoniorosario.shelfhelpv2.models.ImageLinks;
import com.antoniorosario.shelfhelpv2.models.Item;
import com.antoniorosario.shelfhelpv2.models.VolumeInfo;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookSearchPresenter {
    private static final String LOG_TAG = "BookSearchPresenter";
    private static final String GOOGLE_BOOKS_BASE_URL = "https://www.googleapis.com";
    private static final String API_KEY = "AIzaSyA9wJxYq_xwO2G8GFInxR1UqubGa5x24Lw";

    private GoogleBooksApi client;
    private BookSearchView bookSearchView;

    public void setView(BookSearchView bookSearchView) {
        this.bookSearchView = bookSearchView;
    }

    public BookSearchPresenter() {
        if (client == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(GOOGLE_BOOKS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.client(httpClient.build())
                    .build();

            client = retrofit.create(GoogleBooksApi.class);
        }
    }

    public void executeQuery(String query) {
        Call<BaseJsonResponse> call = client.executeQuery(API_KEY, query);
        bookSearchView.showSearchingView();
        call.enqueue(new Callback<BaseJsonResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseJsonResponse> call, @NonNull Response<BaseJsonResponse> response) {
                if (response.isSuccessful()) {

                    List<Item> items = response.body().getItems();
                    List<Book> books = new ArrayList<>();

                    if (items != null) {
                        for (int i = 0; i < items.size(); i++) {
                            Item currentItem = items.get(i);
                            VolumeInfo itemInfo = currentItem.getVolumeInfo();
                            ImageLinks itemImages = itemInfo.getImageLinks();
                            List<String> authors = itemInfo.getAuthors();

                            String bookTitle = ((itemInfo.getTitle() == null) ? "Unknown title" : itemInfo.getTitle());
                            String subtitle = ((itemInfo.getSubtitle() == null) ? "" : itemInfo.getSubtitle());
                            String publisher = ((itemInfo.getPublisher() == null) ? "Unknown publisher" : itemInfo.getPublisher());
                            String description = ((itemInfo.getDescription() == null) ? "No Description" : itemInfo.getDescription());
                            String publishedDate = ((itemInfo.getPublishedDate() == null) ? "Unknown published date" : itemInfo.getPublishedDate());
                            String thumbnailUrl = ((itemImages == null) ? "" : itemImages.getSmallThumbnail());
                            String authorName = ((authors == null) ? "Unknown author" : authors.get(0));

                            Book book = new Book(bookTitle, authorName, thumbnailUrl, subtitle, publisher, description, publishedDate);
                            books.add(book);
                        }
                    }
                    if (bookSearchView != null) {
                        bookSearchView.showSuccessfulSearchView(books);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseJsonResponse> call, @NonNull Throwable t) {
                if (bookSearchView != null) {
                    bookSearchView.showFailedSearchView();
                }
            }
        });
    }
}

