package app.guillen.com.mypersonalapp.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;

import app.guillen.com.mypersonalapp.*;
import app.guillen.com.mypersonalapp.models.User;
import app.guillen.com.mypersonalapp.repositories.UserRepository;

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
            SharedPreferences.Editor modifier = sharedPreferences.edit();
            Log.d("settings", "preference changed: " + s);
            if("fullname".equals(s)){
                Log.d("settings", "Nuevo Fullname: " + sharedPreferences.getString(s, null));
            }else if("theme".equals(s)){
                Log.d("settings", "Nuevo tema seleccionado: " + sharedPreferences.getBoolean(s, false));
            }else if("font".equals(s)){
                Log.d("settings", "Nueva fuente asignada: " + sharedPreferences.getString(s, null));
                String update = sharedPreferences.getString(s, null);
                boolean success = modifier.putString("preferenciaFuente", update).commit();
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