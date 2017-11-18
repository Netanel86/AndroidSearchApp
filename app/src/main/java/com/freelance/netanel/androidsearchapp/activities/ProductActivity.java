package com.freelance.netanel.androidsearchapp.activities;

import android.content.Context;
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
import com.freelance.netanel.androidsearchapp.services.IImageLoader;
import com.freelance.netanel.androidsearchapp.services.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_PRODUCT = "product_key";
    private static final int RES_PLACEHOLDER = R.drawable.ic_buybuy_logo;

    /**
     * Prepares an instance of {@link Intent} to start new {@link ProductActivity}
     *
     * @param context the Context of the parent activity.
     * @param product an instance of Product to display.
     * @return an instance of Intent ready for starting a new ProductActivity,
     * including the {@link Product} to pass to the new activity.
     */
    public static Intent prepareIntent(Context context, Product product) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

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

    private Product mProduct;

    private IImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        mProduct = getIntent().getExtras().getParcelable(EXTRA_PRODUCT);

        mImageLoader = new ImageLoader();
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnBuy.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        bindProduct();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_product_btn_buy:
                Intent intent = ProductWebViewActivity.prepareIntent(this, mProduct);
                startActivity(intent);
                break;

            case R.id.activity_product_btn_back:
                // TODO: 05/11/2017 send back notification if buy button was pressed
                ProductActivity.this.finish();
                break;
        }
    }

    private void bindProduct() {
        tvName.setText(mProduct.getName());
        tvDescription.setText(mProduct.getDescription());

        mImageLoader.load(mProduct.getImageUrl(), this, ivImage, RES_PLACEHOLDER);
    }
}
