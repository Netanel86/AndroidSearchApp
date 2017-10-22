package com.freelance.netanel.androidsearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelance.netanel.androidsearchapp.model.Product;
import com.freelance.netanel.androidsearchapp.services.GetImageAsyncTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity {

    @BindView(R.id.activity_product_tv_description)
    TextView tvDescription;

    @BindView(R.id.activity_product_iv_product)
    ImageView ivImage;

    @BindView(R.id.activity_product_btn_buy)
    Button btnBuy;

    Product product;

    GetImageAsyncTask imageLoadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("product_bundle");

        pullAndSetProduct(bundle);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProductWebView = new Intent(getApplicationContext(),ProductWebViewActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("product_url",String.format("https://www.shopyourway.com/xxx/%s",product.id));
                intentProductWebView.putExtra("webview_bundle",bundle);
                startActivity(intentProductWebView);
            }
        });

    }

    private void pullAndSetProduct(Bundle productBundle)
    {
        product = new Product();

        product.imageUrl = productBundle.getString("image");
        product.description = productBundle.getString("description");
        product.id = productBundle.getInt("id");


        tvDescription.setText(product.description);

        imageLoadTask = new GetImageAsyncTask(ivImage,null);
        imageLoadTask.execute(product.imageUrl);
    }

}
