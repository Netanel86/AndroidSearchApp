package com.freelance.netanel.androidsearchapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freelance.netanel.androidsearchapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Netanel on 02/11/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEWTYPE_TITLE = 2;
    private static final int VIEWTYPE_EMPTY = 1;
    private static final int VIEWTYPE_ITEM = 3;

    private ArrayList<String> mItems;
    private IHistoryAdapterCallBack mCallBack;

    public interface IHistoryAdapterCallBack{
        void onItemClick(String item);
    }

    public HistoryAdapter() {
        super();
        this.mItems = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = 0;
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case VIEWTYPE_TITLE:
                holder = new ViewHolderEmpty(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rv_item_history_title, parent, false));
                break;
            case VIEWTYPE_ITEM:
                holder = new ViewHolderItem(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rv_item_history, parent, false),
                        mCallBack);
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
        if(holder instanceof ViewHolderItem){
            ((ViewHolderItem)holder).bind(mItems.get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {

        int viewType = 0;
        if(mItems.isEmpty()) {
            viewType = VIEWTYPE_EMPTY;
        }else if(position == 0) {
            viewType = VIEWTYPE_TITLE;
        }else {
            viewType = VIEWTYPE_ITEM;
        }

        return viewType;
    }

    @Override
    public int getItemCount() {
        return mItems.isEmpty() ? 1 : mItems.size() + 1;
    }

    public void setItems(Set<String> items) {
        this.mItems.clear();
        if(items != null) {
            HistoryAdapter.this.mItems.addAll(items);
            Collections.sort(HistoryAdapter.this.mItems);
        }
        notifyDataSetChanged();
    }

    public void setCallBack(IHistoryAdapterCallBack callback)
    {
        this.mCallBack = callback;
    }

    class ViewHolderItem extends RecyclerView.ViewHolder{

        // TODO: 16/11/2017 add button to pass history item to textbox without submit
        @BindView(R.id.rv_item_history_tv)
        public TextView mTextViewHistory;

        private ViewHolderItem(View itemView, final IHistoryAdapterCallBack callback) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callback != null)
                    {
                        callback.onItemClick(mTextViewHistory.getText().toString());
                    }
                }
            });
        }

        private void bind(String text)
        {
            mTextViewHistory.setText(text);
        }
    }

    class ViewHolderEmpty extends RecyclerView.ViewHolder {
        public ViewHolderEmpty(View itemView) {
            super(itemView);
        }
    }
}
