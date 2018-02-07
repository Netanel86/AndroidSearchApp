package com.freelance.netanel.androidsearchapp.feature.search.history;

import com.freelance.netanel.androidsearchapp.infra.IMvpPresenter;

/**
 * Created by Netanel on 06/02/2018.
 */

public interface HistoryVHContract {
    interface IView {
        void bindQuery(String query);
    }
    interface IPresenter extends IMvpPresenter<HistoryVHContract.IView> {

        void onInsertButtonClicked();

        void onItemClicked();
    }
}

