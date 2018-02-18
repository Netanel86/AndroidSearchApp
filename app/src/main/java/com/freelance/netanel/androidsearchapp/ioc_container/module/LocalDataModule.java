package com.freelance.netanel.androidsearchapp.ioc_container.module;


import com.freelance.netanel.androidsearchapp.feature.search.history.repository.HistoryRepository;
import com.freelance.netanel.androidsearchapp.feature.search.history.repository.IHistoryRepository;
import com.freelance.netanel.androidsearchapp.ioc_container.SearchViewScope;
import com.freelance.netanel.androidsearchapp.service.json_parser.IJsonParser;
import com.freelance.netanel.androidsearchapp.service.shared_pref.ISharedPrefRepository;

import dagger.Module;
import dagger.Provides;

/**
 * <p></p>
 *
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 * Created on 16/02/2018
 */

@Module
public class LocalDataModule {

    @Provides
    @SearchViewScope
    public IHistoryRepository provideHistoryRepository(ISharedPrefRepository sharedPrefRepository, IJsonParser jsonParser) {
        return new HistoryRepository(sharedPrefRepository, jsonParser);
    }
}
