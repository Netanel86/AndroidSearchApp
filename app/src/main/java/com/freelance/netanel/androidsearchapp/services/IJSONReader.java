package com.freelance.netanel.androidsearchapp.services;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Netanel on 24/09/2017.
 * Supplies an interface for parsing JSON data
 * Implement to create a custom <{@link IJSONReader}
 */
public interface IJSONReader{
    /**
     * Parses a JSON stream to a list of objects
     * @param inputStream a JSON source data stream
     * @return <{@link List} of parsed objects
     */
    List parseJsonStream(InputStream inputStream);
}


