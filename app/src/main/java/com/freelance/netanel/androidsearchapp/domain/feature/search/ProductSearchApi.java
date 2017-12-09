package com.freelance.netanel.androidsearchapp.domain.feature.search;

import android.content.Context;

import com.freelance.netanel.androidsearchapp.domain.model.Product;
import com.freelance.netanel.androidsearchapp.domain.services.INetworkClient;
import com.freelance.netanel.androidsearchapp.domain.services.NetworkClientApi;
import com.freelance.netanel.androidsearchapp.domain.services.json.IJsonParser;
import com.freelance.netanel.androidsearchapp.domain.services.json.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Netanel on 01/10/2017.
 */
 public class ProductSearchApi {

    private final String DATA_ENDPOINT = "https://platform.shopyourway.com/products/search?" +
            "q=%s&" +
            "page=%s" +
            "&token=0_20975_253402300799_1_39c0fd9abf524b96985688e78892212c05f34203a46ac36a4117f211b41c7f5d&hash=16eba7802b35f6cb1b03dbf6262d4db0808f437a14f070019a6fa98da45b3d90";

    private IJsonParser jsonParser;
    private INetworkClient networkApi;
    private IDataFetcherCallback callback;

    public interface IDataFetcherCallback {
        void onDataFetch(List<Product> items);
        void onDataFetchFail(IOException exception);
    }

    public ProductSearchApi(Context context) {
        jsonParser = new JsonParser();
        networkApi = new NetworkClientApi();
    }

    public void setDataFetchCallback(IDataFetcherCallback callback) {
        ProductSearchApi.this.callback = callback;
    }

    public void searchData(String query) {
        String url = String.format(DATA_ENDPOINT, query, 1);
        networkApi.getData(url, new INetworkClient.INetworkCallBack() {
            @Override
            public void onSuccess(Reader reader) {
                Type listType = new TypeToken<List<Product>>() {
                }.getType();

                List<Product> products = jsonParser.fromJson(reader,
                        listType, "products");

                if (callback != null) {
                    callback.onDataFetch(products);
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