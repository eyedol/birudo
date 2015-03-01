/*
 * Copyright 2015 Henry Addo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
