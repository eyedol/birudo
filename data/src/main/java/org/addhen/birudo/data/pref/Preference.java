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
