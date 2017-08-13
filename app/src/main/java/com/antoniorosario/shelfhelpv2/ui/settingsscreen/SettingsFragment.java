package com.antoniorosario.shelfhelpv2.ui.settingsscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.Toast;

import com.antoniorosario.shelfhelpv2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//TODO
public class SettingsFragment extends Fragment {
    @BindView(R.id.stats_table_row) TableRow statsRow;


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container, false);
        ButterKnife.bind(this, view);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        getActivity();
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return view;
    }

    @OnClick(R.id.stats_table_row)
    public void showStats() {
        Toast.makeText(getActivity(), getString(R.string.show_stats), Toast.LENGTH_LONG).show();
    }
}
