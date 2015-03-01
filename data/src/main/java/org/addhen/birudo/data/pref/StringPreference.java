package org.addhen.birudo.data.pref;


import android.content.SharedPreferences;

/**
 * Preference for saving String values
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class StringPreference extends BasePreference<String> {

    /**
     * Constructs a new {@link org.addhen.birudo.data.pref.StringPreference}
     *
     * @param sharedPreferences SharedPreferences to be used for storing the value.
     * @param key               The key for the preference
     */
    public StringPreference(SharedPreferences sharedPreferences, String key) {
        this(sharedPreferences, key, null);
    }

    /**
     * Constructs a new {@link org.addhen.birudo.data.pref.StringPreference}
     *
     * @param sharedPreferences SharedPreferences to be used for storing the value.
     * @param key               The key for the preference
     * @param defaultValue      The default value
     */
    public StringPreference(SharedPreferences sharedPreferences, String key,
            String defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    /**
     * Get the saved String
     *
     * @return The saved string
     */
    @Override
    public String get() {
        return getSharedPreferences().getString(getKey(), getDefaultValue());
    }

    /**
     * Set the string to be saved
     *
     * @param value The String value to be saved
     */
    @Override
    public void set(String value) {
        getSharedPreferences().edit().putString(getKey(), value).apply();
    }
}
