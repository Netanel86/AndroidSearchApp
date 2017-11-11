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
    private API api;

    private IHistoryRepository historyRepository;

    private ResultAdapter resultAdapter;
    private HistoryAdapter historyAdapter;

    private LinearLayoutManager listLayoutManager;
    private GridLayoutManager gridLayoutManager;

    private SearchView searchView;

    @BindView(R.id.activity_search_progress)
    public  View progress;

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

        api = new API();
        buildUI();
    }

    @Override
    protected void onStart() {
        super.onStart();

        historyRepository = new HistoryRepository(getApplicationContext());
        historyAdapter.setItems(historyRepository.getSearchHistory());
        historyAdapter.setCallBack(new HistoryAdapter.IHistoryAdapterCallBack() {
            @Override
            public void onItemClick(String query) {
                searchView.setQuery(query,true);
            }
        });
        resultAdapter.setCallback(new ResultAdapter.IResultAdapterCallBack() {
            @Override
            public void onItemClick(Product item) {
                openProductActivity(item);
            }
        });

        api.setDataFetchCallback(new API.IDataFetcherCallback() {
            @Override
            public void onDataFetch(final List<Product> items) {
                SearchActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setVisibility(View.GONE);
                        if(items != null){
                            resultAdapter.setResults(items);
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

        MenuItem clearHistoryItem = menu.findItem(R.id.menu_action_clear);
        clearHistoryItem.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        historyRepository.clear();
                        historyAdapter.setItems(null);
                        return false;
                    }
                });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_search_btn_list:
                setResultsLayout(listLayoutManager, ResultAdapter.LAYOUT_TYPE_LIST);
                break;

            case R.id.activity_search_btn_grid:
                setResultsLayout(gridLayoutManager, ResultAdapter.LAYOUT_TYPE_GRID);
                break;
        }
    }

    private void initButterknife() {
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
    }

    private void buildUI() {
        listLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.grid_col_count));
        resultAdapter = new ResultAdapter(null);
        historyAdapter = new HistoryAdapter();

        rvHistory.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvHistory.setAdapter(historyAdapter);

        rvResults.setLayoutManager(listLayoutManager);
        rvResults.setAdapter(resultAdapter);


//        rvResults.addItemDecoration(
//                new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(),
//                        R.drawable.divider_horizontal)));
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
                viewSwitcher.setDisplayedChild(CHILD_RESULTS);
                return true;
            }
        });
        searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewSwitcher.setDisplayedChild(CHILD_RESULTS);
                historyRepository.addSearchQuery(query);
                historyAdapter.setItems(historyRepository.getSearchHistory());
                progress.setVisibility(View.VISIBLE);
                api.searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()) {
                    viewSwitcher.setDisplayedChild(CHILD_HISTORY);
                    // TODO: 05/11/2017 add filter for search result history
                }
                return false;
            }
        });
    }

    private void openProductActivity(Product product) {
        Intent intentProductView = new Intent(getApplicationContext(), ProductActivity.class);

        intentProductView.putExtra("product_bundle", product);
        startActivity(intentProductView);
    }

    private void setResultsLayout(RecyclerView.LayoutManager targetLayoutManager, int targetViewType) {
        if (resultAdapter.getCurrentLayout() != targetViewType) {
            rvResults.setLayoutManager(targetLayoutManager);
            resultAdapter.setLayout(targetViewType);
        }
    }

    private void toast(String message, int length) {
        Toast.makeText(this, message, length).show();
    }
}
