package com.freelance.netanel.androidsearchapp;

import android.support.v7.widget.RecyclerView;

import com.freelance.netanel.androidsearchapp.adapters.HistoryAdapter;

/**
 * <p>Supplies an interface for implementing a search history api</p>
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */
public interface ISearchHistoryApi {
    /**
     * Get's the adapter handled by this api
     * @return a {@code RecyclerView.Adapter} handling the search history.
     */
    HistoryAdapter getAdapter();

    /**
     * Load's the search history to the adapter
     */
    void loadHistory();

    /**
     * Add's a new search query to the collection
     * @param query the query to add
     */
    void addSearchQuery(String query);

    /**
     * Set's a filter on the collection.
     * @param contained the string to filter by.
     */
    void setFilter(String contained);

    /**
     * Clear's both the adapter and the stored data
     */
    void clear();
}
