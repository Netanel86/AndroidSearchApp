package com.freelance.netanel.androidsearchapp.feature.search;

import com.freelance.netanel.androidsearchapp.infra.MvpPresenter;
import com.freelance.netanel.androidsearchapp.model.Product;

/**
 * Created by Netanel on 31/01/2018.
 */

public class ResultViewHolderPresenter extends MvpPresenter<ResultViewHolderContract.IView>
        implements ResultViewHolderContract.IPresenter {

    private Product product;
    private IProductPresenterCallback callback;
    interface IProductPresenterCallback {
        void onProductClicked(Product product);
    }

    public void setCallback(IProductPresenterCallback callback) {
        this.callback = callback;
    }

    @Override
    public void bindView(ResultViewHolderContract.IView view) {
        super.bindView(view);
        this.getView().bindName(product.getName());
        this.getView().bindDescription(product.getDescription());
        this.getView().bindImageUrl(product.getImageUrl());
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void onProductClicked() {
        if(callback!=null) {
            callback.onProductClicked(product);
        }
    }
}
