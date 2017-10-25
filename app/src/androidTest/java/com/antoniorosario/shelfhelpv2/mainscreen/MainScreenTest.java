package com.antoniorosario.shelfhelpv2.mainscreen;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.antoniorosario.shelfhelpv2.R;
import com.antoniorosario.shelfhelpv2.ui.mainscreen.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;


@RunWith(AndroidJUnit4.class)
public class MainScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickSearchFromMenuShowsSearchScreen(){
        onView(withId(R.id.action_search_book)).perform(click());

        onView(withId(R.id.search_title_text)).check(matches(isDisplayed()));
    }
}
