package com.freelance.netanel.androidsearchapp.infra;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>Abstract implementation for an MVP presenter that handles a collection of items, where each item
 * represents a child presenter. Each of the child presenters is bound to a view, and supplies an
 * interface for presenting a data model class. The class supplies basic methods for managing the
 * collection</p>
 *
 * @param <V> The main view or framework class type to be bounded to this presenter.
 * @param <M> The data model class type presented by this presenter.
 * @param <P> The presenter class type of the child presenters.
 *
 * @see IMvpCollectionPresenter
 * @see IMvpPresenter
 * @see MvpPresenter
 *
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
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

    public M getItem(int position) {
        return items.get(position);
    }

    public List<M> getItems() {
        return items;
    }

    public int getItemCount() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.size() == 0;
    }

    public P getItemPresenter(int position) {
        return presenters.get(getPresenterKey(position));
    }

    public abstract int getItemViewType(int position);

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
}
