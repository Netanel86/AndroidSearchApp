package com.freelance.netanel.androidsearchapp.feature.search;

import android.content.Context;
import android.content.Intent;

import com.freelance.netanel.androidsearchapp.feature.history.HistoryAdapter;
import com.freelance.netanel.androidsearchapp.feature.history.ISearchHistoryApi;
import com.freelance.netanel.androidsearchapp.feature.history.SearchHistoryApi;
import com.freelance.netanel.androidsearchapp.feature.product.ProductActivity;
import com.freelance.netanel.androidsearchapp.infra.MvpPresenter;
import com.freelance.netanel.androidsearchapp.model.Product;

import java.io.IOException;
import java.util.List;

/**
 * Created by Netanel on 04/02/2018.
 */

public class SearchPresenter extends MvpPresenter<SearchContract.IView> implements SearchContract.IPresenter {
    private static final int CHILD_RESULTS = 0;
    private static final int CHILD_HISTORY = 1;

    private static final boolean ENABLED = true;

    private int currentChild = CHILD_RESULTS;

    private ResultAdapterContract.IPresenter resultsPresenter;
    private IProductRepository productRepository;
    private ISearchHistoryApi searchHistoryApi;

    public SearchPresenter(Context context,
                           IProductRepository productRepository,
                           ResultAdapterContract.IPresenter resultsPresenter) {
        this.resultsPresenter = resultsPresenter;
        this.productRepository = productRepository;
        searchHistoryApi = new SearchHistoryApi();

        initialize(context);
    }

    private void initialize(final Context context) {
        resultsPresenter.setCallback(new ResultAdapterPresenter.IPresenterCallback() {
            @Override
            public void openProduct(Product product) {
                Intent intent = ProductActivity.prepareIntent(context, product);
                getView().showProductView(intent);
            }
        });

        searchHistoryApi.loadHistory();
        searchHistoryApi.getAdapter().setCallBack(new HistoryAdapter.IHistoryAdapterCallBack() {
            @Override
            public void onItemClick(String query, boolean submit) {
                getView().setSearchQuery(query, submit);
            }

            @Override
            public void onItemClearClick() {
                searchHistoryApi.clear();
            }
        });

        productRepository.setDataFetchCallback(new ProductRepository.IDataFetcherCallback() {
            @Override
            public void onDataFetch(final List<Product> items) {
                getView().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getView().hideProgress();
                        getView().setEnabled(ENABLED);
                        if (items != null) {
                            resultsPresenter.clearAndAddAll(items);
                        } else {
                            getView().showMessageFailed();
                        }
                    }
                });
            }

            @Override
            public void onDataFetchFail(final IOException exception) {
                getView().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getView().hideProgress();
                        getView().setEnabled(ENABLED);
                        getView().showLongToast(exception.getMessage());
                    }
                });
            }
        });
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
    public void onButtonSearchClicked() {
        setViewChild(CHILD_HISTORY);
    }

    @Override
    public void onHideSearchHistory() {
        setViewChild(CHILD_RESULTS);
    }

    @Override
    public void onSubmitSearch(String query) {
        setViewChild(CHILD_RESULTS);
        searchHistoryApi.addSearchQuery(query);
        productRepository.searchData(query);
        getView().showProgress();
        getView().clearQueryFocus();
        getView().setEnabled(!ENABLED);
    }

    @Override
    public void onQueryTextChanged(String query) {
        if (!query.isEmpty()) {
            searchHistoryApi.setFilter(query);
            if (currentChild != CHILD_HISTORY) {
                setViewChild(CHILD_HISTORY);
            }
        } else {
            searchHistoryApi.loadHistory();
        }
    }

    @Override
    public void onQueryFocusChanged(boolean hasFocus) {
        if(hasFocus) {
            setViewChild(CHILD_HISTORY);
        }
    }

    @Override
    public int getCurrentChild() {
        return currentChild;
    }

    @Override
    public ResultAdapterContract.IPresenter getResultsPresenter() {
        return resultsPresenter;
    }

    @Override
    public ISearchHistoryApi getHistoryApi() {
        return searchHistoryApi;
    }

    private void setViewChild(int childId) {
        if(currentChild != childId) {
            this.getView().showViewChild(childId);
            currentChild = childId;
        }
    }
}
