package com.freelance.netanel.androidsearchapp.infra;

/**
 * Created by Netanel on 01/02/2018.
 */

public abstract class MvpPresenter<V> implements IMvpPresenter<V> {
    private V view;
    public void bindView(V view) {
        this.view = view;
    }
    public V getView() {
        return this.view;
    }
}