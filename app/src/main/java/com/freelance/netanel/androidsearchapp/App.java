package com.freelance.netanel.androidsearchapp;

import android.app.Activity;
import android.app.Application;

import com.freelance.netanel.androidsearchapp.ioc_container.AppComponent;
import com.freelance.netanel.androidsearchapp.ioc_container.DaggerAppComponent;
import com.freelance.netanel.androidsearchapp.ioc_container.module.ContextModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Netanel on 10/12/2017.
 */

public class App extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private static App instance;
    public static App getInstance() {
        return instance;
    }

    private AppComponent appComponent;
    public AppComponent getInjector() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        App.getInstance().getInjector().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
