package com.freelance.netanel.androidsearchapp;

import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by Netanel on 24/09/2017.
 */

public interface IJSONReader
{
    JSONObject read(InputStream inputStream);
}


