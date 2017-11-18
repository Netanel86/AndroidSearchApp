package com.freelance.netanel.androidsearchapp.activities;
import android.content.Intent;
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

import com.freelance.netanel.androidsearchapp.API;
import com.freelance.netanel.androidsearchapp.DividerItemDecoration;
import com.freelance.netanel.androidsearchapp.adapters.HistoryAdapter;
import com.freelance.netanel.androidsearchapp.repository.HistoryRepository;
import com.freelance.netanel.androidsearchapp.repository.IHistoryRepository;
import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.adapters.ResultAdapter;
import com.freelance.netanel.androidsearchapp.model.Product;

import java.io.IOException;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CHILD_RESULTS = 0;
    private static final int CHILD_HISTORY = 1;
    private API mAPI;

    private IHistoryRepository mIHistoryRepository;

    private ResultAdapter mResultadapter;
    private HistoryAdapter mHistoryAdapter;

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

        mAPI = new API();
        buildUI();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mIHistoryRepository = new HistoryRepository(getApplicationContext());
        mHistoryAdapter.setItems(mIHistoryRepository.getSearchHistory());
        mHistoryAdapter.setCallBack(new HistoryAdapter.IHistoryAdapterCallBack() {
            @Override
            public void onItemClick(String query, boolean submit) {
                mSearchView.setQuery(query, submit);
            }

            @Override
            public void onItemClearClick() {
                mIHistoryRepository.clear();
            }
        });
        mResultadapter.setCallback(new ResultAdapter.IResultAdapterCallBack() {
            @Override
            public void onItemClick(Product item) {
                openProductActivity(item);
            }
        });

        mAPI.setDataFetchCallback(new API.IDataFetcherCallback() {
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
        mHistoryAdapter = new HistoryAdapter();

        rvHistory.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvHistory.setAdapter(mHistoryAdapter);

        rvResults.setLayoutManager(mListLayoutManager);
        rvResults.setAdapter(mResultadapter);

        rvHistory.addItemDecoration(new DividerItemDecoration(getDrawable(R.drawable.divider_horizontal)));
    }

    private void createSearchView(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.menu_action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem,new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                viewSwitcher.setDisplayedChild(CHILD_HISTORY);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if(viewSwitcher.getDisplayedChild() == CHILD_HISTORY) {
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
                mIHistoryRepository.addSearchQuery(query);
                mHistoryAdapter.setItems(mIHistoryRepository.getSearchHistory());
                progress.setVisibility(View.VISIBLE);
                mAPI.searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty() && viewSwitcher.getDisplayedChild() != CHILD_HISTORY) {
                    viewSwitcher.setDisplayedChild(CHILD_HISTORY);
                }
                mHistoryAdapter.setItemsFilteredByName(mIHistoryRepository.getSearchHistory(),newText);
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
