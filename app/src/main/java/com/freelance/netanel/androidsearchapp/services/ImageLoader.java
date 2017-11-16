package com.freelance.netanel.androidsearchapp.services;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Created by Netanel on 12/11/2017.
 */

public class ImageLoader implements IImageLoader {
    public void load(String url, Context context, ImageView intoView, int placeHolderResId) {
        RequestCreator request = Picasso.with(context).load(Uri.parse(url));
        if(placeHolderResId != 0) {
            request.placeholder(placeHolderResId);
        }
        request.into(intoView);
    }
}
