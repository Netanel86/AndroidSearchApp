package com.freelance.netanel.androidsearchapp.domain.services.network;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by Netanel on 11/11/2017.
 */

public interface INetworkClient {

    <T> void getData(String url, String parameter, Type typeOfT,
            final INetworkCallBack<T> callback);

    interface IResponseParser {
        <T> T parse(Reader reader, String parameter, final Type typeOfT);
    }

    interface INetworkCallBack<TReturn> {
        void onSuccess(TReturn result);

        void onFailure(IOException ex);
    }
}
