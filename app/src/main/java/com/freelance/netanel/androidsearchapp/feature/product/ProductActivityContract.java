package com.freelance.netanel.androidsearchapp.feature.product;


import com.freelance.netanel.androidsearchapp.infra.IMvpPresenter;

/**
 * <p></p>
 *
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 * Created on 03/03/2018
 */

public interface ProductActivityContract {
    interface IView {
        void bindName(String name);
        void bindDescription(String description);
        void bindImage(String url);
        void showWebView(String url);
        void showViewChild(int childId);
    }

    interface IPresenter extends IMvpPresenter<ProductActivityContract.IView> {
        void onButtonBuyClicked();
        void onButtonBackClicked();
        void onStart();
    }
}
