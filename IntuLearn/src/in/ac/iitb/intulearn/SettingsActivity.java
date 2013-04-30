package in.ac.iitb.intulearn;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

// REF: http://goo.gl/lI0g8
public class SettingsActivity extends Activity {
    public static final String PREF_UI_SPEED_KEY = "pref_ui_speed_key";
    public static final String PREF_UI_SPEED_VALUE_FAST = "fast";
    public static final String PREF_UI_SPEED_VALUE_MEDIUM = "medium";
    public static final String PREF_UI_SPEED_VALUE_SLOW = "slow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
            for (String key : sharedPreferences.getAll().keySet())
                initSummary(findPreference(key));
        }

        @Override
        public void onResume() {
            super.onResume();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

            // Register summary listener
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

            // Unregister summary listener
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        }

        private void initSummary(Preference pref) {
            if (pref instanceof PreferenceCategory) {
                PreferenceCategory category = (PreferenceCategory) pref;
                for (int i = 0; i < category.getPreferenceCount(); i++) {
                    initSummary(category.getPreference(i));
                }
            } else {
                updateSummary(pref);
            }
        }

        private void updateSummary(Preference pref) {
            String key = pref.getKey();
            CharSequence summary;
            if (PREF_UI_SPEED_KEY.equals(key)) {
                ListPreference p = (ListPreference) pref;
                int index = p.findIndexOfValue(p.getValue());
                summary = "Current Speed: " + (index >= 0 ? p.getEntries()[index] : "[None]");
            } else {
                // For all other preferences
                summary = pref.getSharedPreferences().getString(key, null);
            }
            pref.setSummary(summary);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPref, String key) {
            updateSummary(findPreference(key));
        }

    }
}
