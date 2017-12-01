package com.freelance.netanel.androidsearchapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Represents a repository for fetching shared data</p>
 * @see ISharedPrefRepository
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */
public class AppSharedPreferences implements ISharedPrefRepository{
    private static final String RESOURCE_APP_NAME = "RES_APP";

    private SharedPreferences sharedPreferences;

    public AppSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(RESOURCE_APP_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void addStringSet(Set<String> preference, String key) {
        sharedPreferences.edit().putStringSet(key, preference).apply();
    }

    @Override
    public void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    @Override
    public Set<String> getStringSet(String key) {
        Set<String> preference = null;
        try {
            preference = sharedPreferences.getStringSet(key, new HashSet<String>());
        } catch (ClassCastException ex) {
            remove(key);
        }
        return preference;
    }

    @Override
    public void clear() {
        sharedPreferences.edit().clear().apply();
    }
}