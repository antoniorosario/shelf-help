package com.antoniorosario.shelfhelpv2.ui.settingsscreen;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.antoniorosario.shelfhelpv2.ui.SingleFragmentActivity;


public class SettingsActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, SettingsActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return SettingsFragment.newInstance();
    }
}
