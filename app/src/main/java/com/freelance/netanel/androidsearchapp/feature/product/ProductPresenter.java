package com.freelance.netanel.androidsearchapp.feature.product;


import android.content.Context;
import android.content.Intent;

import com.freelance.netanel.androidsearchapp.infra.MvpPresenter;
import com.freelance.netanel.androidsearchapp.model.Product;

/**
 * <p></p>
 *
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 * Created on 25/02/2018
 */

public class ProductPresenter extends MvpPresenter<ProductContract.IView> implements ProductContract.IPresenter {

    private Product product;
    private Context context;

    public ProductPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onButtonBuyClicked() {
        Intent intent = ProductWebViewActivity.prepareIntent(context, product);
        getView().showProductWebView(intent);
    }

    @Override
    public void onButtonBackClicked() {
        getView().closeView();
    }

    @Override
    public void onViewCreated(Product product) {
        this.product = product;
        getView().bindName(product.getName());
        getView().bindDescription(product.getDescription());
        getView().bindImage(product.getImageUrl());
    }
}
