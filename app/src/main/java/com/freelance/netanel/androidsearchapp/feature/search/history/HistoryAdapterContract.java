package com.freelance.netanel.androidsearchapp.feature.search.history;

import com.freelance.netanel.androidsearchapp.infra.IMvpCollectionPresenter;
import com.freelance.netanel.androidsearchapp.infra.IMvpPresenter;

/**
 * Created by Netanel on 06/02/2018.
 */

public class HistoryAdapterContract {
    public interface IView {
        void notifyDataSetChanged();
    }
    public interface IPresenter
            extends IMvpCollectionPresenter<IView,String,HistoryVHContract.IPresenter> {
        void onItemClearClicked();

        void onQueryTextChanged(String filter);

        void onQuerySubmit(String query);

        void reloadHistory();

        void setCallBack(IPresenterCallBack callBack);

        interface IPresenterCallBack{
            void onQueryClicked(String item, boolean isSubmit);
        }
    }
}
