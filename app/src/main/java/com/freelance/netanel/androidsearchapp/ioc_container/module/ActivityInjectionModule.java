package com.freelance.netanel.androidsearchapp.ioc_container.module;


import com.freelance.netanel.androidsearchapp.feature.product.ProductActivity;
import com.freelance.netanel.androidsearchapp.feature.product.ProductActivityModule;
import com.freelance.netanel.androidsearchapp.feature.search.SearchActivity;
import com.freelance.netanel.androidsearchapp.feature.search.SearchActivityModule;
import com.freelance.netanel.androidsearchapp.feature.product.ProductViewScope;
import com.freelance.netanel.androidsearchapp.feature.search.SearchViewScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Netanel on 23/12/2017.
 */

@Module
public abstract class ActivityInjectionModule {

    @ContributesAndroidInjector(modules = {SearchActivityModule.class})
    @SearchViewScope
    abstract SearchActivity contributeSearchActivityInjector();

    @ContributesAndroidInjector(modules = {ProductActivityModule.class})
    @ProductViewScope
    abstract ProductActivity contributeProductActivityInjector();
}
