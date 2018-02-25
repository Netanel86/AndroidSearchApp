package com.freelance.netanel.androidsearchapp.feature.search;

import com.freelance.netanel.androidsearchapp.model.Product;
import com.freelance.netanel.androidsearchapp.service.network_api.INetworkClient;
import com.freelance.netanel.androidsearchapp.service.json_parser.TypeOfT;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Netanel on 01/10/2017.
 */
 public class ProductRepository implements IProductRepository {

    private final String DATA_ENDPOINT = "https://platform.shopyourway.com/products/search?" +
            "q=%s" +
            "&page=%s" +
            "&token=0_20975_253402300799_1_39c0fd9abf524b96985688e78892212c05f34203a46ac36a4117f211b41c7f5d" +
            "&hash=16eba7802b35f6cb1b03dbf6262d4db0808f437a14f070019a6fa98da45b3d90";

    private final String PRODUCT_ENDPOINT = "https://platform.shopyourway.com/products/get?" +
            "ids=%d";

    private static final String PRODUCTS_MEMBER_NAME = "products";

    private INetworkClient networkApi;

    public ProductRepository(INetworkClient networkApi) {
        this.networkApi = networkApi;
    }

    @Override
    public void searchData(String query,final IDataFetcherCallback<List<Product>> callback) {
        String url = String.format(DATA_ENDPOINT, query, 1);
        Type resultType = new TypeOfT<List<Product>>() {
        }.getType();
        networkApi.getData(url, PRODUCTS_MEMBER_NAME, resultType,
                new INetworkClient.INetworkCallBack<List<Product>>() {
                    @Override
                    public void onSuccess(List<Product> result) {
                        if (callback != null) {
                            callback.onDataFetch(result);
                        }
                    }

                    @Override
                    public void onFailure(IOException ex) {
                        if (callback != null) {
                            callback.onDataFetchFail(ex);
                        }
                    }
                });
    }

    @Override
    public void fetchProduct(int productId, final IDataFetcherCallback<Product> callback) {
        String url = String.format(PRODUCT_ENDPOINT, productId);
        networkApi.getData(url, null, Product.class, new INetworkClient.INetworkCallBack<Product>() {
            @Override
            public void onSuccess(Product result) {
                callback.onDataFetch(result);
            }

            @Override
            public void onFailure(IOException ex) {
                callback.onDataFetchFail(ex);
            }
        });
    }
}