package com.freelance.netanel.androidsearchapp;

import android.content.Context;

import com.freelance.netanel.androidsearchapp.model.Product;
import com.freelance.netanel.androidsearchapp.services.DAO;
import com.freelance.netanel.androidsearchapp.services.IJsonParser;
import com.freelance.netanel.androidsearchapp.services.INetworkClient;
import com.freelance.netanel.androidsearchapp.services.JsonParser;
import com.freelance.netanel.androidsearchapp.services.NetworkClientApi;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Netanel on 01/10/2017.
 */
 public class API {

    private final String DATA_ENDPOINT = "https://platform.shopyourway.com/products/search?" +
            "q=%s&" +
            "page=%s" +
            "&token=0_20975_253402300799_1_39c0fd9abf524b96985688e78892212c05f34203a46ac36a4117f211b41c7f5d&hash=16eba7802b35f6cb1b03dbf6262d4db0808f437a14f070019a6fa98da45b3d90";

    private DAO mDAO;
//    public DAO getDataBase() {
//        return mDAO;
//    }
    private IJsonParser mJsonParser;
    private INetworkClient mNetworkApi;
    private IDataFetcherCallback mCallback;

    public interface IDataFetcherCallback {
        void onDataFetch(List<Product> items);
        void onDataFetchFail(IOException exception);
    }

    public API(Context context) {
        mJsonParser = new JsonParser();
        mNetworkApi = new NetworkClientApi();
        mDAO = new DAO(context);
    }

    public void setDataFetchCallback(IDataFetcherCallback callback) {
        API.this.mCallback = callback;
    }

    public void searchData(String query) {
        String url = String.format(DATA_ENDPOINT, query, 1);
        mNetworkApi.getData(url, new INetworkClient.INetworkCallBack() {
            @Override
            public void onSuccess(Reader reader) {
                Type listType = new TypeToken<List<Product>>() {
                }.getType();

                List<Product> products = mJsonParser.fromJson(reader,
                        listType, "products");

                if (mCallback != null) {
                    mCallback.onDataFetch(products);
                }
            }

            @Override
            public void onFailure(IOException ex) {
                if (mCallback != null) {
                    mCallback.onDataFetchFail(ex);
                }
            }
        });
    }
}