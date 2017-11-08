package com.freelance.netanel.androidsearchapp.services;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by Netanel on 24/09/2017.
 * Supplies an interface for parsing JSON data
 * Implement to create a custom <{@link IJSONParser}
 */
public interface IJSONParser {

    <T> T fromJson(Reader reader, Type typeOfT);
    <T> T fromJson(Reader reader, Type typeOfT, String memberName);
}


