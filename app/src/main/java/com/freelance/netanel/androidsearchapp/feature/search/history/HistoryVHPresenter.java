package com.freelance.netanel.androidsearchapp.feature.search.history;

import com.freelance.netanel.androidsearchapp.infra.MvpPresenter;

/**
 * Created by Netanel on 06/02/2018.
 */

public class HistoryVHPresenter extends MvpPresenter<HistoryVHContract.IView>
        implements HistoryVHContract.IPresenter {

    private String query;

    private IHistoryPresenterCallback callback;

    @Override
    public void bindView(HistoryVHContract.IView view) {
        super.bindView(view);
        getView().bindQuery(query);
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public void onInsertButtonClicked() {
        if (callback != null) {
            callback.onInsertClicked(query);
        }
    }

    @Override
    public void onItemClicked() {
        if(callback != null) {
            callback.onQueryClicked(query);
        }
    }

    public void setCallback(IHistoryPresenterCallback callback) {
        this.callback = callback;
    }

    public interface IHistoryPresenterCallback {
        void onQueryClicked(String query);
        void onInsertClicked(String query);
    }
}
