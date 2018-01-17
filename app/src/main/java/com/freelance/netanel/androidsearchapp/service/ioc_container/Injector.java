package com.freelance.netanel.androidsearchapp.domain;

import android.content.Context;

import com.freelance.netanel.androidsearchapp.domain.search_api.ProductSearchApi;
import com.freelance.netanel.androidsearchapp.ui.history.SearchHistoryApi;
import com.freelance.netanel.androidsearchapp.ui.search_view.ResultAdapter;

/**
 * Created by Netanel on 31/12/2017.
 */

public class Injector {
    private static Injector instance;
    public static Injector getInstance() {
        return instance;
    }

    private AppComponent appComponent;

    public Injector(Context context) {
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(context))
                .build();

    }

    public void inject(App app) {
        appComponent.inject(app);
    }
    public void inject(SearchHistoryApi searchHistoryApi) {
        appComponent.inject(searchHistoryApi);
    }
    public void inject(ProductSearchApi productSearchApi) {
        appComponent.inject(productSearchApi);
    }
    public void inject(ResultAdapter resultAdapter) {
        appComponent.inject(resultAdapter);
    }
}
