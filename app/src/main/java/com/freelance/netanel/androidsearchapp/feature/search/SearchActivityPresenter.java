package com.freelance.netanel.androidsearchapp.feature.search;

import com.freelance.netanel.androidsearchapp.feature.search.history.HistoryAdapterContract;
import com.freelance.netanel.androidsearchapp.feature.search.results.ResultAdapterContract;
import com.freelance.netanel.androidsearchapp.infra.MvpPresenter;
import com.freelance.netanel.androidsearchapp.model.Product;

import java.io.IOException;
import java.util.List;

/**
 * Created by Netanel on 04/02/2018.
 */

public class SearchActivityPresenter extends MvpPresenter<SearchActivityContract.IView> implements SearchActivityContract.IPresenter {
    private static final int CHILD_RESULTS = 0;
    private static final int CHILD_HISTORY = 1;

    private static final boolean ENABLED = true;

    private int currentChild;

    private ResultAdapterContract.IPresenter resultsPresenter;
    private HistoryAdapterContract.IPresenter historyPresenter;
    private IProductRepository productRepository;
    private ISearchActivityRouter router;

    public SearchActivityPresenter(ISearchActivityRouter router,
                                   IProductRepository productRepository,
                                   ResultAdapterContract.IPresenter resultsPresenter,
                                   HistoryAdapterContract.IPresenter historyPresenter) {
        this.resultsPresenter = resultsPresenter;
        this.historyPresenter = historyPresenter;
        this.productRepository = productRepository;
        this.router = router;

        initialize();
    }

    private void initialize() {
        resultsPresenter.setCallback(new ResultAdapterContract.IPresenter.IPresenterCallback() {
            @Override
            public void openProduct(Product product) {
                router.showProductView(product);
            }
        });

        historyPresenter.setCallBack(new HistoryAdapterContract.IPresenter.IPresenterCallBack() {
            @Override
            public void onQueryClicked(String query, boolean isSubmit) {
                getView().setSearchQuery(query, isSubmit);
            }
        });

    }

    private void setViewChild(int childId) {
        if(currentChild != childId) {
            this.getView().showViewChild(childId);
            currentChild = childId;
        }
    }

    @Override
    public void onStart() {
        router.parseIntentData();
        setViewChild(CHILD_RESULTS);
    }

    @Override
    public void onButtonListClicked() {
        this.getView().setLayoutList();
        resultsPresenter.setLayoutList();
    }

    @Override
    public void onButtonGridClicked() {
        this.getView().setLayoutGrid();
        resultsPresenter.setLayoutGrid();
    }

    @Override
    public void onExpandSearchClicked() {
        setViewChild(CHILD_HISTORY);
    }

    @Override
    public void onCollapseSearchClicked() {
        setViewChild(CHILD_RESULTS);
    }

    @Override
    public void onQuerySubmit(String query) {
        setViewChild(CHILD_RESULTS);
        historyPresenter.onQuerySubmit(query);
        productRepository.searchData(query,
                new IProductRepository.IDataFetcherCallback<List<Product>>() {
            @Override
            public void onDataFetch(final List<Product> data) {
                onFetch(data);
            }

            @Override
            public void onDataFetchFail(final IOException exception) {
                onFetchFailed(exception);
            }
        });
        getView().showProgress();
        getView().clearQueryFocus();
        getView().setEnabled(!ENABLED);
    }

    @Override
    public void onQueryTextChanged(String query) {
            historyPresenter.onQueryTextChanged(query);
    }

    @Override
    public void onQueryFocusChanged(boolean hasFocus) {
        if(hasFocus) {
            setViewChild(CHILD_HISTORY);
        }
    }

    @Override
    public ResultAdapterContract.IPresenter getResultsPresenter() {
        return resultsPresenter;
    }

    @Override
    public HistoryAdapterContract.IPresenter getHistoryPresenter() {
        return historyPresenter;
    }

    private void onFetch(final List<Product> results) {
        getView().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().hideProgress();
                getView().setEnabled(ENABLED);
                if (results != null) {
                    resultsPresenter.clearAndAddAll(results);
                } else {
                    getView().showMessageFailed();
                }
            }
        });
    }

    private void onFetchFailed(final IOException exception) {
        getView().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().hideProgress();
                getView().setEnabled(ENABLED);
                getView().showLongToast(exception.getMessage());
            }
        });
    }
}
