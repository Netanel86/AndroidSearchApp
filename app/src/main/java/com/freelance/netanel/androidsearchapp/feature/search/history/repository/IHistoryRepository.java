package com.freelance.netanel.androidsearchapp.feature.search.history.repository;

import java.util.List;

/**
 * Created by Netanel on 29/10/2017.
 */

public interface IHistoryRepository {
    void addSearchQuery(String query);
    List<String> getSearchHistory();
    void clear();
}
