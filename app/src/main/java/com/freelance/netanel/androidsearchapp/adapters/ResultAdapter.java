package com.freelance.netanel.androidsearchapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.model.Product;
import com.freelance.netanel.androidsearchapp.services.IImageLoader;
import com.freelance.netanel.androidsearchapp.services.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Netanel on 22/09/2017.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    public static final int LAYOUT_TYPE_LIST = 0;
    public static final int LAYOUT_TYPE_GRID = 1;

    private int currentLayout = LAYOUT_TYPE_LIST;

    private List<Product> mResults;
    private IResultAdapterCallBack mCallBack;

    public interface IResultAdapterCallBack {
        void onItemClick(Product item);
    }

    public ResultAdapter(List<Product> results) {
        this.mResults = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = 0;
        switch (viewType) {
            case LAYOUT_TYPE_LIST:
                layout = R.layout.rv_item_product_list;
                break;
            case LAYOUT_TYPE_GRID:
                layout = R.layout.rv_item_product_grid;
        }
        View root = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mResults.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mResults == null ? 0 : mResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return currentLayout;
    }

    public int getCurrentLayout() {
        return currentLayout;
    }

    public void setLayout(int layout) {
        this.currentLayout = layout;
        notifyDataSetChanged();
    }

    public void setResults(List<Product> results) {
        this.mResults = results;
        notifyDataSetChanged();
    }

    public void setCallback(IResultAdapterCallBack callback) {
        ResultAdapter.this.mCallBack = callback;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private static final int PLACE_HOLDER_RES  = R.drawable.ic_buybuy_logo;

        /**
         * mPosition of current item in the collection
         */
        private int mPosition;
        private IImageLoader mImageLoader;

        @BindView(R.id.rv_item_product_iv_image)
        public ImageView mImageView;

        @BindView(R.id.rv_item_product_tv_name)
        public TextView mTextViewName;

        @BindView(R.id.rv_item_product_tv_description)
        public TextView mTextViewDescription;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallBack != null) {
                        mCallBack.onItemClick(mResults.get(mPosition));
                    }
                }
            });
            mImageLoader = new ImageLoader();
        }

        private void bind(Product result, int position) {
            ViewHolder.this.mPosition = position;

            mTextViewName.setText(result.getName());
            mTextViewDescription.setText(result.getDescription());

            mImageLoader.load(result.getImageUrl(), ViewHolder.this.itemView.getContext(),
                    mImageView, PLACE_HOLDER_RES);
        }
    }
    // TODO: 04/11/2017 add viewholder for empty list
}
