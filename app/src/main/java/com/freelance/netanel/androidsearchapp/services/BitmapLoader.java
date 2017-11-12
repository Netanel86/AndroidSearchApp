package com.freelance.netanel.androidsearchapp.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Netanel on 06/10/2017.
 * A Bitmap Helper class which handles loading of images
 */
public class BitmapLoader {

    // TODO: 06/11/2017 use picasso lib to load images and handle cache
    private IBitmapFetcherCallBack finishCallback;
    private FetchImageTask imageFetchTask;

    public interface IBitmapFetcherCallBack
    {
        void onBitmapFetch(Bitmap bmp);
    }

    public void setImageFetchCallback(IBitmapFetcherCallBack callback) {
        finishCallback = callback;
    }

    public void loadBitmapFromURL(String urlString, int maxSize) {
        if (imageFetchTask != null){
            imageFetchTask.cancel(true);
        }
        imageFetchTask= new FetchImageTask();
        imageFetchTask.execute(urlString,Integer.toString(maxSize));
    }

    /**
     * get the bounds and details of a bitmap resource without loading it.
     * @param input a Bitmap {@link InputStream}
     * @return an {@link android.graphics.BitmapFactory.Options} object containing image details
     */
    private BitmapFactory.Options getBitmapBounds(InputStream input) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input,null,options);
        return options;
    }

    private Bitmap loadScaledBitmap(InputStream input, BitmapFactory.Options optionsSource, int maxSize) {
        BitmapFactory.Options optionsScaled = new BitmapFactory.Options();
        optionsScaled.inSampleSize = calculateInSampleSize(optionsSource,maxSize);
        return BitmapFactory.decodeStream(input,null,optionsScaled);
    }

    private Bitmap loadBitmap(InputStream input){
        return BitmapFactory.decodeStream(input);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int maxSize) {
        int scale = 1;
        if(options.outHeight > maxSize || options.outWidth > maxSize)
        {
            scale = (int) Math.pow(2, (int) Math.ceil(Math.log(maxSize / (double) Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));
        }

        return  scale;
    }

    public class FetchImageTask extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            String imageUrl = params[0];
            int maxSize = Integer.parseInt(params[1]);
            Bitmap bmp = null;

            try {
                URL imageURL = new URL(imageUrl);
                InputStream input = imageURL.openStream();
                BitmapFactory.Options optionsSource = getBitmapBounds(input);
                input.close();

                input = imageURL.openStream();
                bmp = maxSize == Integer.MAX_VALUE ?
                        loadBitmap(input) : loadScaledBitmap(input,optionsSource,maxSize);
                input.close();
            }
            catch (IOException ex)
            {
                Log.e("ERROR: Loading Bitmap",ex.getMessage());
                ex.printStackTrace();
            }

            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (finishCallback != null)
            {
                finishCallback.onBitmapFetch(bitmap);
            }
        }
    }
}
