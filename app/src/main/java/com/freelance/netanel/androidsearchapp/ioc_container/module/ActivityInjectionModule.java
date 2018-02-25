package com.freelance.netanel.androidsearchapp.ioc_container.module;


import com.freelance.netanel.androidsearchapp.feature.product.ProductActivity;
import com.freelance.netanel.androidsearchapp.feature.search.SearchActivity;
import com.freelance.netanel.androidsearchapp.ioc_container.ProductViewScope;
import com.freelance.netanel.androidsearchapp.ioc_container.SearchViewScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Netanel on 23/12/2017.
 */

@Module
public abstract class ActivityInjectionModule {

    @ContributesAndroidInjector(modules = {LocalDataModule.class, PresenterModule.class})
    @SearchViewScope
    abstract SearchActivity contributeSearchActivityInjector();

    @ContributesAndroidInjector(modules = PresenterModule.class)
    @ProductViewScope
    abstract ProductActivity contributeProductActivityInjector();
}
