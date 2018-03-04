package com.freelance.netanel.androidsearchapp.feature.search;

import com.freelance.netanel.androidsearchapp.feature.search.history.HistoryAdapterContract;
import com.freelance.netanel.androidsearchapp.feature.search.history.HistoryAdapterPresenter;
import com.freelance.netanel.androidsearchapp.feature.search.history.repository.HistoryRepository;
import com.freelance.netanel.androidsearchapp.feature.search.history.repository.IHistoryRepository;
import com.freelance.netanel.androidsearchapp.feature.search.results.ResultAdapterContract;
import com.freelance.netanel.androidsearchapp.feature.search.results.ResultAdapterPresenter;
import com.freelance.netanel.androidsearchapp.service.json_parser.IJsonParser;
import com.freelance.netanel.androidsearchapp.service.shared_pref.ISharedPrefRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Netanel on 04/02/2018.
 */

@Module
public class SearchActivityModule {

    @Provides
    @SearchViewScope
    public IHistoryRepository provideHistoryRepository(
            ISharedPrefRepository sharedPrefRepository, IJsonParser jsonParser) {
        return new HistoryRepository(sharedPrefRepository, jsonParser);
    }

    @Provides
    @SearchViewScope
    public SearchRouter provideSearchRouter(SearchActivity context, IJsonParser jsonParser) {
      return new SearchRouter(context,jsonParser);
    }

    @Provides
    @SearchViewScope
    public ResultAdapterContract.IPresenter provideResultsPresenter() {
        return new ResultAdapterPresenter();
    }

    @Provides
    @SearchViewScope
    public HistoryAdapterContract.IPresenter provideHistoryPresenter(IHistoryRepository historyRepository) {
        return new HistoryAdapterPresenter(historyRepository);
    }

    @Provides
    @SearchViewScope
    public SearchContract.IPresenter provideSearchPresenter
            (SearchRouter router,
             IProductRepository productRepository,
             ResultAdapterContract.IPresenter resultsPresenter,
             HistoryAdapterContract.IPresenter historyPresenter) {
        return new SearchPresenter(router, productRepository, resultsPresenter, historyPresenter);
    }
}
