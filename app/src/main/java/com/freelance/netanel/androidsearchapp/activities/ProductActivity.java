package com.freelance.netanel.androidsearchapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.model.Product;
import com.freelance.netanel.androidsearchapp.services.FetchImageTask;

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

    private FetchImageTask imageLoadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("product_bundle");

        pullAndSetProduct(bundle);

        btnBuy.setOnClickListener(this);
        btnBack.setOnClickListener(this);

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
                        String.format("https://www.shopyourway.com/xxx/%s", product.id));
                intentProductWebView.putExtra("webview_bundle", bundle);
                startActivity(intentProductWebView);
                break;

            case R.id.activity_product_btn_back:
                ProductActivity.this.finish();
                break;
        }
    }

    private void pullAndSetProduct(Bundle productBundle)
    {
        product = new Product();

        product.imageUrl = productBundle.getString("image");
        product.name = productBundle.getString("name");
        product.description = productBundle.getString("description");
        product.id = productBundle.getInt("id");


        tvName.setText(product.name);
        tvDescription.setText(product.description);


        imageLoadTask = new FetchImageTask(ivImage,null);
        imageLoadTask.execute(product.imageUrl);
    }

}
