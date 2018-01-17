package com.freelance.netanel.androidsearchapp.service.ioc_container;

import android.content.Context;

import com.freelance.netanel.androidsearchapp.App;
import com.freelance.netanel.androidsearchapp.feature.search.ProductSearchApi;
import com.freelance.netanel.androidsearchapp.feature.history.SearchHistoryApi;
import com.freelance.netanel.androidsearchapp.feature.search.ResultAdapter;
import com.freelance.netanel.androidsearchapp.service.ioc_container.module.ContextModule;

/**
 * <p>This class wraps Google's Dagger library </p>
 * @see dagger
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
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
