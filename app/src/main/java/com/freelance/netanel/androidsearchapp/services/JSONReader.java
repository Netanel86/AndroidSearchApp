package com.freelance.netanel.androidsearchapp.services;

import com.freelance.netanel.androidsearchapp.model.Product;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Netanel on 24/09/2017.
 */

public class JSONReader implements IJSONReader {
    @Override
    public List parseJsonStream(InputStream inputStream) {
        JSONBase base = new Gson().fromJson(new InputStreamReader(inputStream),JSONBase.class);
        return base.products;
    }

    private class JSONBase
    {
        public int count;
        public ArrayList<Product> products;
    }
}
