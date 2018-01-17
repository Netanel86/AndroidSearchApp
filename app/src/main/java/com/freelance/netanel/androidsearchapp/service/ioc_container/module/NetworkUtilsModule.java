package com.freelance.netanel.androidsearchapp.domain;

import com.freelance.netanel.androidsearchapp.domain.json.IJsonParser;
import com.freelance.netanel.androidsearchapp.domain.json.JsonParser;
import com.freelance.netanel.androidsearchapp.domain.network_api.INetworkClient;
import com.freelance.netanel.androidsearchapp.domain.network_api.JsonResponseParser;
import com.freelance.netanel.androidsearchapp.domain.network_api.NetworkClientApi;
import com.freelance.netanel.androidsearchapp.ui.product_view.IImageLoader;
import com.freelance.netanel.androidsearchapp.ui.product_view.ImageLoader;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Netanel on 09/01/2018.
 */
@Module
abstract class NetworkUtilsModule {

    @Binds
    public abstract IJsonParser provideJsonParser(JsonParser jsonParser);

    @Binds
    public abstract INetworkClient.IResponseParser provideResponseParser(JsonResponseParser jsonResponseParser);

    @Binds
    public abstract INetworkClient provideNetworkClient(NetworkClientApi networkClientApi);

    @Binds
    public abstract IImageLoader provideImageLoader(ImageLoader imageLoader);
}
