package com.freelance.netanel.androidsearchapp.feature.product;


import com.freelance.netanel.androidsearchapp.infra.MvpPresenter;
import com.freelance.netanel.androidsearchapp.model.Product;

/**
 * <p></p>
 *
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 * Created on 03/03/2018
 */

public class ProductActivityPresenter extends MvpPresenter<ProductActivityContract.IView>
        implements  ProductActivityContract.IPresenter{
    private static final String PRODUCT_URL = "https://www.shopyourway.com/xxx/%s";
    private static final int CHILD_APPVIEW = 0;
    private static final int CHILD_WEBVIEW = 1;

    private int currentChlid;

    private Product product;
    private ProductActivityRouter router;

    public ProductActivityPresenter(ProductActivityRouter router) {
        this.router = router;
    }

    private void setViewChild(int childId) {
        if(currentChlid != childId) {
            currentChlid = childId;
            this.getView().showViewChild(childId);
        }
    }

    @Override
    public void onStart() {
        setViewChild(CHILD_APPVIEW);
    }

    @Override
    public void onButtonBuyClicked() {
        String url = String.format(PRODUCT_URL, product.getId());
        setViewChild(CHILD_WEBVIEW);
        this.getView().showWebView(url);
    }

    @Override
    public void onButtonBackClicked() {
        if(currentChlid == CHILD_WEBVIEW) {
            setViewChild(CHILD_APPVIEW);
        } else {
            router.closeView();
        }
    }

    @Override
    public void bindView(ProductActivityContract.IView view) {
        super.bindView(view);

        product = router.getProductFromIntent();

        getView().bindName(product.getName());
        getView().bindDescription(product.getDescription());
        getView().bindImage(product.getImageUrl());
    }
}
