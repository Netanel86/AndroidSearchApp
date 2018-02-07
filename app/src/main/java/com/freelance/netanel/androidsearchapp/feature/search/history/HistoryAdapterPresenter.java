package com.freelance.netanel.androidsearchapp.feature.search.history;

import android.support.annotation.NonNull;

import com.freelance.netanel.androidsearchapp.feature.search.history.repository.IHistoryRepository;
import com.freelance.netanel.androidsearchapp.infra.MvpCollectionPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Netanel on 06/02/2018.
 */

public class HistoryAdapterPresenter
        extends MvpCollectionPresenter<HistoryAdapterContract.IView, String, HistoryVHContract.IPresenter>
        implements HistoryAdapterContract.IPresenter {

    public static final int VIEWTYPE_TITLE = 2;
    public static final int VIEWTYPE_EMPTY = 1;
    public static final int VIEWTYPE_ITEM = 3;
    public static final int VIEWTYPE_CLEAR = 4;
    private static boolean SUMBIT = true;

    public IHistoryRepository historyRepository;
    public HistoryAdapterPresenter(IHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
        this.addAll(historyRepository.getSearchHistory());
    }

    private IPresenterCallBack callBack;

    @Override
    public void setCallBack(IPresenterCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected Object getPresenterKey(int position) {
        return getItem(position);
    }

    @Override
    protected Object getPresenterKey(@NonNull String model) {
        return model;
    }

    @Override
    protected HistoryVHContract.IPresenter createPresenter(@NonNull String model) {
        HistoryVHPresenter presenter = new HistoryVHPresenter();
        presenter.setQuery(model);
        presenter.setCallback(new HistoryVHPresenter.IHistoryPresenterCallback() {
            @Override
            public void onQueryClicked(String query) {
                if(callBack != null) {
                    callBack.onQueryClicked(query, SUMBIT);
                }
            }

            @Override
            public void onInsertClicked(String query) {
                if(callBack != null) {
                    callBack.onQueryClicked(query, !SUMBIT);
                }
            }
        });
        return presenter;
    }

    @Override
    public void onItemClearClicked() {
        clearAll();
        getView().notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (isEmpty()) {
            viewType = VIEWTYPE_EMPTY;
        } else if (position == 0) {
            viewType = VIEWTYPE_TITLE;
        } else if (position == getItemCount() + 1) {
            viewType = VIEWTYPE_CLEAR;
        } else {
            viewType = VIEWTYPE_ITEM;
        }

        return viewType;
    }

    @Override
    public HistoryVHContract.IPresenter getItemPresenter(int position) {
        return presenters.get(getPresenterKey(position));
    }

    private String lastQuery;
    @Override
    public void onQueryTextChanged(String newQuery) {
        if(newQuery.isEmpty()) {
            lastQuery = null;
            reloadHistory();
        } else {
            List<String> toFilter;
            if(lastQuery != null && lastQuery.length() < newQuery.length()) {
                toFilter = getItems();
            } else {
                toFilter = historyRepository.getSearchHistory();
            }

            List<String> filtered = new ArrayList<>();
            for (String word : toFilter) {
                if (word.startsWith(newQuery)) {
                    filtered.add(word);
                }
            }

            Collections.sort(filtered);

            clearAll();
            addAll(filtered);
            getView().notifyDataSetChanged();
        }
    }

    @Override
    public void reloadHistory() {
        if(!isEmpty()) {
            clearAll();
        }
        addAll(historyRepository.getSearchHistory());
        getView().notifyDataSetChanged();
    }

    @Override
    public void onQuerySubmit(String query) {
        historyRepository.addSearchQuery(query);
        reloadHistory();
    }
}
