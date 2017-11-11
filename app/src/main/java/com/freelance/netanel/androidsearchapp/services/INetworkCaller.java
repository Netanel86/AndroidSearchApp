package com.freelance.netanel.androidsearchapp.services;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by Netanel on 11/11/2017.
 */

public interface INetworkCaller {

    void getData(String url, final INetworkCallBack callback);

    interface INetworkCallBack {
        void onSuccess(Reader reader);
        void onFaliure(IOException ex);
    }
}
