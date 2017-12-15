package com.freelance.netanel.androidsearchapp.domain;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Netanel on 10/12/2017.
 */

@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }
}
