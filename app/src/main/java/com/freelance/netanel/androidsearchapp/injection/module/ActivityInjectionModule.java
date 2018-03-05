package com.freelance.netanel.androidsearchapp.injection.module;


import com.freelance.netanel.androidsearchapp.feature.product.ProductActivity;
import com.freelance.netanel.androidsearchapp.feature.product.ProductActivityModule;
import com.freelance.netanel.androidsearchapp.injection.PerActivity;
import com.freelance.netanel.androidsearchapp.feature.search.SearchActivity;
import com.freelance.netanel.androidsearchapp.feature.search.SearchActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Netanel on 23/12/2017.
 */

@Module
public abstract class ActivityInjectionModule {

    @PerActivity
    @ContributesAndroidInjector(modules = {SearchActivityModule.class})
    abstract SearchActivity contributeSearchActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = {ProductActivityModule.class})
    abstract ProductActivity contributeProductActivityInjector();
}
