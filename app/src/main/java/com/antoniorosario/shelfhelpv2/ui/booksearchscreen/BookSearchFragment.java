package com.antoniorosario.shelfhelpv2.ui.booksearchscreen;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.antoniorosario.shelfhelpv2.R;
import com.antoniorosario.shelfhelpv2.models.Book;
import com.antoniorosario.shelfhelpv2.utils.ConnectivityUtils;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BookSearchFragment extends Fragment implements SearchView.OnQueryTextListener, BookSearchView {
    private static final String BASE_BOOKS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?key=AIzaSyA9wJxYq_xwO2G8GFInxR1UqubGa5x24Lw";
    private static final String QUERY_STRING = "QUERY_STRING";
    private static final String LIST_OF_BOOKS = "LIST_OF_BOOKS";

    @BindView(R.id.loading_indicator) ProgressBar loadingIndicator;
    @BindView(R.id.search_title_text) TextView searchTitleTextView;
    @BindView(R.id.search_subtitle_text) TextView searchSubtitleTextView;
    @BindView(R.id.search_icon) ImageView searchIcon;
    @BindView(R.id.retry_query_button) ImageButton retryQueryButton;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.book_search_list) RecyclerView searchRecyclerView;

    private SearchView searchView;
    private BookSearchAdapter bookSearchAdapter;
    private BookSearchPresenter bookSearchPresenter;
    private String query;
    private List<Book> bookList = Collections.emptyList();

    public static BookSearchFragment newInstance() {
        return new BookSearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        bookSearchAdapter = new BookSearchAdapter(getActivity());
        bookSearchPresenter = new BookSearchPresenter();
        bookSearchPresenter.setView(this);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        if (bookList == null || bookList.isEmpty()) {
            showActiveSearch();
        }

        if (savedInstanceState != null) {
            query = savedInstanceState.getString(QUERY_STRING);
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        searchRecyclerView.setAdapter(bookSearchAdapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // initialize searchview
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search_book);
        // work around for saving query state
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if (!TextUtils.isEmpty(query)) {
            searchItem.expandActionView();
            searchView.setQuery(query, true);
            searchView.clearFocus();
        }
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(QUERY_STRING, query);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bookSearchPresenter.setView(null);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Check whether or not there is an active network connection
        if (ConnectivityUtils.isConnected(getActivity())) {
            // Search submitted with an active connection
            // Fetch the data remotely
            bookSearchPresenter.executeQuery(query);
        } else {
            // Search submitted without an active connection
            showOfflineSearchView();
        }
        // Reset SearchView query
        this.query = "";
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        this.query = newText;
        // Let the user know they don't have an active network connection while typing, else resume search
        if (!(ConnectivityUtils.isConnected(getActivity()))) {
            showDeviceIsOfflineView();
        } else {
            bookSearchAdapter.clear();
            showActiveSearch();
        }
        return false;
    }

    //Allow user to retry query, only visible if there is no internet connection
    @OnClick(R.id.retry_query_button)
    public void retryQuery() {
        onQueryTextSubmit(query);
    }

    @Override
    public void showActiveSearch() {
        searchIcon.setVisibility(View.VISIBLE);
        searchTitleTextView.setText(getString(R.string.search_active_string_title));
        searchSubtitleTextView.setText(getString(R.string.search_active_state_string_subtitle));
        searchIcon.setVisibility(View.VISIBLE);
        retryQueryButton.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showSuccessfulSearchView(List<Book> bookList) {
        this.bookList = bookList;
        searchIcon.setVisibility(View.GONE);
        searchSubtitleTextView.setText("");
        searchTitleTextView.setText("");
        loadingIndicator.setVisibility(View.GONE);
        retryQueryButton.setVisibility(View.GONE);

        if (bookList != null && !bookList.isEmpty()) {
            // If we have a list of books add it to the adapter
            bookSearchAdapter.setBooks(bookList);
            bookSearchAdapter.notifyDataSetChanged();
        } else {
            // If a users search did not return searchResults let them know
            searchTitleTextView.setText(R.string.search_no_results_title_text);
            searchSubtitleTextView.setText("");
        }
    }

    @Override
    public void showOfflineSearchView() {
        bookSearchAdapter.clear();
        loadingIndicator.setVisibility(View.GONE);
        searchIcon.setVisibility(View.GONE);
        searchTitleTextView.setText(R.string.search_offline_title_text);
        searchSubtitleTextView.setText(R.string.search_offline_subtitle_text);
        retryQueryButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDeviceIsOfflineView() {
        bookSearchAdapter.clear();
        loadingIndicator.setVisibility(View.GONE);
        searchIcon.setVisibility(View.GONE);
        searchTitleTextView.setText(R.string.search_device_is_offline_title);
        searchSubtitleTextView.setText(R.string.search_device_is_offline_subtitle);
        retryQueryButton.setVisibility(View.GONE);
    }

    @Override
    public void showSearchingView() {
        loadingIndicator.setVisibility(View.VISIBLE);
        searchIcon.setVisibility(View.GONE);
        searchSubtitleTextView.setText("");
        searchTitleTextView.setText("");
    }

    @Override
    public void showFailedSearchView() {
        Toast.makeText(getActivity(), R.string.error_connecting_to_server, Toast.LENGTH_LONG).show();
    }
}
