package com.freelance.netanel.androidsearchapp.domain;


import com.freelance.netanel.androidsearchapp.domain.search_api.ProductSearchApi;
import com.freelance.netanel.androidsearchapp.ui.history.SearchHistoryApi;
import com.freelance.netanel.androidsearchapp.ui.search_view.ResultAdapter;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by Netanel on 10/12/2017.
 */
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityInjectionModule.class,
        ContextModule.class,
        NetworkUtilsModule.class,
        DataModule.class
})
@Singleton
public interface AppComponent extends AndroidInjector<App> {
    void inject(SearchHistoryApi searchHistoryApi);
    void inject(ProductSearchApi productSearchApi);
    void inject(ResultAdapter resultAdapter);
}