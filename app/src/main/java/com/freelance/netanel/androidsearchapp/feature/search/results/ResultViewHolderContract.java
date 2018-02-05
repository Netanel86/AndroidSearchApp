package com.freelance.netanel.androidsearchapp.feature.search.results;

import com.freelance.netanel.androidsearchapp.infra.IMvpPresenter;

/**
 * Created by Netanel on 31/01/2018.
 */

public interface ResultViewHolderContract {
    interface IView {
        void bindName(String name);
        void bindImageUrl(String url);
        void bindDescription(String description);
    }
    interface IPresenter extends IMvpPresenter<ResultViewHolderContract.IView> {
        void onProductClicked();
    }
}
