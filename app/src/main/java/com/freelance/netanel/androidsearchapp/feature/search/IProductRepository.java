package com.freelance.netanel.androidsearchapp.feature.search;

import com.freelance.netanel.androidsearchapp.model.Product;

import java.io.IOException;
import java.util.List;

/**
 * Created by Netanel on 17/01/2018.
 */

public interface IProductRepository {
    void searchData(String query, IDataFetcherCallback<List<Product>> callback);

    void fetchProduct(int productId, IDataFetcherCallback<Product> callback);

    interface IDataFetcherCallback<T> {
        void onDataFetch(T data);
        void onDataFetchFail(IOException exception);
    }
}
