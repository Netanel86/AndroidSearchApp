package com.freelance.netanel.androidsearchapp.service.ioc_container;

import android.content.Context;

import com.freelance.netanel.androidsearchapp.service.ioc_container.module.ContextModule;

/**
 * <p>This class wraps Google's Dagger library </p>
 * @see dagger
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */

public class Injection {
    private static Injection instance;

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new Injection(context);
        } else {
            throw new UnsupportedOperationException("Injection is already instantiated");
        }
    }

    private static AppComponent appComponent;

    public static AppComponent getInjector() {
        if (instance == null) {
            throw new UnsupportedOperationException(
                    "Injection was not initialized, use initialize() before getter.");
        }
        return appComponent;
    }

    private Injection(Context context) {
        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(context))
                .build();
    }
}
