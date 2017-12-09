package com.freelance.netanel.androidsearchapp.ui.search;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.freelance.netanel.androidsearchapp.domain.feature.search.ProductSearchApi;
import com.freelance.netanel.androidsearchapp.ui.history.DividerItemDecoration;
import com.freelance.netanel.androidsearchapp.ui.product.ProductActivity;
import com.freelance.netanel.androidsearchapp.ui.history.ISearchHistoryApi;
import com.freelance.netanel.androidsearchapp.ui.history.SearchHistoryApi;
import com.freelance.netanel.androidsearchapp.ui.history.HistoryAdapter;
import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.domain.model.Product;

import java.io.IOException;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CHILD_RESULTS = 0;
    private static final int CHILD_HISTORY = 1;
    private ProductSearchApi mAPI;

    private ISearchHistoryApi searchHistoryApi;
    private ResultAdapter mResultadapter;

    private LinearLayoutManager mListLayoutManager;
    private GridLayoutManager mGridLayoutManager;

    private SearchView mSearchView;

    @BindView(R.id.activity_search_progress)
    public View progress;

    @BindView(R.id.activity_search_vs)
    public ViewSwitcher viewSwitcher;

    @BindView(R.id.activity_search_rv_results)
    public RecyclerView rvResults;

    @BindView(R.id.activity_search_rv_history)
    public RecyclerView rvHistory;

    @BindView(R.id.activity_search_btn_list)
    public ImageButton btnList;

    @BindView(R.id.activity_search_btn_grid)
    public ImageButton btnGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initButterknife();

        mAPI = new ProductSearchApi(this);
        searchHistoryApi = new SearchHistoryApi(this);
        buildUI();

        Uri data = this.getIntent().getData();
        if(data != null && data.isHierarchical()) {
            String uri = this.getIntent().getDataString();
            // TODO: 02/12/2017 add a router to navigate threw views
            toast("My Uri:" + uri,Toast.LENGTH_LONG);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        searchHistoryApi.loadHistory();
        searchHistoryApi.getAdapter().setCallBack(new HistoryAdapter.IHistoryAdapterCallBack() {
            @Override
            public void onItemClick(String query, boolean submit) {
                mSearchView.setQuery(query, submit);
            }

            @Override
            public void onItemClearClick() {
                searchHistoryApi.clear();
            }
        });
        mResultadapter.setCallback(new ResultAdapter.IResultAdapterCallBack() {
            @Override
            public void onItemClick(Product item) {
                openProductActivity(item);
            }
        });

        mAPI.setDataFetchCallback(new ProductSearchApi.IDataFetcherCallback() {
            @Override
            public void onDataFetch(final List<Product> items) {
                SearchActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setVisibility(View.GONE);
                        if(items != null){
                            mResultadapter.setResults(items);
                        }
                        else {
                            toast(getResources().getString(R.string.message_load_failed),Toast.LENGTH_LONG);
                        }
                    }
                });
            }

            @Override
            public void onDataFetchFail(final IOException exception) {
                SearchActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setVisibility(View.GONE);
                        toast(exception.getMessage(),Toast.LENGTH_LONG);
                    }
                });
            }
        });

        viewSwitcher.setDisplayedChild(CHILD_RESULTS);

        btnList.setOnClickListener(this);
        btnGrid.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        createSearchView(menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_search_btn_list:
                setResultsLayout(mListLayoutManager, ResultAdapter.LAYOUT_TYPE_LIST);
                break;

            case R.id.activity_search_btn_grid:
                setResultsLayout(mGridLayoutManager, ResultAdapter.LAYOUT_TYPE_GRID);
                break;
        }
    }

    private void initButterknife() {
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
    }

    private void buildUI() {
        mListLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mGridLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.grid_col_count));
        mResultadapter = new ResultAdapter();

        rvHistory.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvHistory.setAdapter(searchHistoryApi.getAdapter());

        rvResults.setLayoutManager(mListLayoutManager);
        rvResults.setAdapter(mResultadapter);

        rvHistory.addItemDecoration(new DividerItemDecoration(getDrawable(R.drawable.divider_horizontal)));
    }

    private void createSearchView(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.menu_action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        viewSwitcher.setDisplayedChild(CHILD_HISTORY);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        if (viewSwitcher.getDisplayedChild() == CHILD_HISTORY) {
                            viewSwitcher.setDisplayedChild(CHILD_RESULTS);
                        }
                        return true;
                    }
                });
        mSearchView = (SearchView) searchItem.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewSwitcher.setDisplayedChild(CHILD_RESULTS);
                searchHistoryApi.addSearchQuery(query);
                progress.setVisibility(View.VISIBLE);
                mAPI.searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    searchHistoryApi.setFilter(newText);
                    if (viewSwitcher.getDisplayedChild() != CHILD_HISTORY) {
                        viewSwitcher.setDisplayedChild(CHILD_HISTORY);
                    }
                } else {
                    searchHistoryApi.loadHistory();
                }

                return false;
            }
        });
    }

    private void openProductActivity(Product product) {
        Intent intent = ProductActivity.prepareIntent(this,product);
        startActivity(intent);
    }

    private void setResultsLayout(RecyclerView.LayoutManager targetLayoutManager, int targetViewType) {
        if (mResultadapter.getCurrentLayout() != targetViewType) {
            rvResults.setLayoutManager(targetLayoutManager);
            mResultadapter.setLayout(targetViewType);
        }
    }

    private void toast(String message, int length) {
        Toast.makeText(this, message, length).show();
    }
}