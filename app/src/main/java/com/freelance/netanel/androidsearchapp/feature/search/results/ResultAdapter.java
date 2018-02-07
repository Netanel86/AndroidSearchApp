package com.freelance.netanel.androidsearchapp.feature.search.results;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelance.netanel.androidsearchapp.R;

/**
 * Created by Netanel on 22/09/2017.
 */

public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ResultAdapterContract.IView {
    private ResultAdapterContract.IPresenter presenter;

    public ResultAdapter(ResultAdapterContract.IPresenter presenter) {
        super();
        this.presenter = presenter;
        this.presenter.bindView(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case ResultAdapterPresenter.VIEWTYPE_LIST_ITEM:
                holder = new ResultViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.rv_item_product_list, parent,false));
                break;
            case ResultAdapterPresenter.VIEWTYPE_GRID_ITEM:
                holder = new ResultViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.rv_item_product_grid, parent, false));
                break;
            default:
                holder = new ViewHolderEmpty(
                        LayoutInflater.from(parent.getContext())
                                .inflate( R.layout.rv_item_product_empty, parent, false));
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ResultViewHolder) {
            ((ResultViewHolder)holder).bindPresenter(presenter.getItemPresenter(position));
        }
    }

    @Override
    public int getItemCount() {
        return presenter.isEmpty() ? 1 : presenter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return  presenter.getItemViewType(position);
    }

    class ViewHolderEmpty extends RecyclerView.ViewHolder {

        public ViewHolderEmpty(View itemView) {
            super(itemView);
        }
    }
}

