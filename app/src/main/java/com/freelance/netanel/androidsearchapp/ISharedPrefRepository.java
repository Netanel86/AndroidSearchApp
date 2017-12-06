package com.freelance.netanel.androidsearchapp;

import java.util.Set;

/**
 * <p>Supplies a repository interface for handling shared data and preferences</p>
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */
public interface ISharedPrefRepository {

    /***
     * Adds a string set to the shared repository under a key name.
     * @param stringSet A collection of strings to store in the repository.
     * @param key the key name under which to register the set.
     */
    void addStringSet(Set<String> stringSet, String key);

    /***
     * Gets a string set registered in the shared repository under a specified key name.
     * @param key the key name under which the preference is registered.
     * @return the preference string set registered under the requested key,
     * {@code null} otherwise.
     */
    Set<String> getStringSet(String key);

    /***
     * Adds a string to the shared repository under a key name.
     * @param input a string to store in the repository.
     * @param Key the key name under which to register the string.
     */
    void AddString(String input, String Key);

    /***
     * Gets a string registered in the shared repository under a specified key name.
     * @param key the key name under which the string is registered.
     * @return the string registered under the specified key,
     * empty string if no string is assigned to the key.
     */
    String getString(String key);

    /***
     * Removes a preference from the repository.
     * @param key the key name under which the preference is registered.
     */
    void remove(String key);

    /***
     * Clears the entire repository of all registered preferences.
     */
    void clear();
}
