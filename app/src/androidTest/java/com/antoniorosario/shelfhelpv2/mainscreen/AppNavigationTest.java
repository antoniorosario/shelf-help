package com.antoniorosario.shelfhelpv2.mainscreen;


import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.antoniorosario.shelfhelpv2.R;
import com.antoniorosario.shelfhelpv2.ui.mainscreen.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class AppNavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void clickOnAndroidHomeIconOpensNavigation() {
        // Check that the left drawer is closed at startup
        onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))); // Left Drawer should be closed

        // Open Drawer
        String navigateUpDesc = activityTestRule.getActivity()
                .getString(R.string.drawer_open);
        onView(withContentDescription(navigateUpDesc)).perform(click());

        // Check if  drawer is open
        onView(withId(R.id.drawer_layout))
                .check(matches(isOpen(Gravity.LEFT))); // Left drawer is open
    }


    @Test
    public void clickOnStatisticsNavigationItemShowsStatisticsScreen() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left drawer should be closed
                .perform(open()); // Open Drawer

        onView(withId(R.id.nav_view))
                .perform(navigateTo(R.id.settings_activity));

        onView(withId(R.id.stats))
                .check(matches(withText(R.string.stats)));
    }

    @Test
    public void clickOnSearchNavigationItemShowsSearchScreen() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left drawer should be closed
                .perform(open()); // Open Drawer

        onView(withId(R.id.nav_view))
                .perform(navigateTo(R.id.search_activity));

        onView(withId(R.id.search_title_text))
                .check(matches(withText(R.string.search_active_string_title)));

    }
}
