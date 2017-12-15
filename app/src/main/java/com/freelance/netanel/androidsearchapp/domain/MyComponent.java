package com.freelance.netanel.androidsearchapp.domain;


import com.freelance.netanel.androidsearchapp.domain.history_repo.HistoryRepository;

import dagger.Component;

/**
 * Created by Netanel on 10/12/2017.
 */
@Component(modules = {
        ContextModule.class,
        SharedPreferenceModule.class,
        ISharedPrefRepositoryModule.class
})
public interface MyComponent {
    void inject(HistoryRepository app);
}