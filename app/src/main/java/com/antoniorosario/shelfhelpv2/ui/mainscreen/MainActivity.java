package com.antoniorosario.shelfhelpv2.ui.mainscreen;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.antoniorosario.shelfhelpv2.R;
import com.antoniorosario.shelfhelpv2.ui.booksearchscreen.BookSearchActivity;
import com.antoniorosario.shelfhelpv2.ui.settingsscreen.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.antoniorosario.shelfhelpv2.database.ShelfHelpContract.BookEntry;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nvView) NavigationView nvDrawer;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tab_layout) TabLayout tabLayout;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BookShelfPagerAdapter bookShelfPagerAdapter = new BookShelfPagerAdapter(getApplicationContext(), getSupportFragmentManager());
        viewPager.setAdapter(bookShelfPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        // Sync tab selected in the navigation drawer
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                nvDrawer.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // intentionally left blank
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // intentionally left blank
            }
        });

        setSupportActionBar(toolbar);

        drawerToggle = setupDrawerToggle();

        drawer.addDrawerListener(drawerToggle);

        setupDrawerContent(nvDrawer);

        //Set default fragment in viewpager to checked in navigation drawer.
        nvDrawer.setCheckedItem(R.id.books_to_read_fragment);

        View headerView = nvDrawer.getHeaderView(0);
        LinearLayout header = (LinearLayout) headerView.findViewById(R.id.header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEasterEgg();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();
        switch (menuItemId) {
            case R.id.books_to_read_fragment:
                viewPager.setCurrentItem(BookEntry.BOOK_TO_READ);
                break;
            case R.id.books_currently_reading_fragment:
                viewPager.setCurrentItem(BookEntry.BOOK_CURRENTLY_READING);
                break;
            case R.id.books_read_fragment:
                viewPager.setCurrentItem(BookEntry.BOOK_READ);
                break;
            case R.id.search_activity:
                Intent discoverIntent = BookSearchActivity.newIntent(this);
                startActivity(discoverIntent);
                break;
            case R.id.settings_activity:
                Intent settingsIntent = SettingsActivity.newIntent(this);
                startActivity(settingsIntent);
                break;
            case R.id.action_send_feedback:
                sendFeedbackEmail();
                break;
            default:
                viewPager.setCurrentItem(BookEntry.BOOK_TO_READ);
        }

        if (menuItem.isCheckable()) {
            menuItem.setChecked(true);
        }
        drawer.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_search_book:
                Intent discoverIntent = BookSearchActivity.newIntent(this);
                startActivity(discoverIntent);
        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendFeedbackEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: ShelfHelpApp@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.send_feedback_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.send_feedback_text));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void showEasterEgg() {
        Toast.makeText(this, getString(R.string.easter_egg_text),
                Toast.LENGTH_SHORT).show();
    }
}