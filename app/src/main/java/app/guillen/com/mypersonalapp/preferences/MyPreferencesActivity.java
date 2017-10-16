package app.guillen.com.mypersonalapp.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;

import app.guillen.com.mypersonalapp.*;

/**
 * Created by guillen on 09/10/17.
 */

public class MyPreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            Log.d("settings", "preference changed: " + s);
            if("username".equals(s)){
                Log.d("settings", "new value for username: " + sharedPreferences.getString(s, null));
            }else if("applicationUpdates".equals(s)){
                Log.d("settings", "new value for applicationUpdates: " + sharedPreferences.getBoolean(s, false));
            }else if("downloadType".equals(s)){
                Log.d("settings", "new value for downloadType: " + sharedPreferences.getString(s, null));
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }
    }

}