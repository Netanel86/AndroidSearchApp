package com.freelance.netanel.androidsearchapp.ui.product;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Netanel on 12/11/2017.
 */

public interface IImageLoader  {
    void load(String url, Context context, ImageView intoView, int placeHolderResId);
}
