package com.freelance.netanel.androidsearchapp.ui.product;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.domain.model.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductWebViewActivity extends AppCompatActivity {
    private static final String ARGUMENT_ID = "product_id_key";
    private static final String BUNDLE_URL = "webview_bundle";
    private static final String PRODUCT_URL  = "https://www.shopyourway.com/xxx/%s";

    /**
     * Prepares an instance of {@link Intent} to start {@link ProductWebViewActivity} activity
     * @param context the {@link Context} of the parent activity.
     * @param product an instance of {@link Product} to open in web viewer.
     * @return an instance of {@link Intent} ready for starting a new {@link ProductWebViewActivity},
     * including the {@link Product} to pass to the new activity.
     */
    public static Intent prepareIntent(Context context, Product product) {
        Intent intent = new Intent(context, ProductWebViewActivity.class);
        Bundle bundle = new Bundle();

        bundle.putInt(ARGUMENT_ID, product.getId());
        intent.putExtra(BUNDLE_URL, bundle);
        return intent;
    }

    @BindView(R.id.wv_product)
    WebView wvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_webview);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BUNDLE_URL);
        int productId = bundle.getInt(ARGUMENT_ID);
        String url = String.format(PRODUCT_URL, productId);

        wvProduct.setWebViewClient(new WebViewClient());
        wvProduct.loadUrl(url);
    }
}