package com.freelance.netanel.androidsearchapp.feature.product;


import android.content.Intent;

import com.freelance.netanel.androidsearchapp.infra.IMvpPresenter;
import com.freelance.netanel.androidsearchapp.model.Product;

/**
 * <p></p>
 *
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 * Created on 25/02/2018
 */

public interface ProductContract {
    interface IView {
        void showProductWebView(Intent intent);
        void closeView();
        void bindName(String name);
        void bindDescription(String description);
        void bindImage(String url);
    }

    interface IPresenter extends IMvpPresenter<ProductContract.IView> {

        void onButtonBuyClicked();

        void onButtonBackClicked();

        void onViewCreated(Product product);
    }
}
