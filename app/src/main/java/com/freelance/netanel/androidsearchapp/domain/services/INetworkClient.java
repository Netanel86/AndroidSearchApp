package com.freelance.netanel.androidsearchapp.domain.services;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by Netanel on 11/11/2017.
 */

public interface INetworkClient {

    void getData(String url, final INetworkCallBack callback);

    interface INetworkCallBack {
        void onSuccess(Reader reader);
        void onFailure(IOException ex);
    }
}
