package com.antoniorosario.shelfhelpv2;

import com.antoniorosario.shelfhelpv2.models.Book;
import com.antoniorosario.shelfhelpv2.ui.bookdetailsscreen.BookDetailPresenter;
import com.antoniorosario.shelfhelpv2.ui.bookdetailsscreen.BookDetailView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;


public class BookDetailPresenterTest {
    @Mock
    private BookDetailView mockBookDetailView;
    private BookDetailPresenter bookDetailPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        bookDetailPresenter = new BookDetailPresenter();
        bookDetailPresenter.setView(mockBookDetailView);
    }

    @Test
    public void shouldShowAddBookDialogWhenUserClicksAddFab() {
        bookDetailPresenter.addBook();
        verify(mockBookDetailView).showAddBookDialog();
    }

    @Test
    public void shouldShowShareChooserWhenUserClicksShareFab() {
        bookDetailPresenter.shareBook();
        verify(mockBookDetailView).showShareBookChooser();
    }

    @Test
    public void shouldShowBookDataWhenUserClicksOnABookToLoad() {

        Book mockBook = any();
        bookDetailPresenter.loadBook(mockBook);
        verify(mockBookDetailView).showLoadedBook(mockBook);
    }

    //TODO Migrate from CursorLoader to Paging Library,
//    @Test
//    public void shouldResetBookDataWhenLoaderIsReset(){
//
//    }
}
