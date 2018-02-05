package com.freelance.netanel.androidsearchapp.feature.search.history;

import com.freelance.netanel.androidsearchapp.App;
import com.freelance.netanel.androidsearchapp.feature.search.history.repository.IHistoryRepository;

import javax.inject.Inject;

/**
 * <p>Represents an interface for handling user search history</p>
 * @see ISearchHistoryApi
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */
public class SearchHistoryApi implements ISearchHistoryApi {

    @Inject
    public IHistoryRepository historyRepository;

    private HistoryAdapter historyAdapter;
    public HistoryAdapter getAdapter() {
        return historyAdapter;
    }

    public SearchHistoryApi() {
        App.getInstance().getInjector().inject(this);
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
