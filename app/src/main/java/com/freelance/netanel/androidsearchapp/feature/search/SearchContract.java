package com.freelance.netanel.androidsearchapp.feature.search;

import android.content.Intent;

import com.freelance.netanel.androidsearchapp.feature.search.history.HistoryAdapterContract;
import com.freelance.netanel.androidsearchapp.feature.search.results.ResultAdapterContract;
import com.freelance.netanel.androidsearchapp.infra.IMvpPresenter;

/**
 * Created by Netanel on 03/02/2018.
 */

public interface SearchContract {
    interface IView {
        void showViewChild(int childId);
        void showLongToast(String message);
        void showMessageFailed();
        void showProgress();
        void hideProgress();
        void clearQueryFocus();
        void setLayoutList();
        void setLayoutGrid();
        void setSearchQuery(String query, boolean sumbit);
        void setEnabled(boolean enabled);
        void runOnUiThread(Runnable runnable);
    }
    interface IPresenter extends IMvpPresenter<SearchContract.IView> {
        void onStart();
        void onButtonListClicked();
        void onButtonGridClicked();
        void onExpandSearchClicked();
        void onCollapseSearchClicked();
        void onQuerySubmit(String query);
        void onQueryTextChanged(String query);
        void onQueryFocusChanged(boolean hasFocus);
        ResultAdapterContract.IPresenter getResultsPresenter();
        HistoryAdapterContract.IPresenter getHistoryPresenter();
    }
}
