package com.freelance.netanel.androidsearchapp;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inneractive.api.ads.sdk.InneractiveAdManager;
import com.inneractive.api.ads.sdk.InneractiveAdView;

/**
 * Created by Netanel on 05/11/2017.
 */

public class AdViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup group = (ViewGroup) inflater.inflate(R.layout.fragment_ad, container,false );
        InneractiveAdManager.initialize(getContext());
        InneractiveAdView banner = new InneractiveAdView(getContext(),"Nirit_MobileSchool_Android", InneractiveAdView.AdType.Banner);
        group.addView(banner);
        banner.loadAd();

        return group;
    }
}
