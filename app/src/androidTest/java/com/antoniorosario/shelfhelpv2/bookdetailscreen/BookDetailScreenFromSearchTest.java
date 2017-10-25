package com.antoniorosario.shelfhelpv2.bookdetailscreen;

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.antoniorosario.shelfhelpv2.R;
import com.antoniorosario.shelfhelpv2.models.Book;
import com.antoniorosario.shelfhelpv2.ui.bookdetailsscreen.BookDetailActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)

public class BookDetailScreenFromSearchTest {

    private static String title = "Harry Potter";
    private static String author = "JK Rowling";
    private static String imageUrl = "image url";
    private static String subtitle = "subtitle";
    private static String publisher = "publisher";
    private static String description = "description";
    private static String publishedDate = "published date";


    /**
     * Book stub for testing
     */
    private static Book BOOK = new Book(title, author, imageUrl, subtitle, publisher, description, publishedDate);


    @Rule
    public ActivityTestRule<BookDetailActivity> bookDetailActivityTestRule =
            new ActivityTestRule<>(BookDetailActivity.class, true, false);

    @Before
    public void intentWithStubbedBookFromSearch() {
        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent intent = new Intent();
        intent.putExtra("EXTRA_BOOK", Parcels.wrap(BOOK));
        bookDetailActivityTestRule.launchActivity(intent);
    }

    @Test
    public void bookDetailsDisplayed() {
        onView(ViewMatchers.withId(R.id.book_title)).check(matches(withText(title)));
        onView(withId(R.id.author)).check(matches(withText(author)));
        onView(withId(R.id.subtitle)).check(matches(withText(subtitle)));
        onView(withId(R.id.publisher)).check(matches(withText(publisher)));
        onView(withId(R.id.description)).check(matches(withText(description)));
        onView(withId(R.id.publishedDate)).check(matches(withText(publishedDate)));
    }

    @Test
    public void clickAddBookButtonShowsAddBookDialog() {
        onView(withId(R.id.fab)).perform(click());
        onView(withText(R.string.books_to_read)).check(matches(isDisplayed()));
    }

    @Test
    public void addBookToToReadShelf() {
        onView(withId(R.id.fab)).perform(click());
        onView(withText(R.string.books_to_read)).perform(click());
        onView(withText(R.string.add_book)).perform(click());
        onView(withText("Book saved")).
                inRoot(withDecorView(not(is(bookDetailActivityTestRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void addBookToReadingShelf() {
        onView(withId(R.id.fab)).perform(click());
        onView(withText(R.string.currently_reading)).perform(click());
        onView(withText(R.string.add_book)).perform(click());
        onView(withText("Book saved")).
                inRoot(withDecorView(not(is(bookDetailActivityTestRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void addBookToReadShelf() {
        onView(withId(R.id.fab)).perform(click());
        onView(withText(R.string.books_read)).perform(click());
        onView(withText(R.string.add_book)).perform(click());
        onView(withText("Book saved")).
                inRoot(withDecorView(not(is(bookDetailActivityTestRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }
}
