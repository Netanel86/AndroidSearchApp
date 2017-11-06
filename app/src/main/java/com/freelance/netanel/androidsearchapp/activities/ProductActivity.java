package com.freelance.netanel.androidsearchapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.model.Product;
import com.freelance.netanel.androidsearchapp.services.BitmapLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.activity_product_tv_name)
    public TextView tvName;

    @BindView(R.id.activity_product_tv_description)
    public TextView tvDescription;

    @BindView(R.id.activity_product_iv_product)
    public ImageView ivImage;

    @BindView(R.id.activity_product_btn_buy)
    public FloatingActionButton btnBuy;

    @BindView(R.id.activity_product_btn_back)
    public ImageButton btnBack;

    private Product product;

    private BitmapLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("product_bundle");

        imageLoader = new BitmapLoader();
        product = parseProduct(bundle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnBuy.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        imageLoader.setImageFetchCallback(new BitmapLoader.IBitmapFetcherCallBack() {
            @Override
            public void onBitmapFetch(Bitmap bmp) {
                if (bmp != null) {
                    ivImage.setImageBitmap(bmp);
                }
            }
        });

        bindProduct();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_product_btn_buy:
                Intent intentProductWebView = new Intent(getApplicationContext(),
                        ProductWebViewActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("product_url",
                        String.format("https://www.shopyourway.com/xxx/%s", product.getId()));
                intentProductWebView.putExtra("webview_bundle", bundle);
                startActivity(intentProductWebView);
                break;

            case R.id.activity_product_btn_back:
                // TODO: 05/11/2017 send back notification if buy button was pressed
                ProductActivity.this.finish();
                break;
        }
    }

    private Product parseProduct(Bundle productBundle) {
        Product product = new Product();

        product.setImageUrl(productBundle.getString("image"));
        product.setName(productBundle.getString("name"));
        product.setDescription(productBundle.getString("description"));
        product.setId(productBundle.getInt("id"));

        return product;
    }

    private void bindProduct() {
        tvName.setText(product.getName());
        tvDescription.setText(product.getDescription());

        ivImage.setImageResource(R.drawable.ic_buybuy_logo);
        imageLoader.loadBitmapFromURL(product.getImageUrl(),ivImage.getMaxHeight());
    }

}
