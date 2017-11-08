package com.freelance.netanel.androidsearchapp;

import android.os.AsyncTask;
import android.util.Log;

import com.freelance.netanel.androidsearchapp.model.Product;
import com.freelance.netanel.androidsearchapp.services.IJSONParser;
import com.freelance.netanel.androidsearchapp.services.JSONParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

import java.net.HttpURLConnection;

/**
 * Created by Netanel on 01/10/2017.
 */
 public class API {

    private final String DATA_ENDPOINT = "https://platform.shopyourway.com/products/search?" +
            "q=shirt&" +
            "page=%s" +
            "&token=0_20975_253402300799_1_39c0fd9abf524b96985688e78892212c05f34203a46ac36a4117f211b41c7f5d&hash=16eba7802b35f6cb1b03dbf6262d4db0808f437a14f070019a6fa98da45b3d90";

    private IJSONParser mJsonParser;
    private FetchDataTask mFetchDataTask;
    private IDataFetcherCallback mCallback;

    public interface IDataFetcherCallback {
        void onDataFetch(List<Product> items);
    }

    public API() {
        mJsonParser = new JSONParser();
    }

    private HttpURLConnection openConnection(URL url) throws IOException {
        java.net.HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(5000);
        connection.connect();
        return connection;
    }

    public void setDataFetchCallback(IDataFetcherCallback callback) {
        API.this.mCallback = callback;
    }

    public void searchData(String query) {
        if (mFetchDataTask != null) {
            mFetchDataTask.cancel(true);
        }
        mFetchDataTask = new FetchDataTask();
        mFetchDataTask.execute(query);
    }

    private class FetchDataTask extends AsyncTask<String, Void, List<Product>> {

        @Override
        protected void onPostExecute(List<Product> products) {

            if (mCallback != null) {
                mCallback.onDataFetch(products);
            }
        }

        @Override
        protected List<Product> doInBackground(String... params) {
            // TODO: 06/11/2017 set query to actually request a search
            String query = params[0];
            List<Product> products = null;
            try {
                HttpURLConnection connection = openConnection(
                        new URL(String.format(DATA_ENDPOINT, 1)));

                Type listType = new TypeToken<List<Product>>() {
                }.getType();
                products = mJsonParser.fromJson(new InputStreamReader(connection.getInputStream()),
                        listType, "products");

            } catch (IOException ex) {
                Log.e(FetchDataTask.class.getSimpleName(), ex.getMessage());
            }
            return products;
        }
    }
}