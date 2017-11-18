package com.freelance.netanel.androidsearchapp.adapters;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private static final int VIEWTYPE_CLEAR = 4;

    private ArrayList<String> mItems;
    private IHistoryAdapterCallBack mCallBack;

    public interface IHistoryAdapterCallBack{
        void onItemClick(String item, boolean isSubmit);
        void onItemClearClick();
    }

    public HistoryAdapter() {
        super();
        this.mItems = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;

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
            case VIEWTYPE_CLEAR:
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
        } else if(position == mItems.size() + 1) {
            viewType = VIEWTYPE_CLEAR;
        } else {
            viewType = VIEWTYPE_ITEM;
        }

        return viewType;
    }

    @Override
    public int getItemCount() {
        return mItems.isEmpty() ? 1 : mItems.size() + 2;
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

        @BindView(R.id.rv_item_history_tv)
        public TextView mTextViewHistory;

        @BindView(R.id.rv_item_history_btn_insert)
        public FloatingActionButton mBtnInsertText;

        private final boolean vSumbit = true;

        private ViewHolderItem(View itemView, final IHistoryAdapterCallBack callback) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            mTextViewHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callback != null) {
                        callback.onItemClick(mTextViewHistory.getText().toString(),vSumbit);
                    }
                }
            });

            mBtnInsertText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onItemClick(mTextViewHistory.getText().toString(),!vSumbit);
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

    class ViewHolderClear extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_item_history_btn_clear)
        public Button mBtnClear;

        public ViewHolderClear(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mBtnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HistoryAdapter.this.setItems(null);
                    if(mCallBack != null) {
                        mCallBack.onItemClearClick();
                    }
                }
            });
        }
    }
}
