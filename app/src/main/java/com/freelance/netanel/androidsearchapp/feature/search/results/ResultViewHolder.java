package com.freelance.netanel.androidsearchapp.feature.search.results;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelance.netanel.androidsearchapp.App;
import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.infra.MvpViewHolder;
import com.freelance.netanel.androidsearchapp.service.image_loader.IImageLoader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Netanel on 31/01/2018.
 */

public class ResultViewHolder extends MvpViewHolder<ResultVHContract.IPresenter>
        implements ResultVHContract.IView {
    private static final int PLACE_HOLDER_RES  = R.drawable.ic_buybuy_logo;

    @BindView(R.id.rv_item_product_iv_image)
    public ImageView imageView;

    @BindView(R.id.rv_item_product_tv_name)
    public TextView textViewName;

    @BindView(R.id.rv_item_product_tv_description)
    public TextView textViewDescription;

    @Inject
    public IImageLoader imageLoader;

    public ResultViewHolder(View itemView) {
        super(itemView);
        App.getInstance().getInjector().inject(this);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onProductClicked();
            }
        });
    }

    @Override
    public void bindName(String name) {
        textViewName.setText(name);
    }

    @Override
    public void bindImageUrl(String url) {
        imageLoader.load(url, itemView.getContext(),
                imageView, PLACE_HOLDER_RES);
    }

    @Override
    public void bindDescription(String description) {
        textViewDescription.setText(description);
    }
}
