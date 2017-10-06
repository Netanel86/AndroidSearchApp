package com.freelance.netanel.androidsearchapp;

import android.content.Context;

import com.freelance.netanel.androidsearchapp.model.Product;

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
        List<Product> items = new ArrayList<>();
        try
        {
            JSONArray array = object.getJSONArray("products");
            for(int i =0; i < array.length();i++)
            {
                JSONObject current = array.getJSONObject(i);
                Product product = new Product();

                product.id = current.getInt("id");
                product.name = current.getString("name");
                product.imageUrl = current.getString("imageUrl");
                product.description = current.getString("description");

                items.add(product);
            }
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
        return  items;
    }
}
