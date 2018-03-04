package com.freelance.netanel.androidsearchapp.ioc_container;


import com.freelance.netanel.androidsearchapp.App;
import com.freelance.netanel.androidsearchapp.feature.search.results.ResultViewHolder;
import com.freelance.netanel.androidsearchapp.ioc_container.module.ActivityInjectionModule;
import com.freelance.netanel.androidsearchapp.ioc_container.module.ContextModule;
import com.freelance.netanel.androidsearchapp.ioc_container.module.DataModule;
import com.freelance.netanel.androidsearchapp.ioc_container.module.NetworkUtilsModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Netanel on 10/12/2017.
 */
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        ActivityInjectionModule.class,
        ContextModule.class,
        NetworkUtilsModule.class,
        DataModule.class
})
@Singleton
public interface AppComponent extends AndroidInjector<App> {
    void inject(ResultViewHolder viewHolderProduct);
}