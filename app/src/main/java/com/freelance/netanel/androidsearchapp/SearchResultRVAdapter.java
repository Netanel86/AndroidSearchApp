package com.freelance.netanel.androidsearchapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Netanel on 22/09/2017.
 */

public class SearchResultRVAdapter extends RecyclerView.Adapter<SearchResultRVAdapter.ViewHolder>
{
    private List<String> results;

    public void setResults(List<String> results)
    {
        this.results = results;
    }

    public SearchResultRVAdapter(List<String> results)
    {
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item,parent,false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(results.get(position),position);
    }

    @Override
    public int getItemCount() {
        return results == null ? null : results.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_result)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(String result, int position)
        {
            textView.setText(result + " " + position);
        }
    }
}
