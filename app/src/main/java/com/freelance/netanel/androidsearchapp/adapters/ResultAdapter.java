package com.freelance.netanel.androidsearchapp.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.model.Product;
import com.freelance.netanel.androidsearchapp.services.BitmapLoader;

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

    private LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(14440000 * 15) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };

    private List<Product> mResults;
    private IResultAdapterCallBack mCallBack;

    public interface IResultAdapterCallBack{
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

    public int getCurrentLayout()
    {
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
        /**
         * mPosition of current item in the collection
         */
        private int mPosition;
        private BitmapLoader mImageLoader;
        private Product mProduct;

        @BindView(R.id.rv_item_product_iv_image)
        private ImageView mImageView;

        @BindView(R.id.rv_item_product_tv_name)
        private TextView mTextViewName;

        @BindView(R.id.rv_item_product_tv_description)
        private TextView mTextViewDescription;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCallBack != null ){
                        mCallBack.onItemClick(mResults.get(mPosition));
                    }
                }
            });

            mImageLoader = new BitmapLoader();
            mImageLoader.setImageFetchCallback(new BitmapLoader.IBitmapFetcherCallBack() {
                @Override
                public void onBitmapFetch(Bitmap bmp) {
                    if(bmp != null)
                    {
                        mImageView.setImageBitmap(bmp);
                        if(mImageCache != null) {
                            mImageCache.put(mProduct.getImageUrl(), bmp);
                        }
                        mImageView.setBackgroundColor(mImageView.getResources().getColor(R.color.colorIcons));
                    }
                }
            });
        }

        private void bind(Product result, int position) {
            ViewHolder.this.mPosition = position;
            ViewHolder.this.mProduct = result;

            mTextViewName.setText(result.getName());
            mTextViewDescription.setText(result.getDescription());

            Bitmap bmp = mImageCache.get(result.getImageUrl());
            if (bmp != null) {
                mImageView.setImageBitmap(bmp);
            } else {
                mImageView.setImageResource(R.drawable.ic_buybuy_logo);
                mImageLoader.loadBitmapFromURL(result.getImageUrl(), mImageView.getMaxHeight());
            }
        }
    }
    // TODO: 04/11/2017 add viewholder for empty list
}
