package com.freelance.netanel.androidsearchapp.domain;

import android.app.Application;

/**
 * Created by Netanel on 10/12/2017.
 */

public class MyApp extends Application{
    private MyComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerMyComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .sharedPreferenceModule(new SharedPreferenceModule())
                .build();

    }

    public MyComponent getComponent() {
        return component;
    }
}
