package com.freelance.netanel.androidsearchapp.domain.network_api;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;


import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Netanel on 11/11/2017.
 */

public class NetworkClientApi implements INetworkClient {

    private final OkHttpClient client = new OkHttpClient();

    private IResponseParser responseParser;

    @Inject
    public NetworkClientApi(IResponseParser parser) {
        if (parser == null) {
            throw new IllegalArgumentException(
                    IResponseParser.class.getSimpleName() + ": 'parser' cant be null");
        }
        this.responseParser = parser;
    }

    @Override
    public <T> void getData(String url, String parameter, Type typeOfT,
            final INetworkCallBack<T> callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        doNetworkCall(request, parameter, typeOfT, callback);
    }

    private <T> void doNetworkCall(Request request, final String parameter, final Type typeOfT,
            final INetworkCallBack<T> callback) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(NetworkClientApi.class.getSimpleName(), e.getMessage());
                if (callback != null) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream responseStream = response.body().byteStream();
                T parsed = responseParser.parse(new InputStreamReader(responseStream), parameter,
                        typeOfT);
                if (callback != null) {
                    callback.onSuccess(parsed);
                }
            }
        });
    }
}
