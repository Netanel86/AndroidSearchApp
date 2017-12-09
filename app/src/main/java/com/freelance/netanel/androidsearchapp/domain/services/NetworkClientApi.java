package com.freelance.netanel.androidsearchapp.domain.services;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    public void getData(String url, final INetworkCallBack callback){
        Request request = new Request.Builder()
                .url(url)
                .build();
        doNetworkCall(request,callback);
    }

    private void doNetworkCall(Request request, final INetworkCallBack callback){
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(NetworkClientApi.class.getSimpleName(),e.getMessage());
                if(callback != null){
                    callback.onFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream responseStream = response.body().byteStream();

                if (callback != null) {
                    callback.onSuccess(new InputStreamReader(responseStream));
                }
            }
        });
    }
}
