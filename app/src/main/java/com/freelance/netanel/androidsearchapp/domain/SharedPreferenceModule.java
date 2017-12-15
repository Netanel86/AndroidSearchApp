package com.freelance.netanel.androidsearchapp.domain;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Netanel on 10/12/2017.
 */
@Module
public class SharedPreferenceModule {
    private static final String RESOURCE_APP_NAME = "RES_APP";

    @Provides
    @Inject
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(RESOURCE_APP_NAME, Context.MODE_PRIVATE);
    }
}
