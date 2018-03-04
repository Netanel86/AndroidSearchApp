package com.freelance.netanel.androidsearchapp.injection.module;

import com.freelance.netanel.androidsearchapp.service.json_parser.IJsonParser;
import com.freelance.netanel.androidsearchapp.service.json_parser.GsonParser;
import com.freelance.netanel.androidsearchapp.service.network_api.INetworkClient;
import com.freelance.netanel.androidsearchapp.service.network_api.JsonResponseParser;
import com.freelance.netanel.androidsearchapp.service.network_api.OkHttpClientApi;
import com.freelance.netanel.androidsearchapp.service.image_loader.IImageLoader;
import com.freelance.netanel.androidsearchapp.service.image_loader.PicassoLoader;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Netanel on 09/01/2018.
 */
@Module
public abstract class NetworkUtilsModule {

    @Binds
    public abstract IJsonParser provideJsonParser(GsonParser jsonParser);

    @Binds
    public abstract INetworkClient.IResponseParser provideResponseParser(JsonResponseParser jsonResponseParser);

    @Binds
    public abstract INetworkClient provideNetworkClient(OkHttpClientApi networkClientApi);

    @Binds
    public abstract IImageLoader provideImageLoader(PicassoLoader imageLoader);
}
