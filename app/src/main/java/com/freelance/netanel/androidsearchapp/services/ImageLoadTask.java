package com.freelance.netanel.androidsearchapp.services;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.freelance.netanel.androidsearchapp.R;

/**
 * Created by Netanel on 05/10/2017.
 * An Async Task for loading images in a different thread
 */

public class ImageLoadTask extends AsyncTask<String,Void,Bitmap> {
    private LruCache<String,Bitmap> imageCache;
    private ImageView imageView;
    private int maxImageSize;
    private String imageUrl;

    public ImageLoadTask(ImageView imageView, LruCache<String,Bitmap> imageCache)
    {
        this.imageView = imageView;
        this.imageCache = imageCache;
        this.maxImageSize = imageView.getMaxHeight();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        this.imageUrl = params[0];
        Bitmap bmp = BitmapLoader.loadBitmapFromURL(imageUrl,maxImageSize);
        return bmp;
    }

    @Override
    protected void onPreExecute() {
        imageView.setImageResource(R.color.DarkBlue);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap != null)
        {
            imageView.setImageBitmap(bitmap);
            imageCache.put(imageUrl,bitmap);
        }
    }
}
