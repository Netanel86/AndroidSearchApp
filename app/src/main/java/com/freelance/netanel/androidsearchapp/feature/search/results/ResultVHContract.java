package com.freelance.netanel.androidsearchapp.feature.search.results;

import com.freelance.netanel.androidsearchapp.infra.IMvpPresenter;

/**
 * Created by Netanel on 31/01/2018.
 */

public interface ResultVHContract {
    interface IView {
        void bindName(String name);
        void bindImageUrl(String url);
        void bindDescription(String description);
    }
    interface IPresenter extends IMvpPresenter<ResultVHContract.IView> {
        void onProductClicked();
    }
}
