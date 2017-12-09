package com.freelance.netanel.androidsearchapp.domain.services.json;

import com.freelance.netanel.androidsearchapp.domain.services.network.INetworkClient;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * <p>JsonParser represents a service for parsing json input and mapping it to java objects.</p>
 * @see IJsonParser
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */
public class JsonParser implements IJsonParser {

    /**
     * @throws IllegalArgumentException If the input stream is {@code null}.
     */
    @Override
    public <T> T fromJson(Reader reader, Type typeOfT) {
        validateNotNull(reader);
        GsonBuilder builder =
                createGsonBuilder(typeOfT, null);
        return builder.create().fromJson(reader, typeOfT);
    }

    /**
     * @throws IllegalArgumentException If the input stream is {@code null}.
     */
    @Override
    public <T> T fromJson(Reader reader, Type typeOfT, String memberName) {
        validateNotNull(reader);
        GsonBuilder builder =
                createGsonBuilder(typeOfT, memberName);
        return builder.create().fromJson(reader, typeOfT);
    }

    /**
     * @throws IllegalArgumentException If the input stream is {@code null}.
     */
    @Override
    public <T> T fromJson(String json, Type typeOfT) {
        validateNotNull(json);
        GsonBuilder builder =
                createGsonBuilder(typeOfT, null);
        return builder.create().fromJson(json, typeOfT);
    }

    /**
     * @throws IllegalArgumentException If the input stream is {@code null}.
     */
    @Override
    public <T> T fromJson(String json, Type typeOfT, String memberName) {
        validateNotNull(json);
        GsonBuilder builder =
                createGsonBuilder(typeOfT, memberName);
        return builder.create().fromJson(json, typeOfT);
    }

    /***
     * @throws IllegalArgumentException If the object is {@code null}.
     */
    @Override
    public <T> String toJson(T object) {
        validateNotNull(object);
        return new GsonBuilder().create().toJson(object);
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

    private void validateNotNull(Object input) {
        if(input == null){
            throw new IllegalArgumentException("'input' : value is null, the Json input should not be null");
        }
    }
}

