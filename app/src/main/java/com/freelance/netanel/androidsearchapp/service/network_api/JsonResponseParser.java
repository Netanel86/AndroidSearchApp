package com.freelance.netanel.androidsearchapp.service.network_api;

import com.freelance.netanel.androidsearchapp.service.json_parser.IJsonParser;

import java.io.Reader;
import java.lang.reflect.Type;

import javax.inject.Inject;

/**
 * Created by Netanel on 10/12/2017.
 */

public class JsonResponseParser implements INetworkClient.IResponseParser {
    private IJsonParser jsonParser;

    @Inject
    public JsonResponseParser(IJsonParser jsonParser) {
        this.jsonParser = jsonParser;
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
