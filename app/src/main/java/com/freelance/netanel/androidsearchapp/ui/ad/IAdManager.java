package com.freelance.netanel.androidsearchapp.ui.ad;

import android.content.Context;
import android.view.View;

/**
 * Created by Netanel on 11/11/2017.
 */

public interface IAdManager {
    View createBanner(Context context);
    void loadAd(View adView);
}