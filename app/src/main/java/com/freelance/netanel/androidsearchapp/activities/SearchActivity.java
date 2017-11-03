package com.freelance.netanel.androidsearchapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.freelance.netanel.androidsearchapp.API;
import com.freelance.netanel.androidsearchapp.DividerItemDecoration;
import com.freelance.netanel.androidsearchapp.adapters.HistoryAdapter;
import com.freelance.netanel.androidsearchapp.HistoryRepository;
import com.freelance.netanel.androidsearchapp.IHistoryRepository;
import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.RecyclerItemTouchListener;
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
    private int page;

    private IHistoryRepository historyRepository;
    private List<Product> searchResults;

    private ResultAdapter resultAdapter;
    private HistoryAdapter historyAdapter;

    private LinearLayoutManager listLayoutManager;
    private GridLayoutManager gridLayoutManager;

    private SearchView searchView;


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

    @BindView(R.id.search_toolbar)
    public Toolbar tbSearch;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_search_btn_list:
                setResultsLayout(listLayoutManager, ResultAdapter.VIEWYPE_LIST);
                break;

            case R.id.activity_search_btn_grid:
                setResultsLayout(gridLayoutManager, ResultAdapter.VIEWTYPE_GRID);
                break;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initButterknife();
        setSupportActionBar(tbSearch);

        api = new API();
        buildUI();
    }

    @Override
    protected void onStart() {
        super.onStart();

        historyRepository = new HistoryRepository(getApplicationContext());
        historyAdapter.setItems(historyRepository.getSearchHistory());
        historyAdapter.setCallBack(new HistoryAdapter.IHistoryAdapterCallback() {
            @Override
            public void onItemClick(String query) {
                searchView.setQuery(query,true);
            }
        });

        viewSwitcher.setDisplayedChild(CHILD_RESULTS);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);

        MenuItem clearItem = menu.findItem(R.id.action_clear);
        clearItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                historyRepository.clear();
                historyAdapter.setItems(null);
                return false;
            }
        });

        MenuItem searchItem = menu.findItem(R.id.action_search);
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
                initiateSearch(query);
                historyRepository.addSearchQuery(query);
                historyAdapter.setItems(historyRepository.getSearchHistory());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()) {
                    viewSwitcher.setDisplayedChild(CHILD_HISTORY);
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId())
//        {
//            case R.id.action_search:
//                initiateSearch();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void initButterknife() {
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
    }

    private void buildUI() {
        listLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.gridColCount));
        resultAdapter = new ResultAdapter(null);
        historyAdapter = new HistoryAdapter();

        rvHistory.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvHistory.setAdapter(historyAdapter);

        rvResults.setLayoutManager(listLayoutManager);
        rvResults.setAdapter(resultAdapter);

        btnList.setOnClickListener(this);
        btnGrid.setOnClickListener(this);

        rvResults.addOnItemTouchListener(
                new RecyclerItemTouchListener(getApplicationContext(), rvResults,
                        new RecyclerItemTouchListener.IRecyclerTouchListener() {
                            @Override
                            public void onClickItem(View view, int position) {
                                openProductActivity(searchResults.get(position));
                            }
                        }));

        rvResults.addItemDecoration(
                new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.divider_horizontal)));
    }

    private void openProductActivity(Product product) {
        Intent intentProductView = new Intent(getApplicationContext(), ProductActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("image", product.imageUrl);
        bundle.putString("description", product.description);
        bundle.putInt("id", product.id);

        intentProductView.putExtra("product_bundle", bundle);
        startActivity(intentProductView);
    }

    private void setSearchResults(List<Product> results) {
        toast("Results Loaded!", Toast.LENGTH_SHORT);
//        if (resultAdapter == null) {
//            resultAdapter = new ResultAdapter(results);
//            rvResults.setAdapter(resultAdapter);
//        } else {
//            resultAdapter.setResults(results);
//        }
        resultAdapter.setResults(searchResults = results);
    }

    private void setResultsLayout(RecyclerView.LayoutManager targetLayoutManager, int targetViewType) {
        if (resultAdapter.getCurrentLayout() != targetViewType) {
            rvResults.setLayoutManager(targetLayoutManager);
            resultAdapter.setLayout(targetViewType);
        }
    }

    private void initiateSearch(String query) {
        page = parseInt(query);
        if (page != 0) {
            toast("Loading results...", Toast.LENGTH_SHORT);
//            searchResults = new ArrayList();
            GetDataAsyncTask getDataAsyncTask = new GetDataAsyncTask();
            getDataAsyncTask.execute(SearchActivity.this.api);
        }
    }

    private void toast(String message, int length) {
        Toast.makeText(this, message, length).show();
    }

    private int parseInt(String integerString) {
        int value = 0;
        try {
            value = Integer.parseInt(integerString);
            if (value == 0) {
                throw new NumberFormatException("Value can't be zero");
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            toast("Error: " + ex.getMessage(), Toast.LENGTH_LONG);
        }

        return value;
    }

    class GetDataAsyncTask extends AsyncTask<API, Void, List<Product>> {

        private String errorMessage;

        @Override
        protected void onPostExecute(List<Product> products) {
            if (products != null) {
                setSearchResults(products);
                viewSwitcher.setDisplayedChild(CHILD_RESULTS);
            } else {
                toast(errorMessage, Toast.LENGTH_LONG);
                errorMessage = null;
            }
        }

        @Override
        protected List<Product> doInBackground(API... params) {
            API api = params[0];
            List products = null;
            try {
                products = api.get(page);
            } catch (IOException ex) {
                ex.printStackTrace();
                errorMessage = ex.getMessage();
            }
            return products;
        }
    }
}
