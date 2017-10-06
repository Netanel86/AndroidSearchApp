package com.freelance.netanel.androidsearchapp;

import android.content.Context;

import com.freelance.netanel.androidsearchapp.model.Product;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import java.net.HttpURLConnection;

/**
 * Created by Netanel on 01/10/2017.
 */
 class API {

    private final String DATA_ENDPOINT = "https://platform.shopyourway.com/products/search?q=shirt&page=1&token=0_20975_253402300799_1_39c0fd9abf524b96985688e78892212c05f34203a46ac36a4117f211b41c7f5d&hash=16eba7802b35f6cb1b03dbf6262d4db0808f437a14f070019a6fa98da45b3d90";

    IJSONReader jsonReader;
    IListItemParser resultsParser;

    public API()
    {
        jsonReader = new JSONReader();
        resultsParser = new SearchItemParser();
    }

    private HttpURLConnection openConnection(URL url) throws IOException
    {
        java.net.HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(5000);
        connection.connect();
        return  connection;
    }

    public List<Product> get() throws IOException {
        JSONObject jsonObject;

        HttpURLConnection connection = openConnection(new URL(DATA_ENDPOINT));
        jsonObject = jsonReader.read(connection.getInputStream());

//        jsonObject = jsonReader.read(context.getResources().openRawResource(R.raw.search));

        return resultsParser.parse(jsonObject);
    }



}
