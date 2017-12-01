package com.freelance.netanel.androidsearchapp.repository;

import java.util.List;
import java.util.Set;

/**
 * Created by Netanel on 29/10/2017.
 */

public interface IHistoryRepository {
    void addSearchQuery(String query);
    List<String> getSearchHistory();
    void clear();
}
