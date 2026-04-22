package com.freelance.netanel.androidsearchapp.feature.search.ad_banner;

import android.content.Context;
import android.view.View;

/**
 * Created by Netanel on 11/11/2017.
 */

public interface IAdManager {
    View createBanner(Context context);
    void loadAd(View adView);
}
