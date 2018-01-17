package com.freelance.netanel.androidsearchapp.domain;


import com.freelance.netanel.androidsearchapp.ui.product_view.ProductActivity;
import com.freelance.netanel.androidsearchapp.ui.search_view.SearchActivity;

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
