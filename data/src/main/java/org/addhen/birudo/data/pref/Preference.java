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

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface Preference<T> {

    /**
     * Gets the value of the preference
     */
    T get();

    /**
     * Checks if the preference is set.
     *
     * @return The status of the preferences. Whether it has been set or not.
     */
    boolean isSet();

    /**
     * Set the value for the preference
     */
    void set(T value);

    /**
     * Deletes the set preference.
     */
    void delete();


}
