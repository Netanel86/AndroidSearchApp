package com.freelance.netanel.androidsearchapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelance.netanel.androidsearchapp.services.AdManager;
import com.freelance.netanel.androidsearchapp.services.IAdManager;

/**
 * Created by Netanel on 05/11/2017.
 */

public class AdViewFragment extends Fragment {

    private IAdManager mAdManager;
    private View mAdView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup group = (ViewGroup) inflater.inflate(R.layout.fragment_ad, container,false );

        mAdManager = new AdManager();
        mAdView = mAdManager.createBanner(getContext());
        group.addView(mAdView);

        return group;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdManager.loadAd(mAdView);
    }
}
