package com.freelance.netanel.androidsearchapp.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by Netanel on 07/11/2017.
 */
public class GsonClassTypeAdapter<T> implements JsonDeserializer<T> {
    private Type mClass;
    private String mKey;

    public GsonClassTypeAdapter(Type targetClass) {

        if (targetClass == null) {
            throw new NullPointerException(
                    GsonClassTypeAdapter.class.getSimpleName() + ": 'targetClass' cant be null");
        }
        mClass = targetClass;
    }

    public  GsonClassTypeAdapter(Type targetClass, String key){
        this(targetClass);
        mKey = key;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonElement element;

        if(Collection.class.isAssignableFrom((Class<?>) ((ParameterizedType)mClass).getRawType())) {
            //if the requested class is a collection
            if(json.isJsonObject()) {
                //case the collection is nested in a json object
                if(mKey == null) {
                    throw new NullPointerException(GsonClassTypeAdapter.class.getSimpleName()
                            + ": 'key' cant be null, a nested collection must be specified with a member name");
                }
                element = json.getAsJsonObject().getAsJsonArray(mKey);
            }else {
                //case the json object is a json array (collection)
                element = json.getAsJsonArray();
            }
        }else {
            //if the requested class is not a collection
            if (mKey != null) {
                //if a key was specified, get the requested member
                element = json.getAsJsonObject().getAsJsonObject(mKey);
            }else {
                //if no key was specified, return the entire object
                element = json.getAsJsonObject();
            }
        }

        return new Gson().fromJson(element,mClass);
    }
}
