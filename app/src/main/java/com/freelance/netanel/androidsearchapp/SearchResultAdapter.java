package com.freelance.netanel.androidsearchapp;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelance.netanel.androidsearchapp.model.Product;
import com.freelance.netanel.androidsearchapp.services.GetImageAsyncTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Netanel on 22/09/2017.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    public static final int LIST = 0;
    public static final int GRID = 1;

    private int currentLayout = LIST;
    private List<Product> results;
    private LruCache<String, Bitmap> imageCache = new LruCache<String, Bitmap>(14440000 * 15) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };

    public SearchResultAdapter(List<Product> results) {
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = 0;
        switch (viewType) {
            case LIST:
                layout = R.layout.rv_item_product_list;
                break;
            case GRID:
                layout = R.layout.rv_item_product_grid;
        }
        View root = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(results.get(position), position);
    }

    @Override
    public int getItemCount() {
        return results == null ? 0 : results.size();
    }

    @Override
    public int getItemViewType(int position) {
        return currentLayout;
    }

    public void setLayout(int layout) {
        this.currentLayout = layout;
        notifyDataSetChanged();
    }

    public void setResults(List<Product> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_item_product_iv_image)
        public ImageView imageView;

        @BindView(R.id.rv_item_product_tv_name)
        public TextView textViewName;

        @BindView(R.id.rv_item_product_tv_description)
        public TextView textViewDescription;

        private GetImageAsyncTask imageLoadTask;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setImage(String imageURL) {
            Bitmap bmp = imageCache.get(imageURL);
            if (bmp != null) {
                imageView.setImageBitmap(bmp);
            } else {
                if (imageLoadTask != null) {
                    imageLoadTask.cancel(true);
                }
                imageLoadTask = new GetImageAsyncTask(imageView, imageCache);
                imageLoadTask.execute(imageURL);
            }
        }

        public void bind(Product result, int position) {
            textViewName.setText(result.name);
            textViewDescription.setText(result.description);
            setImage(result.imageUrl);
        }
    }
}