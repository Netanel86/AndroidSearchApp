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
     * Adds a preference set to the repository under a key name.
     * @param preference A collection of strings.
     * @param key the key name under which to register the preference.
     */
    void addStringSet(Set<String> preference, String key);

    /***
     * Removes a preference from the repository.
     * @param key the key name under which the preference is registered.
     */
    void remove(String key);

    /***
     * Gets a preference set registered under the specified key name.
     * @param key the key name under which the preference is registered.
     * @return the preference string set registered to the requested key,
     * {@code null} otherwise.
     */
    Set<String> getStringSet(String key);

    /***
     * Clears the entire repository of all registered preferences.
     */
    void clear();
}
