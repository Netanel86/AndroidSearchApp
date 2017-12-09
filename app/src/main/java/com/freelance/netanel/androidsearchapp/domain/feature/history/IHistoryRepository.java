package com.freelance.netanel.androidsearchapp.domain.feature.history;

import java.util.List;

/**
 * Created by Netanel on 29/10/2017.
 */

public interface IHistoryRepository {
    void addSearchQuery(String query);
    List<String> getSearchHistory();
    void clear();
}
