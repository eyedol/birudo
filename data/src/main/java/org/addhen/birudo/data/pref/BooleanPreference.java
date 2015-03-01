package org.addhen.birudo.data.pref;

import android.content.SharedPreferences;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class BooleanPreference extends BasePreference<Boolean> {

    /**
     * Constructs a new {@link org.addhen.birudo.data.pref.BooleanPreference}
     *
     * @param sharedPreferences SharedPreferences to be used for storing the value.
     * @param key               The key for the preference
     */
    public BooleanPreference(SharedPreferences sharedPreferences, String key) {
        this(sharedPreferences, key, false);
    }

    /**
     * Constructs a new {@link org.addhen.birudo.data.pref.BooleanPreference}
     *
     * @param sharedPreferences SharedPreferences to be used for storing the value.
     * @param key               The key for the preference
     * @param defaultValue      The default value
     */
    public BooleanPreference(SharedPreferences sharedPreferences, String key,
            Boolean defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    /**
     * Gets the saved Boolean
     *
     * @return The saved Boolean
     */
    @Override
    public Boolean get() {
        return getSharedPreferences().getBoolean(getKey(), getDefaultValue());
    }

    /**
     * Sets the Boolean to be saved
     *
     * @param value The Boolean value to be saved
     */
    @Override
    public void set(Boolean value) {
        this.set((boolean) value);
    }

    public void set(boolean value) {
        getSharedPreferences().edit().putBoolean(getKey(), value).apply();
    }

}
