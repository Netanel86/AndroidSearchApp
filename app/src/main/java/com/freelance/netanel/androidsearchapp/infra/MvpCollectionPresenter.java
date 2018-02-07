package com.freelance.netanel.androidsearchapp.infra;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Netanel on 01/02/2018.
 */

public abstract class MvpCollectionPresenter
        <V, M, P extends IMvpPresenter> extends MvpPresenter<V>
        implements IMvpCollectionPresenter<V, M, P> {

    protected final HashMap<Object,P> presenters;
    private final List<M> items;

    public MvpCollectionPresenter() {
        this.presenters = new HashMap<>();
        this.items = new ArrayList<>();
    }

    public int getItemCount() {
        return items.size();
    }

    public boolean isEmpty() {
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

    public List<M> getItems() {
        return items;
    }

    public P getItemPresenter(int position) {
        return presenters.get(getPresenterKey(position));
    }

    public abstract int getItemViewType(int position);
}
