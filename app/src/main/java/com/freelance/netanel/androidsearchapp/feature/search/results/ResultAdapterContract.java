package com.freelance.netanel.androidsearchapp.feature.search.results;

import com.freelance.netanel.androidsearchapp.infra.IMvpPresenter;
import com.freelance.netanel.androidsearchapp.model.Product;

import java.util.List;

/**
 * Created by Netanel on 31/01/2018.
 */

public interface ResultAdapterContract {
    interface IView {
        void notifyDataSetChanged();
    }

    interface IPresenter extends IMvpPresenter<ResultAdapterContract.IView> {
        boolean isListEmpty();
        int getItemCount();
        void setCallback(IPresenterCallback callback);
        void clearAndAddAll(List<Product> products);
        int getItemViewType();
        void setLayoutList();
        void setLayoutGrid();
        ResultViewHolderContract.IPresenter getPresenter(int position);

        interface IPresenterCallback{
            void openProduct(Product product);
        }
    }
}
