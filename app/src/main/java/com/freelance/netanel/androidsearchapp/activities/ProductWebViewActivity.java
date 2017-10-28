package com.freelance.netanel.androidsearchapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.freelance.netanel.androidsearchapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductWebViewActivity extends AppCompatActivity {

    @BindView(R.id.wv_product)
    WebView wvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_webview);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("webview_bundle");
        String url = bundle.getString("product_url");


        wvProduct.setWebViewClient(new WebViewClient());
        wvProduct.loadUrl(url);
    }
}