package com.example.jameskempf.calculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jameskempf.calculator.R;

import java.util.prefs.PreferenceChangeListener;

public class SettingsActivity1 extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //final Switch nw = (Switch)findViewById(R.id.nightSwitch);

        //final RelativeLayout layout = (RelativeLayout)findViewById(R.id.rLayout);

        //addPreferencesFromResource(R.xml.pref_general);

        //bindPreferenceSummaryToValue(findPreference(getString(R.string.night_mode_key)));

        //bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_location_key)));

        /*nw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    layout.setBackgroundColor(Color.DKGRAY);
                    nw.setTextColor(Color.WHITE);
                    Toast.makeText(getApplicationContext(), "Night Mode On", Toast.LENGTH_SHORT).show();
                }
                else {
                    layout.setBackgroundColor(Color.WHITE);
                    nw.setTextColor(Color.BLACK);
                    Toast.makeText(getApplicationContext(), "Night Mode Off", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), "")

        );
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference)preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex > 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(stringValue);
        }

        return true;
    }
}
