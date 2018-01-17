package com.freelance.netanel.androidsearchapp.feature.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.freelance.netanel.androidsearchapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private List<String> items;
    private IHistoryAdapterCallBack callBack;

    public HistoryAdapter() {
        super();
        this.items = new ArrayList<>();
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
                        callBack);
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
            ((ViewHolderItem)holder).bind(items.get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {

        int viewType = 0;
        if(items.isEmpty()) {
            viewType = VIEWTYPE_EMPTY;
        }else if(position == 0) {
            viewType = VIEWTYPE_TITLE;
        } else if(position == items.size() + 1) {
            viewType = VIEWTYPE_CLEAR;
        } else {
            viewType = VIEWTYPE_ITEM;
        }

        return viewType;
    }

    @Override
    public int getItemCount() {
        return items.isEmpty() ? 1 : items.size() + 2;
    }

    public void setItems(List<String> items) {
        this.items.clear();
        if(items != null) {
            HistoryAdapter.this.items.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void setCallBack(IHistoryAdapterCallBack callback)
    {
        this.callBack = callback;
    }

    public void setItemsFilteredByName(List<String> items, String filter) {
        List<String> filtered = new ArrayList<>();
        for (String word: items){
            if(word.startsWith(filter)) {
                filtered.add(word);
            }
        }

        Collections.sort(filtered);
        setItems(filtered);
    }

    public interface IHistoryAdapterCallBack{
        void onItemClick(String item, boolean isSubmit);
        void onItemClearClick();
    }

    class ViewHolderItem extends RecyclerView.ViewHolder{

        @BindView(R.id.rv_item_history_tv)
        public TextView textViewHistory;

        @BindView(R.id.rv_item_history_btn_insert)
        public ImageButton buttonInsertText;

        private static final boolean SUMBIT = true;

        private ViewHolderItem(View itemView, final IHistoryAdapterCallBack callback) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callback != null) {
                        callback.onItemClick(textViewHistory.getText().toString(), SUMBIT);
                    }
                }
            });

            buttonInsertText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onItemClick(textViewHistory.getText().toString(),!SUMBIT);
                    }
                }
            });
        }

        private void bind(String text)
        {
            textViewHistory.setText(text);
        }
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
                    HistoryAdapter.this.setItems(null);
                    if(callBack != null) {
                        callBack.onItemClearClick();
                    }
                }
            });
        }
    }
}