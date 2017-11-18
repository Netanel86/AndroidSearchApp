package com.freelance.netanel.androidsearchapp.services;

import android.content.Context;
import android.view.View;

import com.inneractive.api.ads.sdk.InneractiveAdManager;
import com.inneractive.api.ads.sdk.InneractiveAdView;

/**
 * Created by Netanel on 11/11/2017.
 */

public class AdManager implements IAdManager {
    private static final String APP_ID = "Nirit_MobileSchool_Android";

    @Override
    public View createBanner(Context context) {
        InneractiveAdManager.initialize(context);
        return new InneractiveAdView(context, APP_ID, InneractiveAdView.AdType.Banner);
    }

    public void loadAd(View adView) {
        if (adView != null) {
            if (adView instanceof InneractiveAdView) {
                ((InneractiveAdView) adView).loadAd();
            } else {
                throw new ClassCastException(
                        AdManager.class.getSimpleName() + ": 'adView' is not an instance of "
                                + InneractiveAdView.class.getSimpleName());
            }
        } else {
            throw new NullPointerException(AdManager.class.getSimpleName() + ": 'adView'");
        }
    }
}
