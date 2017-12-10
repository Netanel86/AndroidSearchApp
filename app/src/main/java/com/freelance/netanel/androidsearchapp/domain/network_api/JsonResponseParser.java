package com.freelance.netanel.androidsearchapp.domain.network_api;

import com.freelance.netanel.androidsearchapp.domain.json.IJsonParser;
import com.freelance.netanel.androidsearchapp.domain.json.JsonParser;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by Netanel on 10/12/2017.
 */

public class JsonResponseParser implements INetworkClient.IResponseParser {
    private IJsonParser jsonParser;

    public JsonResponseParser() {
        jsonParser = new JsonParser();
    }

    @Override
    public <T> T parse(Reader reader, String parameter, Type typeOfT) {
        if (parameter == null || parameter.isEmpty()) {
            return jsonParser.fromJson(reader, typeOfT);
        } else {
            return jsonParser.fromJson(reader,
                    typeOfT, parameter);
        }
    }
}
