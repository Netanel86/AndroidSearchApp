package com.freelance.netanel.androidsearchapp.service.ioc_container.module;


import com.freelance.netanel.androidsearchapp.feature.product.ProductActivity;
import com.freelance.netanel.androidsearchapp.feature.search.SearchActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Netanel on 23/12/2017.
 */

@Module
public abstract class ActivityInjectionModule {

    @ContributesAndroidInjector
    abstract SearchActivity contributeSearchAcvtivtyInjector();
    @ContributesAndroidInjector
    abstract ProductActivity contributeProductAcvtivtyInjector();
}
