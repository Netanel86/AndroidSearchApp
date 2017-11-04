package com.freelance.netanel.androidsearchapp.repository;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Netanel on 29/10/2017.
 */

public class HistoryRepository implements IHistoryRepository {

    private static final String FILE_NAME = "FILE_NAME";
    private static final String HISTORY_LIST = "HISTORY";

    private SharedPreferences sharedPref;

    public HistoryRepository(Context context)
    {
        sharedPref = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);

    }

    @Override
    public void addSearchQuery(String query) {

        Set<String> newHistory = new HashSet<>();
        newHistory.add(query);

        Set<String> history = getSearchHistory();
        if(!history.isEmpty()){
            newHistory.addAll(history);
        }

        sharedPref.edit().putStringSet(HISTORY_LIST,newHistory).apply();
    }

    @Override
    public Set<String> getSearchHistory() {
        Set<String> set = null;
        try {
             set = sharedPref.getStringSet(HISTORY_LIST, new HashSet<String>());
        }
        catch (ClassCastException ex)
        {
            sharedPref.edit().clear().apply();
        }
        return set;
    }

    @Override
    public void clear() {
        sharedPref.edit().clear().apply();
    }
}
