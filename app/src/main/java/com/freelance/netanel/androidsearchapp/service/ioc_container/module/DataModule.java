package com.freelance.netanel.androidsearchapp.domain;


import android.content.Context;
import android.content.SharedPreferences;

import com.freelance.netanel.androidsearchapp.domain.history_repo.HistoryRepository;
import com.freelance.netanel.androidsearchapp.domain.history_repo.IHistoryRepository;
import com.freelance.netanel.androidsearchapp.domain.json.IJsonParser;
import com.freelance.netanel.androidsearchapp.domain.shared_pref.AppSharedPreferences;
import com.freelance.netanel.androidsearchapp.domain.shared_pref.ISharedPrefRepository;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Netanel on 10/12/2017.
 */
@Module
public class DataModule {
    private static final String RESOURCE_APP_NAME = "RES_APP";

    @Provides
    @Singleton
    public ISharedPrefRepository providesISharedPrefRepository(Context context){
        return new AppSharedPreferences(context.getSharedPreferences(RESOURCE_APP_NAME, Context.MODE_PRIVATE));
    }

    @Provides
    @Singleton
    public IHistoryRepository provideHistoryRepository(ISharedPrefRepository sharedPrefRepository, IJsonParser jsonParser) {
        return new HistoryRepository(sharedPrefRepository, jsonParser);
    }
}
