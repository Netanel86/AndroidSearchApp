package com.freelance.netanel.androidsearchapp.feature.search;

import com.freelance.netanel.androidsearchapp.model.Product;

import java.io.IOException;
import java.util.List;

/**
 * Created by Netanel on 17/01/2018.
 */

public interface IProductRepository {
    void setDataFetchCallback(IDataFetcherCallback callback);

    void searchData(String query);

    interface IDataFetcherCallback {
        void onDataFetch(List<Product> items);
        void onDataFetchFail(IOException exception);
    }
}
