package com.antoniorosario.shelfhelpv2;

import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.antoniorosario.shelfhelpv2.ui.booksearchscreen.BookSearchActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

public class BookSearchScreenTest {

    @Rule
    public ActivityTestRule<BookSearchActivity> bookSearchActivityTestRule =
            new ActivityTestRule<>(BookSearchActivity.class);

    @Test
    public void verifySearchViewWorksProperly(){
        String searchString = "Harry Potter";
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText(searchString));
        onView(withId(android.support.design.R.id.search_src_text)).check(matches(withText(searchString)));
    }

}
