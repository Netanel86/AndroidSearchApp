package com.freelance.netanel.androidsearchapp.domain.history_repo;

import android.content.Context;

import com.freelance.netanel.androidsearchapp.domain.App;
import com.freelance.netanel.androidsearchapp.domain.json.IJsonParser;
import com.freelance.netanel.androidsearchapp.domain.json.JsonParser;
import com.freelance.netanel.androidsearchapp.domain.json.TypeOfT;
import com.freelance.netanel.androidsearchapp.domain.shared_pref.ISharedPrefRepository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Netanel on 29/10/2017.
 */

public class HistoryRepository implements IHistoryRepository {

    private static final String KEY_HISTORY = "HISTORY";
    private static final boolean CHANGED = true;
    private boolean isChanged = CHANGED;

    private ISharedPrefRepository sharedPreferences;
    private IJsonParser jsonParser;

    private ICollectionWrapper<String> historyItems;

    @Inject
    public HistoryRepository(ISharedPrefRepository sharedPreferences, IJsonParser jsonParser) {
        this.jsonParser = jsonParser;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void addSearchQuery(String query) {
        isChanged = CHANGED;
        historyItems.add(query);
        String json = jsonParser.toJson(historyItems.getWrappedList());
        sharedPreferences.AddString(json,KEY_HISTORY);
    }

    @Override
    public List<String> getSearchHistory() {
        if(isChanged) {
            try {
                isChanged = !CHANGED;

                historyItems = new TimeStampList<>();
                List<TimeStampList<String>.TimeStampItem> storedItems = fetchAndParseHistory();

                if(storedItems != null) {
                    historyItems.addAllWrappedItems(storedItems);
                }
            } catch (ClassCastException ex) {
                HistoryRepository.this.clear();
            }
        }

        return getStringList();
    }

    @Override
    public void clear() {
        sharedPreferences.remove(KEY_HISTORY);
        isChanged = CHANGED;
    }

    private List<TimeStampList<String>.TimeStampItem> fetchAndParseHistory() {
        String json = sharedPreferences.getString(KEY_HISTORY);
        Type typeToken = new TypeOfT<ArrayList<TimeStampList<String>.TimeStampItem>>() {
        }.getType();
        return jsonParser.fromJson(json, typeToken);
    }

    private List<String> getStringList() {
        return historyItems.getObjectTList();
    }
}
