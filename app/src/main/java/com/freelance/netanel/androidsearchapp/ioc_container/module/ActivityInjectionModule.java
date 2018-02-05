package com.freelance.netanel.androidsearchapp.ioc_container.module;


import com.freelance.netanel.androidsearchapp.feature.product.ProductActivity;
import com.freelance.netanel.androidsearchapp.feature.search.SearchActivity;
import com.freelance.netanel.androidsearchapp.ioc_container.SearchScreenScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Netanel on 23/12/2017.
 */

@Module
public abstract class ActivityInjectionModule {

    @ContributesAndroidInjector(modules = {PresenterModule.class})
    @SearchScreenScope
    abstract SearchActivity contributeSearchActivityInjector();

    @ContributesAndroidInjector
    abstract ProductActivity contributeProductActivityInjector();
}
