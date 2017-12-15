package com.freelance.netanel.androidsearchapp.domain;


import com.freelance.netanel.androidsearchapp.domain.shared_pref.AppSharedPreferences;
import com.freelance.netanel.androidsearchapp.domain.shared_pref.ISharedPrefRepository;


import dagger.Binds;
import dagger.Module;

/**
 * Created by Netanel on 10/12/2017.
 */
@Module
public abstract class ISharedPrefRepositoryModule {
    @Binds
    public abstract ISharedPrefRepository bindISharedPrefRepository(AppSharedPreferences
            appSharedPreferences);

}
