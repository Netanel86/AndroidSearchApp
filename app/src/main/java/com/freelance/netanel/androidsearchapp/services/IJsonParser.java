package com.freelance.netanel.androidsearchapp.services;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by Netanel on 24/09/2017.
 * Supplies an interface for parsing JSON data.
 * Implement to create a custom {@link IJsonParser}
 */
public interface IJsonParser {

    /**
     * Parses a data stream containing a json input and map it into a class of the requested type.
     * @param reader a data reader object extending {@link Reader}, containing a json input.
     * @param typeOfT the type of the requested object to map.
     * @param <T> the type of the requested object to map (same as {@code typeOfT}).
     * @return an object of type {@code <T>} mapped from the json input otherwise
     * {@code null}, if no suitable object was found in the json input
     */
    <T> T fromJson(Reader reader, Type typeOfT);

    /**
     * Parses a data stream containing a json input and map it into a class of the requested type.
     * @param reader a data reader object extending {@link Reader}, containing a json input.
     * @param typeOfT the type of the requested object to map.
     * @param memberName the member name of the object in the json input.
     * @param <T> the type of the requested object to map (same as {@code typeOfT}).
     * @return an object of type {@code <T>} mapped from the json input otherwise
     * {@code null}, if no suitable object was found in the json input
     */
    <T> T fromJson(Reader reader, Type typeOfT, String memberName);

    /**
     * Parses a string containing a json input and map it into a class of the requested type.
     * @param json a string containing a json input.
     * @param typeOfT the type of the requested object to map.
     * @param <T> the type of the requested object to map (same as {@code typeOfT}).
     * @return an object of type {@code <T>} mapped from the json input otherwise
     * {@code null}, if no suitable object was found in the json input
     */
    <T> T fromJson(String json, Type typeOfT);

    /**
     * Parses a string containing a json input and map it into a class of the requested type.
     * @param json a string containing a json input.
     * @param typeOfT the type of the requested object to map.
     * @param memberName the member name of the object in the json input.
     * @param <T> the type of the requested object to map (same as {@code typeOfT}).
     * @return an object of type {@code <T>} mapped from the json input otherwise
     * {@code null}, if no suitable object was found in the json input
     */
    <T> T fromJson(String json, Type typeOfT, String memberName);
}


