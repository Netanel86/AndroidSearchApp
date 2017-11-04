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
import com.freelance.netanel.androidsearchapp.services.FetchImageTask;

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

    private List<Product> results;

    private LruCache<String, Bitmap> imageCache = new LruCache<String, Bitmap>(14440000 * 15) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };

    private IListAdapterCallback<Product> callback;

    public ResultAdapter(List<Product> results) {
        this.results = results;
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

    public int getCurrentLayout()
    {
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

    public void setCallback(IListAdapterCallback<Product> callback) {
        ResultAdapter.this.callback = callback;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * position of current item in the collection
         */
        private int position;

        @BindView(R.id.rv_item_product_iv_image)
        public ImageView imageView;

        @BindView(R.id.rv_item_product_tv_name)
        public TextView textViewName;

        @BindView(R.id.rv_item_product_tv_description)
        public TextView textViewDescription;

        private FetchImageTask imageLoadTask;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callback != null ){
                        callback.onItemClick(results.get(position));
                    }
                }
            });
        }

        private void setImage(String imageURL) {
            Bitmap bmp = imageCache.get(imageURL);
            if (bmp != null) {
                imageView.setImageBitmap(bmp);
            } else {
                if (imageLoadTask != null) {
                    imageLoadTask.cancel(true);
                }
                imageLoadTask = new FetchImageTask(imageView, imageCache);
                imageLoadTask.execute(imageURL);
            }
        }

        public void bind(Product result, int position) {
            ViewHolder.this.position = position;
            textViewName.setText(result.name);
            textViewDescription.setText(result.description);
            setImage(result.imageUrl);
        }
    }

    // TODO: 04/11/2017 add viewholder for empty list
}
