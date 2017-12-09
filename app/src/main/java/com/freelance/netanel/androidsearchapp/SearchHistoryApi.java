package com.freelance.netanel.androidsearchapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.freelance.netanel.androidsearchapp.adapters.HistoryAdapter;
import com.freelance.netanel.androidsearchapp.repository.HistoryRepository;
import com.freelance.netanel.androidsearchapp.repository.IHistoryRepository;

/**
 * <p>Represents an interface for handling user search history</p>
 * @see ISearchHistoryApi
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */
public class SearchHistoryApi implements ISearchHistoryApi {
    private IHistoryRepository historyRepository;

    private HistoryAdapter historyAdapter;
    public HistoryAdapter getAdapter() {
        return historyAdapter;
    }

    public SearchHistoryApi(Context context) {
        historyRepository = new HistoryRepository(context);
        historyAdapter = new HistoryAdapter();
    }

    public void loadHistory() {
        historyAdapter.setItems(historyRepository.getSearchHistory());
    }

    public void addSearchQuery(String query) {
        historyRepository.addSearchQuery(query);
        loadHistory();
    }

    public void setFilter(String contained) {
        historyAdapter.setItemsFilteredByName(historyRepository.getSearchHistory(),contained);
    }

    public void clear() {
        historyRepository.clear();
        historyAdapter.setItems(null);
    }
}
