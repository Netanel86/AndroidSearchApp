package com.freelance.netanel.androidsearchapp.infra;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Netanel on 01/02/2018.
 */

public abstract class MvpRecyclerViewAdapterPresenter
        <V, M, P> extends MvpPresenter<V> {

    protected final HashMap<Object,P> presenters;
    private final List<M> items;

    public MvpRecyclerViewAdapterPresenter() {
        this.presenters = new HashMap<>();
        this.items = new ArrayList<>();
    }

    public P getPresenter(int position) {
        return presenters.get(getPresenterKey(position));
    }

    public int getItemCount() {
        return items.size();
    }

    public boolean isListEmpty() {
        return items.size() == 0;
    }

    protected abstract Object getPresenterKey(int position);

    protected abstract Object getPresenterKey(@NonNull M model);

    protected abstract P createPresenter(@NonNull M model);

    protected void clearAll() {
        this.items.clear();
        this.presenters.clear();
    }

    protected void addAll(List<M> models) {
        for (M model : models) {
            this.items.add(model);
            this.presenters.put(getPresenterKey(model), createPresenter(model));
        }
    }

    public M getItem(int position) {
        return items.get(position);
    }
}
