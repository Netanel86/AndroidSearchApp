package com.freelance.netanel.androidsearchapp.service.ioc_container.module;


import android.content.Context;

import com.freelance.netanel.androidsearchapp.feature.history.repository.HistoryRepository;
import com.freelance.netanel.androidsearchapp.feature.history.repository.IHistoryRepository;
import com.freelance.netanel.androidsearchapp.feature.search.IProductRepository;
import com.freelance.netanel.androidsearchapp.feature.search.ProductRepository;
import com.freelance.netanel.androidsearchapp.service.json_parser.IJsonParser;
import com.freelance.netanel.androidsearchapp.service.network_api.INetworkClient;
import com.freelance.netanel.androidsearchapp.service.shared_pref.AppSharedPreferences;
import com.freelance.netanel.androidsearchapp.service.shared_pref.ISharedPrefRepository;


import javax.inject.Singleton;

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

    @Provides
    @Singleton
    public IProductRepository provideProductRepository(INetworkClient networkClient) {
        return new ProductRepository(networkClient);
    }
}
