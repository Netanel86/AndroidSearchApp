package com.freelance.netanel.androidsearchapp.services;

import android.app.Application;
import android.content.Intent;

import com.freelance.netanel.androidsearchapp.activities.ProductActivity;
import com.freelance.netanel.androidsearchapp.activities.SearchActivity;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Netanel on 19/11/2017.
 * tomer@sears.co.il
 */

public class PageRouter {
    private HashMap<String,Type> mRoute;

    public PageRouter() {
        mRoute = new HashMap<>();
    }

//    public Intent navigate(String route) {
//        ProductActivity.prepareIntent(Application.)
//    }
}
