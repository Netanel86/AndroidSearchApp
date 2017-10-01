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

public class ListItemParser implements IListItemParser
{
    @Override
    public List parse(Context context)
    {

        JSONObject object = new JSONReader().read(context.getResources().openRawResource(R.raw.search));


        List<ResultItem> items = new ArrayList<>();
        try
        {
            JSONArray array = object.getJSONArray("products");
            for(int i =0; i < array.length();i++)
            {
                JSONObject current = array.getJSONObject(i);
                ResultItem item = new ResultItem();

                item.Name = current.getString("name");
                item.ImageUrl = current.getString("imageUrl");

                items.add(item);
            }
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
        return  items;
    }
}
