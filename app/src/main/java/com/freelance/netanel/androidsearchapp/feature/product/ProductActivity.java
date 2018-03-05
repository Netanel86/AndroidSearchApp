package com.freelance.netanel.androidsearchapp.feature.product;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.service.image_loader.IImageLoader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class ProductActivity extends AppCompatActivity
        implements ProductActivityContract.IView, View.OnClickListener  {
    public static final String PRODUCT_EXTRA_KEY = "product_key";

    private static final int PLACEHOLDER_ID = R.drawable.ic_buybuy_logo;

    @BindView(R.id.activity_product_tv_name)
    public TextView tvName;

    @BindView(R.id.activity_product_tv_description)
    public TextView tvDescription;

    @BindView(R.id.activity_product_iv_product)
    public ImageView ivImage;

    @BindView(R.id.activity_product_btn_buy)
    public FloatingActionButton btnBuy;

    @Inject
    public IImageLoader imageLoader;

    @BindView(R.id.activity_product_wv)
    public WebView wvProduct;

    @BindView(R.id.activity_product_vs)
    public ViewSwitcher viewSwitcher;

    @Inject
    public ProductActivityContract.IPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter.bindView(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        presenter.onButtonBackClicked();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();

        btnBuy.setOnClickListener(this);
    }

    @Override
    public void showWebView(String url) {
        wvProduct.setWebViewClient(new WebViewClient());
        wvProduct.loadUrl(url);
    }

    @Override
    public void showViewChild(int childId) {
        viewSwitcher.setDisplayedChild(childId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_product_btn_buy:
                presenter.onButtonBuyClicked();
                break;
        }
    }

    @Override
    public void bindName(String name) {
        tvName.setText(name);
    }

    @Override
    public void bindDescription(String description) {
        tvDescription.setText(description);
    }

    @Override
    public void bindImage(String url) {
        imageLoader.load(url, this, ivImage, PLACEHOLDER_ID);
    }

}
