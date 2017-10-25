package com.antoniorosario.shelfhelpv2;


import com.antoniorosario.shelfhelpv2.ui.booksearchscreen.BookSearchPresenter;
import com.antoniorosario.shelfhelpv2.ui.booksearchscreen.BookSearchView;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BookSearchPresenterTest {

    @Mock
    private BookSearchView mockBookSearchView;
    private BookSearchPresenter bookSearchPresenter;

    @Before
    public  void setup(){
        MockitoAnnotations.initMocks(this);
        bookSearchPresenter = new BookSearchPresenter();
    }


    //Can't Mock AsyncTask, switch to Retrofit then revisit
//    @Test
//    public void shouldShowSearchingViewWhenTaskExecutes(){
//    }

}
