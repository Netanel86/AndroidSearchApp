package com.freelance.netanel.androidsearchapp;

import android.os.AsyncTask;
import android.util.Log;

import com.freelance.netanel.androidsearchapp.model.Product;
import com.freelance.netanel.androidsearchapp.services.IJSONReader;
import com.freelance.netanel.androidsearchapp.services.JSONReader;

import java.io.IOException;
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

    private IJSONReader jsonReader;

    private FetchDataTask dataFetchTask;
    private IDataFetcherCallback callback;

    public interface IDataFetcherCallback {
        void onDataFetch(List<Product> items);
    }

    public API() {
        jsonReader = new JSONReader();
    }

    private HttpURLConnection openConnection(URL url) throws IOException {
        java.net.HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(5000);
        connection.connect();
        return connection;
    }

    public void setDataFetchCallback(IDataFetcherCallback callback) {
        API.this.callback = callback;
    }

    public void searchData(String query) {
        if (dataFetchTask != null) {
            dataFetchTask.cancel(true);
        } else {
            dataFetchTask = new FetchDataTask();
            dataFetchTask.execute(query);
        }
    }

    private class FetchDataTask extends AsyncTask<String, Void, List<Product>> {

        @Override
        protected void onPostExecute(List<Product> products) {
            if (callback != null) {
                callback.onDataFetch(products);
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

                products = jsonReader.parseJsonStream(connection.getInputStream());

            } catch (IOException ex) {
                Log.e(FetchDataTask.class.getSimpleName(), ex.getMessage());
            }
            return products;
        }
    }
}
