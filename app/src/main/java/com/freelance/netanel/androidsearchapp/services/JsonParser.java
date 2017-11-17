package com.freelance.netanel.androidsearchapp.services;

import com.freelance.netanel.androidsearchapp.adapters.GsonClassTypeAdapter;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * <p>JsonParser represents a service for parsing json input and mapping it to java objects.</p>
 * @see IJSONParser
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */
public class JsonParser implements IJSONParser {

    /**
     * @throws IllegalArgumentException If the input stream is {@code null}.
     */
    @Override
    public <T> T fromJson(Reader reader, Type typeOfT) {
        validateJson(reader);
        GsonBuilder builder =
                createGsonBuilder(typeOfT, null);
        return builder.create().fromJson(reader, typeOfT);
    }

    /**
     * @throws IllegalArgumentException If the input stream is {@code null}.
     */
    @Override
    public <T> T fromJson(Reader reader, Type typeOfT, String memberName) {
        validateJson(reader);
        GsonBuilder builder =
                createGsonBuilder(typeOfT, memberName);
        return builder.create().fromJson(reader, typeOfT);
    }

    /**
     * @throws IllegalArgumentException If the input stream is {@code null}.
     */
    @Override
    public <T> T fromJson(String json, Type typeOfT) {
        validateJson(json);
        GsonBuilder builder =
                createGsonBuilder(typeOfT, null);
        return builder.create().fromJson(json, typeOfT);
    }

    /**
     * @throws IllegalArgumentException If the input stream is {@code null}.
     */
    @Override
    public <T> T fromJson(String json, Type typeOfT, String memberName) {
        validateJson(json);
        GsonBuilder builder =
                createGsonBuilder(typeOfT, memberName);
        return builder.create().fromJson(json, typeOfT);
    }

    private <T> GsonBuilder createGsonBuilder(Type typeOfT, String memberName) {
        GsonBuilder builder = new GsonBuilder();
        GsonClassTypeAdapter adapter;
        if(memberName == null || memberName.isEmpty()){
            adapter = new GsonClassTypeAdapter<T>(typeOfT);
        }else {
            adapter = new GsonClassTypeAdapter<T>(typeOfT, memberName);
        }
        builder.registerTypeAdapter(typeOfT, adapter);
        return builder;
    }

    private void validateJson(Object jsonInput) {
        if(jsonInput == null){
            throw new IllegalArgumentException("'jsonInput' : value is null, the Json input should not be null");
        }
    }
}

