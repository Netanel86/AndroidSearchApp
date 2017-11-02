package com.freelance.netanel.androidsearchapp;

import java.util.Set;

/**
 * Created by Netanel on 29/10/2017.
 */

public interface IHistoryRepository {
    void addSearchQuery(String query);
    Set<String> getSearchHistory();
}
