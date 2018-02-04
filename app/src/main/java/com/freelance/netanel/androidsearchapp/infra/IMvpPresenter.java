package com.freelance.netanel.androidsearchapp.infra;

import android.support.annotation.NonNull;

/**
 * Created by Netanel on 02/02/2018.
 */

public interface IMvpPresenter<V> {
    void bindView(@NonNull V view);
    V getView();
}
