package com.freelance.netanel.androidsearchapp;

import android.app.Activity;
import android.app.Application;

import com.freelance.netanel.androidsearchapp.service.ioc_container.Injector;

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

    private Injector injector;

    @Override
    public void onCreate() {
        super.onCreate();
        injector = new Injector(this);
        injector.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
