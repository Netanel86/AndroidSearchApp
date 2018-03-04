package com.freelance.netanel.androidsearchapp.feature.product;


import android.content.Context;

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
    @ProductViewScope
    public ProductRouter provideProductRouter(ProductActivity context, IJsonParser jsonParser) {
        return new ProductRouter(context,jsonParser);
    }

    @Provides
    @ProductViewScope
    public ProductActivityContract.IPresenter provideProductActivityPresenter(
            ProductRouter router) {
        return new ProductActivityPresenter(router);
    }
}
