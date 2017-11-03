package com.freelance.netanel.androidsearchapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freelance.netanel.androidsearchapp.R;

import java.util.ArrayList;
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

    private ArrayList<String> items;
    private IListAdapterCallback<String> callback;

    public HistoryAdapter() {
        super();
        this.items = new ArrayList<>();
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
                        callback);
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
        }else {
            viewType = VIEWTYPE_ITEM;
        }

        return viewType;
    }

    @Override
    public int getItemCount() {
        return items.isEmpty() ? 1 : items.size() + 1;
    }

    public void setItems(Set<String> items) {
        this.items.clear();
        if(items != null) {
            this.items.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void setCallBack(IListAdapterCallback<String> callback)
    {
        this.callback = callback;
    }

    class ViewHolderItem extends RecyclerView.ViewHolder{

        @BindView(R.id.rv_item_history_tv)
        TextView textViewHistory;

        public ViewHolderItem(View itemView, final IListAdapterCallback<String> callback) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callback != null)
                    {
                        callback.onItemClick(textViewHistory.getText().toString());
                    }
                }
            });
        }

        public void bind(String text)
        {
            textViewHistory.setText(text);
        }
    }

    class ViewHolderEmpty extends RecyclerView.ViewHolder {
        public ViewHolderEmpty(View itemView) {
            super(itemView);
        }
    }
}
