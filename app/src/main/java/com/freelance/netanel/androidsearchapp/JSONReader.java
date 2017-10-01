package com.freelance.netanel.androidsearchapp;

import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by Netanel on 24/09/2017.
 */

public class JSONReader implements IJSONReader
{
    @Override
    public JSONObject read(InputStream inputStream) {
        JSONObject jsonObject = null;
        try
        {
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();
            jsonObject = new JSONObject(new String(buffer,"UTF-8"));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return  jsonObject;
    }
}
