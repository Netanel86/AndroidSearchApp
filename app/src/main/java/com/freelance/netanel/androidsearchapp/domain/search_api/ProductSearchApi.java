package com.freelance.netanel.androidsearchapp.domain.search_api;

import com.freelance.netanel.androidsearchapp.domain.model.Product;
import com.freelance.netanel.androidsearchapp.domain.network_api.INetworkClient;
import com.freelance.netanel.androidsearchapp.domain.network_api.JsonResponseParser;
import com.freelance.netanel.androidsearchapp.domain.network_api.NetworkClientApi;
import com.freelance.netanel.androidsearchapp.domain.json.TypeOfT;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Netanel on 01/10/2017.
 */
 public class ProductSearchApi {

    private final String DATA_ENDPOINT = "https://platform.shopyourway.com/products/search?" +
            "q=%s&" +
            "page=%s" +
            "&token=0_20975_253402300799_1_39c0fd9abf524b96985688e78892212c05f34203a46ac36a4117f211b41c7f5d&hash=16eba7802b35f6cb1b03dbf6262d4db0808f437a14f070019a6fa98da45b3d90";

    private static final String PRODUCTS_MEMBER_NAME = "products";
    @Inject
    public INetworkClient networkApi;
    private IDataFetcherCallback callback;

    public interface IDataFetcherCallback {
        void onDataFetch(List<Product> items);
        void onDataFetchFail(IOException exception);
    }

    public ProductSearchApi() {
        networkApi = new NetworkClientApi(new JsonResponseParser());
    }

    public void setDataFetchCallback(IDataFetcherCallback callback) {
        ProductSearchApi.this.callback = callback;
    }

    public void searchData(String query) {
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
}