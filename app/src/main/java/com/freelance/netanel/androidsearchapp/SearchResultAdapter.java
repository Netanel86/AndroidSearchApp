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
import com.freelance.netanel.androidsearchapp.services.ImageLoadTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Netanel on 22/09/2017.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder>
{
    private List<Product> results;
    private LruCache<String,Bitmap> imageCache = new LruCache<String, Bitmap>(14440000 * 15)
    {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };

    public void setResults(List<Product> results)
    {
        this.results = results;
    }

    public SearchResultAdapter(List<Product> results)
    {
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_result_item,parent,false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(results.get(position),position);
    }

    @Override
    public int getItemCount() {
        return results == null ? null : results.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.iv_image)
        ImageView imageView;

        @BindView(R.id.tv_name)
        TextView textViewName;

        @BindView(R.id.tv_description)
        TextView textViewDescription;

        ImageLoadTask imageLoadTask;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        private void setImage(String imageURL)
        {
            Bitmap bmp = imageCache.get(imageURL);
            if(bmp != null)
            {
                imageView.setImageBitmap(bmp);
            }
            else
            {
                if(imageLoadTask != null)
                {
                    imageLoadTask.cancel(true);
                }
                imageLoadTask = new ImageLoadTask(imageView,imageCache);
                imageLoadTask.execute(imageURL);
            }
        }

        public void bind(Product result, int position)
        {
            textViewName.setText(result.name);
            textViewDescription.setText(result.description);
            setImage(result.imageUrl);
        }
    }
}
