package com.freelance.netanel.androidsearchapp.feature.search;

import android.content.Intent;

import com.freelance.netanel.androidsearchapp.feature.history.ISearchHistoryApi;
import com.freelance.netanel.androidsearchapp.infra.IMvpPresenter;

/**
 * Created by Netanel on 03/02/2018.
 */

public interface SearchContract {
    interface IView {
        void showViewChild(int childId);
        void showLongToast(String message);
        void showMessageFailed();
        void showProductView(Intent intent);
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
        void onButtonListClicked();
        void onButtonGridClicked();
        void onButtonSearchClicked();
        void onHideSearchHistory();
        void onSubmitSearch(String query);
        void onQueryTextChanged(String query);
        void onQueryFocusChanged(boolean hasFocus);
        int getCurrentChild();
        ResultAdapterContract.IPresenter getResultsPresenter();
        ISearchHistoryApi getHistoryApi();
    }
}
