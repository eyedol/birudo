package org.addhen.birudo.data.pref;

import android.content.SharedPreferences;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public abstract  class BasePreference<T> implements Preference<T> {

    private final SharedPreferences mSharedPreferences;

    private final String mKey;

    private final T mDefaultValue;

    public BasePreference(SharedPreferences sharedPreferences, String key, T defaultValue) {
        mSharedPreferences = sharedPreferences;
        mKey = key;
        mDefaultValue = defaultValue;
    }


    @Override
    public boolean isSet() {
        return getSharedPreferences().contains(mKey);
    }

    @Override
    public void delete() {
        getSharedPreferences().edit().remove(mKey).apply();
    }

    /**
     * Gets the key of the preference.
     *
     * @return The key
     */
    protected String getKey() {
        return mKey;
    }

    /**
     * Gets the default value of the preference
     *
     * @return The default value
     */
    protected T getDefaultValue() {
        return mDefaultValue;
    }

    /**
     * Gets the {@link android.content.SharedPreferences}
     *
     * @return The SharedPreferences
     */
    protected SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }
}
