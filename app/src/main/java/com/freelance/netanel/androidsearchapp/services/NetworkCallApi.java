package com.freelance.netanel.androidsearchapp.services;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Netanel on 11/11/2017.
 */

public class NetworkCallApi implements INetworkCaller {

    private final OkHttpClient mClient = new OkHttpClient();

    public void getData(String url, final INetworkCallBack callback){
        Request request = new Request.Builder()
                .url(url)
                .build();
        doNetworkCall(request,callback);
    }

    private void doNetworkCall(Request request, final INetworkCallBack callback){
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(NetworkCallApi.class.getSimpleName(),e.getMessage());
                if(callback != null){
                    callback.onFaliure(e);
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
