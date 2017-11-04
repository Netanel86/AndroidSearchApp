package com.freelance.netanel.androidsearchapp.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * Created by Netanel on 06/10/2017.
 * A Bitmap Helper class which handles loading of images
 */
public class BitmapLoader {
    public static Bitmap loadBitmapFromURL(String urlString, int maxSize) {
        Bitmap bmp = null;

        try {
            URL imageURL = new URL(urlString);
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

    /**
     * get the bounds and details of a bitmap resource without loading it.
     * @param input a Bitmap {@link InputStream}
     * @return an {@link android.graphics.BitmapFactory.Options} object containing image details
     */
    private static BitmapFactory.Options getBitmapBounds(InputStream input) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input,null,options);
        return options;
    }

    private static Bitmap loadScaledBitmap(InputStream input, BitmapFactory.Options optionsSource, int maxSize) {
        BitmapFactory.Options optionsScaled = new BitmapFactory.Options();
        optionsScaled.inSampleSize = calculateInSampleSize(optionsSource,maxSize);
        return BitmapFactory.decodeStream(input,null,optionsScaled);
    }

    private static Bitmap loadBitmap(InputStream input){
        return BitmapFactory.decodeStream(input);
    }
    private static int calculateInSampleSize(BitmapFactory.Options options, int maxSize) {
        int scale = 1;
        if(options.outHeight > maxSize || options.outWidth > maxSize)
        {
            scale = (int) Math.pow(2, (int) Math.ceil(Math.log(maxSize / (double) Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));
        }

        return  scale;
    }

    // TODO: 04/11/2017 refactor fetch image task to be a subclass of bitmap loader
}
