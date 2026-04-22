package com.freelance.netanel.androidsearchapp.feature.search.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.freelance.netanel.androidsearchapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Netanel on 02/11/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements HistoryAdapterContract.IView {

    private HistoryAdapterContract.IPresenter presenter;

    public HistoryAdapter(HistoryAdapterContract.IPresenter presenter) {
        super();
        this.presenter = presenter;
        this.presenter.bindView(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;

        switch (viewType) {
            case HistoryAdapterPresenter.VIEWTYPE_TITLE:
                holder = new ViewHolderEmpty(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rv_item_history_title, parent, false));
                break;
            case HistoryAdapterPresenter.VIEWTYPE_ITEM:
                holder = new HistoryViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rv_item_history, parent, false));
                break;
            case HistoryAdapterPresenter.VIEWTYPE_CLEAR:
                holder = new ViewHolderClear((LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_history_clear,parent,false)));
                break;
            default:
                holder = new ViewHolderEmpty(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rv_item_history_empty, parent, false));
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HistoryViewHolder){
            ((HistoryViewHolder)holder).bindPresenter(presenter.getItemPresenter(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return presenter.isEmpty() ? 1 : presenter.getItemCount() + 2;
    }

    class ViewHolderEmpty extends RecyclerView.ViewHolder {
        public ViewHolderEmpty(View itemView) {
            super(itemView);
        }
    }

    class ViewHolderClear extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_item_history_btn_clear)
        public Button buttonClear;

        public ViewHolderClear(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            buttonClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onItemClearClicked();
                }
            });
        }
    }
}
