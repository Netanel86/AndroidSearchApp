package com.freelance.netanel.androidsearchapp.feature.search.results;

import android.support.annotation.NonNull;

import com.freelance.netanel.androidsearchapp.infra.MvpCollectionPresenter;
import com.freelance.netanel.androidsearchapp.model.Product;

import java.util.List;

/**
 * Created by Netanel on 31/01/2018.
 */

public class ResultAdapterPresenter
        extends MvpCollectionPresenter<ResultAdapterContract.IView,Product,ResultVHContract.IPresenter>
        implements ResultAdapterContract.IPresenter {
    public static final int LAYOUT_TYPE_LIST = 1;
    public static final int LAYOUT_TYPE_GRID = 2;
    private static final int VIEWTYPE_LIST_EMPTY = 3;
    private static final int VIEWTYPE_GRID_EMPTY = 4;
    public static final int VIEWTYPE_LIST_ITEM = 5;
    public static final int VIEWTYPE_GRID_ITEM = 6;

    private int currentLayout = LAYOUT_TYPE_LIST;
    private IPresenterCallback callBack;

    @Override
    public int getItemViewType(int position) {
        int viewtype = 0;

        switch (currentLayout) {
            case LAYOUT_TYPE_LIST:
                if(isEmpty()){
                    viewtype = VIEWTYPE_LIST_EMPTY;
                } else {
                    viewtype = VIEWTYPE_LIST_ITEM;
                }
                break;
            case LAYOUT_TYPE_GRID:
                if(isEmpty()) {
                    viewtype = VIEWTYPE_GRID_EMPTY;
                } else {
                    viewtype= VIEWTYPE_GRID_ITEM;
                }
                break;
        }

        return viewtype;
    }

    public void setCallback(IPresenterCallback callback) {
        this.callBack = callback;
    }

    @Override
    public void setLayoutList() {
        this.currentLayout = LAYOUT_TYPE_LIST;
        this.getView().notifyDataSetChanged();
    }

    @Override
    public void setLayoutGrid() {
        this.currentLayout = LAYOUT_TYPE_GRID;
        this.getView().notifyDataSetChanged();
    }

    @Override
    public Object getPresenterKey(int position) {
        return getItem(position).getId();
    }

    @Override
    protected Object getPresenterKey(@NonNull Product model) {
        return model.getId();
    }

    @Override
    protected ResultVHContract.IPresenter createPresenter(@NonNull Product model) {
        ResultVHPresenter presenter = new ResultVHPresenter();
        presenter.setProduct(model);
        presenter.setCallback(new ResultVHPresenter.IProductPresenterCallback() {
            @Override
            public void onProductClicked(Product product) {
                if(callBack != null) {
                    callBack.openProduct(product);
                }
            }
        });
        return presenter;
    }

    public void clearAndAddAll(List<Product> products) {
        this.clearAll();
        this.addAll(products);

        getView().notifyDataSetChanged();
    }
}
