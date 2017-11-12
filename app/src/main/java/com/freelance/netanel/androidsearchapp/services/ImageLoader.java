package com.freelance.netanel.androidsearchapp.services;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Netanel on 12/11/2017.
 */

public class ImageLoader implements IImageLoader {
    public void load(String url, Context context, ImageView intoView, int placeHolderResId) {
        Picasso.with(context).load(Uri.parse(url)).placeholder(placeHolderResId).into(intoView);
    }
}
