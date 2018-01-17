package com.freelance.netanel.androidsearchapp.feature.history;

import com.freelance.netanel.androidsearchapp.service.ioc_container.Injector;
import com.freelance.netanel.androidsearchapp.feature.history.repository.IHistoryRepository;

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
        Injector.getInstance().inject(this);
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
