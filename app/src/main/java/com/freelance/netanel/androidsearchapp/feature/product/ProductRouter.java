package com.freelance.netanel.androidsearchapp.feature.product;


import com.freelance.netanel.androidsearchapp.model.Product;
import com.freelance.netanel.androidsearchapp.service.json_parser.IJsonParser;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

/**
 * <p></p>
 *
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 * Created on 25/02/2018
 */

public class ProductRouter {
    private WeakReference<ProductActivity> contextReference;
    private IJsonParser jsonParser;

    public ProductRouter(ProductActivity context, IJsonParser jsonParser) {
        this.contextReference = new WeakReference<>(context);
        this.jsonParser = jsonParser;
    }

    public Product getProductFromIntent() {
        String productJson = contextReference.get()
                .getIntent().getExtras().getString(ProductActivity.PRODUCT_EXTRA_KEY);

        return jsonParser.fromJson(productJson,Product.class);
    }

//    public void showProductWebView(int productId) {
//        Intent intent = new Intent(contextReference.get(), ProductWebActivity.class);
//
//        intent.putExtra(ProductWebActivity.PRODUCT_ID_KEY,productId);
//
//        contextReference.get().startActivity(intent);
//    }

    public void closeView() {
        contextReference.get().finish();
    }
}
