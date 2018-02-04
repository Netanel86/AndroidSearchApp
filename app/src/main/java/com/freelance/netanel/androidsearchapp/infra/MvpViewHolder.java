package com.freelance.netanel.androidsearchapp.infra;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Netanel on 02/02/2018.
 */

public abstract class MvpViewHolder<P extends IMvpPresenter> extends RecyclerView.ViewHolder {

    protected P presenter;

    public MvpViewHolder(View itemView) {
        super(itemView);
    }

    public void bindPresenter(P presenter) {
        this.presenter = presenter;
        this.presenter.bindView(this);
    }
}
