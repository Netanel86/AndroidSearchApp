package com.freelance.netanel.androidsearchapp.adapters;

/**
 * Created by Netanel on 03/11/2017.
 */
// TODO: 06/11/2017 use speciefic callback interface in each adapter
public interface IListAdapterCallback<T> {
    void onItemClick(T item);
}
