package com.freelance.netanel.androidsearchapp.infra;

import java.util.List;

/**
 * Created by Netanel on 06/02/2018.
 */

public interface IMvpCollectionPresenter<V, M, P extends IMvpPresenter> extends IMvpPresenter<V> {
    int getItemCount();

    boolean isListEmpty();

    M getItem(int position);

    List<M> getItems();

    P getItemPresenter(int position);

    int getItemViewType(int position);
}
