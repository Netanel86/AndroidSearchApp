package com.freelance.netanel.androidsearchapp.ioc_container.module;

import android.content.Context;

import com.freelance.netanel.androidsearchapp.feature.search.IProductRepository;
import com.freelance.netanel.androidsearchapp.feature.search.history.HistoryAdapterContract;
import com.freelance.netanel.androidsearchapp.feature.search.history.HistoryAdapterPresenter;
import com.freelance.netanel.androidsearchapp.feature.search.history.repository.IHistoryRepository;
import com.freelance.netanel.androidsearchapp.feature.search.results.ResultAdapterContract;
import com.freelance.netanel.androidsearchapp.feature.search.results.ResultAdapterPresenter;
import com.freelance.netanel.androidsearchapp.feature.search.SearchContract;
import com.freelance.netanel.androidsearchapp.feature.search.SearchPresenter;
import com.freelance.netanel.androidsearchapp.ioc_container.SearchScreenScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Netanel on 04/02/2018.
 */

@Module
public class PresenterModule {

    @Provides
    @SearchScreenScope
    public ResultAdapterContract.IPresenter provideResultsPresenter() {
        return new ResultAdapterPresenter();
    }

    @Provides
    @SearchScreenScope
    public HistoryAdapterContract.IPresenter provideHistoryPresenter(IHistoryRepository historyRepository) {
        return new HistoryAdapterPresenter(historyRepository);
    }

    @Provides
    @SearchScreenScope
    public SearchContract.IPresenter provideSearchPresenter
            (Context context,
             IProductRepository productRepository,
             ResultAdapterContract.IPresenter resultsPresenter,
             HistoryAdapterContract.IPresenter historyPresenter) {
        return new SearchPresenter(context, productRepository, resultsPresenter, historyPresenter);
    }
}
