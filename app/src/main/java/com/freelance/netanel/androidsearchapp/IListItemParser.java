package com.freelance.netanel.androidsearchapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Netanel on 24/09/2017.
 */

public interface IListItemParser
{
    List parse(Context context);
}

