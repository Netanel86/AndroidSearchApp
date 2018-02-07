package com.freelance.netanel.androidsearchapp.feature.search.results;

import com.freelance.netanel.androidsearchapp.infra.IMvpCollectionPresenter;
import com.freelance.netanel.androidsearchapp.model.Product;

import java.util.List;

/**
 * Created by Netanel on 31/01/2018.
 */

public interface ResultAdapterContract {
    interface IView {
        void notifyDataSetChanged();
    }

    interface IPresenter extends IMvpCollectionPresenter<IView,Product,ResultVHContract.IPresenter> {
        void setLayoutList();

        void setLayoutGrid();

        void clearAndAddAll(List<Product> products);

        void setCallback(IPresenterCallback callback);

        interface IPresenterCallback{
            void openProduct(Product product);
        }
    }
}
