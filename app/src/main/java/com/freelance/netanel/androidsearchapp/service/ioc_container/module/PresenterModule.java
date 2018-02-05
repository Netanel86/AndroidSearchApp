package com.freelance.netanel.androidsearchapp.service.ioc_container.module;

import android.content.Context;

import com.freelance.netanel.androidsearchapp.feature.search.IProductRepository;
import com.freelance.netanel.androidsearchapp.feature.search.ResultAdapterContract;
import com.freelance.netanel.androidsearchapp.feature.search.ResultAdapterPresenter;
import com.freelance.netanel.androidsearchapp.feature.search.SearchContract;
import com.freelance.netanel.androidsearchapp.feature.search.SearchPresenter;
import com.freelance.netanel.androidsearchapp.service.ioc_container.SearchScreenScope;

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
    public SearchContract.IPresenter provideSearchPresenter
            (Context context,
             IProductRepository productRepository,
             ResultAdapterContract.IPresenter resultsPresenter) {
        return new SearchPresenter(context, productRepository, resultsPresenter);
    }
}
