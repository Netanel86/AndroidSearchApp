package com.freelance.netanel.androidsearchapp.feature.product;


import com.freelance.netanel.androidsearchapp.injection.PerActivity;
import com.freelance.netanel.androidsearchapp.service.json_parser.IJsonParser;

import dagger.Module;
import dagger.Provides;

/**
 * <p></p>
 *
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 * Created on 03/03/2018
 */

@Module
public class ProductActivityModule {

    @Provides
    @PerActivity
    public IProductActivityRouter provideProductRouter(ProductActivity context, IJsonParser jsonParser) {
        return new ProductActivityRouter(context,jsonParser);
    }

    @Provides
    @PerActivity
    public ProductActivityContract.IPresenter provideProductActivityPresenter(
            ProductActivityRouter router) {
        return new ProductActivityPresenter(router);
    }
}
