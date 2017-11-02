package com.freelance.netanel.androidsearchapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Netanel on 29/10/2017.
 */

public class HistoryRepository implements IHistoryRepository {

    private static final String SEARCH_HISTORY = "SEARCH_HISTORY";
    private static final String HISTORY_KEY = "HISTORY";

    private SharedPreferences sharedPref;

    public HistoryRepository(Context context)
    {
        sharedPref = context.getSharedPreferences(SEARCH_HISTORY,Context.MODE_PRIVATE);

    }
    @Override
    public void addSearchQuery(String query) {

        Set<String> history = getSearchHistory();
        history.add(query);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(HISTORY_KEY,history);

        editor.commit();
    }

    @Override
    public Set<String> getSearchHistory() {
        Set<String> set = null;
        try {
             set = sharedPref.getStringSet(HISTORY_KEY, new HashSet<String>());
        }
        catch (ClassCastException ex)
        {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.commit();
        }
        return set;
    }
}
