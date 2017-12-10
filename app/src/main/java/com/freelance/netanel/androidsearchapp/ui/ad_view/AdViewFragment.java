package com.freelance.netanel.androidsearchapp.ui.ad_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelance.netanel.androidsearchapp.R;

/**
 * Created by Netanel on 05/11/2017.
 */

public class AdViewFragment extends Fragment {

    private IAdManager adManager;
    private View adView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup group = (ViewGroup) inflater.inflate(R.layout.fragment_ad, container,false );

        adManager = new AdManager();
        adView = adManager.createBanner(getContext());
        group.addView(adView);

        return group;
    }

    @Override
    public void onStart() {
        super.onStart();
        adManager.loadAd(adView);
    }
}
