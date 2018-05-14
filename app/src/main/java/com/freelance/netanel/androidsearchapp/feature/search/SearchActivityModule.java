package com.freelance.netanel.androidsearchapp.feature.search;

import com.freelance.netanel.androidsearchapp.feature.search.history.HistoryAdapterContract;
import com.freelance.netanel.androidsearchapp.feature.search.history.HistoryAdapterPresenter;
import com.freelance.netanel.androidsearchapp.feature.search.history.repository.HistoryRepository;
import com.freelance.netanel.androidsearchapp.feature.search.history.repository.IHistoryRepository;
import com.freelance.netanel.androidsearchapp.feature.search.results.ResultAdapterContract;
import com.freelance.netanel.androidsearchapp.feature.search.results.ResultAdapterPresenter;
import com.freelance.netanel.androidsearchapp.injection.PerActivity;
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
    @PerActivity
    public IHistoryRepository provideHistoryRepository(
            ISharedPrefRepository sharedPrefRepository, IJsonParser jsonParser) {
        return new HistoryRepository(sharedPrefRepository, jsonParser);
    }

    @Provides
    @PerActivity
    public ISearchActivityRouter provideSearchRouter(SearchActivity context, IJsonParser jsonParser) {
      return new SearchActivityRouter(context,jsonParser);
    }

    @Provides
    @PerActivity
    public ResultAdapterContract.IPresenter provideResultsPresenter() {
        return new ResultAdapterPresenter();
    }

    @Provides
    @PerActivity
    public HistoryAdapterContract.IPresenter provideHistoryPresenter(IHistoryRepository historyRepository) {
        return new HistoryAdapterPresenter(historyRepository);
    }

    @Provides
    @PerActivity
    public SearchActivityContract.IPresenter provideSearchPresenter
            (ISearchActivityRouter router,
             IProductRepository productRepository,
             ResultAdapterContract.IPresenter resultsPresenter,
             HistoryAdapterContract.IPresenter historyPresenter) {
        return new SearchActivityPresenter(router, productRepository, resultsPresenter, historyPresenter);
    }
}
