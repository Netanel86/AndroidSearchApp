package com.freelance.netanel.androidsearchapp.repository;

import android.content.Context;

import com.freelance.netanel.androidsearchapp.ISharedPrefRepository;
import com.freelance.netanel.androidsearchapp.AppSharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Netanel on 29/10/2017.
 */

public class HistoryRepository implements IHistoryRepository {

    private static final String KEY_HISTORY = "HISTORY";

    private ISharedPrefRepository sharedPreferences;

    public HistoryRepository(Context context) {
        sharedPreferences = new AppSharedPreferences(context);
    }

    @Override
    public void addSearchQuery(String query) {

        Set<String> newHistory = new HashSet<>();
        newHistory.add(query);

        List<String> history = getSearchHistory();
        if(!history.isEmpty()){
            newHistory.addAll(history);
        }

        sharedPreferences.addStringSet(newHistory,KEY_HISTORY);
    }

    @Override
    public List<String> getSearchHistory() {
        Set<String> set = null;
        try {
            set = sharedPreferences.getStringSet(KEY_HISTORY);
        }
        catch (ClassCastException ex)
        {
            HistoryRepository.this.clear();
        }
        return new ArrayList<>(set);
    }

    @Override
    public void clear() {
        sharedPreferences.remove(KEY_HISTORY);
    }
}
