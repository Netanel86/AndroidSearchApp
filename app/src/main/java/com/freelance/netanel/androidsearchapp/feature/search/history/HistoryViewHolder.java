package com.freelance.netanel.androidsearchapp.feature.search.history;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.infra.MvpViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Netanel on 06/02/2018.
 */

public class HistoryViewHolder extends MvpViewHolder<HistoryVHContract.IPresenter>
        implements HistoryVHContract.IView, View.OnClickListener {

    @BindView(R.id.rv_item_history_tv)
    public TextView textViewHistory;

    @BindView(R.id.rv_item_history_btn_insert)
    public ImageButton buttonInsertText;

    public HistoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);
        buttonInsertText.setOnClickListener(this);
    }

    @Override
    public void bindQuery(String text) {
        textViewHistory.setText(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rv_item_history_btn_insert:
                presenter.onInsertButtonClicked();
                break;
            default:
                presenter.onItemClicked();
        }
    }
}

