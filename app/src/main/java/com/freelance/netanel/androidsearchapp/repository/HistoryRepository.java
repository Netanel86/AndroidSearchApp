package com.freelance.netanel.androidsearchapp.repository;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Netanel on 29/10/2017.
 */

public class HistoryRepository implements IHistoryRepository {

    private static final String RES_APP = "RES_APP";
    private static final String KEY_HISTORY = "HISTORY";

    private SharedPreferences mSharedPreferences;

    public HistoryRepository(Context context) {
        mSharedPreferences = context.getSharedPreferences(RES_APP,Context.MODE_PRIVATE);
    }

    @Override
    public void addSearchQuery(String query) {

        Set<String> newHistory = new HashSet<>();
        newHistory.add(query);

        Set<String> history = getSearchHistory();
        if(!history.isEmpty()){
            newHistory.addAll(history);
        }

        mSharedPreferences.edit().putStringSet(KEY_HISTORY,newHistory).apply();
    }

    @Override
    public Set<String> getSearchHistory() {
        Set<String> set = null;
        try {
             set = mSharedPreferences.getStringSet(KEY_HISTORY, new HashSet<String>());
        }
        catch (ClassCastException ex)
        {
            mSharedPreferences.edit().clear().apply();
        }
        return set;
    }

    @Override
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
}
