package com.freelance.netanel.androidsearchapp.repository;

import android.content.Context;

import com.freelance.netanel.androidsearchapp.ISharedPrefRepository;
import com.freelance.netanel.androidsearchapp.AppSharedPreferences;
import com.freelance.netanel.androidsearchapp.TimeStampedSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Netanel on 29/10/2017.
 */

public class HistoryRepository implements IHistoryRepository {

    private static final String KEY_HISTORY = "HISTORY";
    private static final boolean changed = true;
    private boolean changedFlag = changed;

    private ISharedPrefRepository sharedPreferences;

    private ArrayList<String> historyItems;

    public HistoryRepository(Context context) {
        sharedPreferences = new AppSharedPreferences(context);
    }

    @Override
    public void addSearchQuery(String query) {
        TimeStampedSet history = new TimeStampedSet(sharedPreferences.getStringSet(KEY_HISTORY));
        history.add(query);
        sharedPreferences.addStringSet(history.getInnerSet(), KEY_HISTORY);
        changedFlag = changed;
    }

    @Override
    public List<String> getSearchHistory() {
        if(changedFlag) {
            try {
                changedFlag = !changed;
                TimeStampedSet sortedSet =
                        new TimeStampedSet(sharedPreferences.getStringSet(KEY_HISTORY));

                historyItems = sortedSet.getNameOnlyList();

            } catch (ClassCastException ex) {
                HistoryRepository.this.clear();
            }
        }
        return historyItems;
    }

    @Override
    public void clear() {
        sharedPreferences.remove(KEY_HISTORY);
        changedFlag = changed;
    }
}
