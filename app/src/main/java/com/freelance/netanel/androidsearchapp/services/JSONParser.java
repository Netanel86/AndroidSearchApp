package com.freelance.netanel.androidsearchapp.services;

import com.freelance.netanel.androidsearchapp.adapters.GsonClassTypeAdapter;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by Netanel on 24/09/2017.
 */

public class JSONParser implements IJSONParser {
    @Override
    public <T> T fromJson(Reader reader, Type typeOfT) {
        GsonBuilder builder = createGsonBuilder(typeOfT, new GsonClassTypeAdapter<T>(typeOfT));
        return builder.create().fromJson(reader, typeOfT);
    }

    @Override
    public <T> T fromJson(Reader reader, Type typeOfT, String memberName) {
        GsonBuilder builder = createGsonBuilder(typeOfT,
                new GsonClassTypeAdapter<T>(typeOfT, memberName));
        return builder.create().fromJson(reader, typeOfT);
    }

    private GsonBuilder createGsonBuilder(Type typeOfT, JsonDeserializer typeAdapter) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(typeOfT, typeAdapter);
        return builder;
    }
}
