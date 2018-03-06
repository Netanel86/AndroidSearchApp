package com.freelance.netanel.androidsearchapp.feature.search;


import android.content.Intent;
import android.net.Uri;

import com.freelance.netanel.androidsearchapp.feature.product.ProductActivity;
import com.freelance.netanel.androidsearchapp.model.Product;
import com.freelance.netanel.androidsearchapp.service.json_parser.IJsonParser;

import java.lang.ref.WeakReference;

/**
 * <p></p>
 *
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 * Created on 25/02/2018
 */

public class SearchActivityRouter implements ISearchActivityRouter {

    private WeakReference<SearchActivity> contextReference;
    private IJsonParser jsonParser;

    public SearchActivityRouter(SearchActivity context, IJsonParser jsonParser) {
        this.contextReference = new WeakReference<>(context);
        this.jsonParser = jsonParser;
        parseIntentData();
    }

    public void showProductView(Product product) {
        Intent intent = new Intent(contextReference.get(), ProductActivity.class);
        String productJson = jsonParser.toJson(product);
        intent.putExtra(ProductActivity.PRODUCT_EXTRA_KEY, productJson);
        contextReference.get().startActivity(intent);
    }

    // TODO: 03/03/2018 create a dynamic router
    public void parseIntentData() {
        Uri data = contextReference.get().getIntent().getData();
        if(data != null && data.isHierarchical()) {
            contextReference.get().showLongToast("My Uri:" + data);
        }
    }

    public void closeView() {
        contextReference.get().finish();
    }
}
