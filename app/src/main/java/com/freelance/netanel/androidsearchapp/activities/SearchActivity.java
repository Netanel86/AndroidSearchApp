package com.freelance.netanel.androidsearchapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.freelance.netanel.androidsearchapp.API;
import com.freelance.netanel.androidsearchapp.DividerItemDecoration;
import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.SearchListItemTouchListener;
import com.freelance.netanel.androidsearchapp.SearchResultAdapter;
import com.freelance.netanel.androidsearchapp.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private API api;
    private int page;
    private List<Product> searchResults;
    private SearchResultAdapter resultAdapter;
    private LinearLayoutManager listLayoutManager;
    private GridLayoutManager gridLayoutManager;

    @BindView(R.id.activity_search_rv_results)
    public RecyclerView rvResults;

    @BindView(R.id.activity_search_et_search)
    public EditText etSearch;

    @BindView(R.id.activity_search_btn_search)
    public Button btnSearch;

    @BindView(R.id.activity_search_btn_list)
    public Button btnHorizontal;

    @BindView(R.id.activity_search_btn_grid)
    public Button btnVertical;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_search_btn_search:
                initiateSearch();
                break;

            case R.id.activity_search_btn_list:
                setLayoutManager(listLayoutManager,SearchResultAdapter.LIST);
                break;

            case R.id.activity_search_btn_grid:
                setLayoutManager(gridLayoutManager,SearchResultAdapter.GRID);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initButterknife();

        api = new API();
        buildUI();
    }

    private void initButterknife() {
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
    }

    private void buildUI() {
        listLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.gridColCount));
        resultAdapter = new SearchResultAdapter(null);

        rvResults.setLayoutManager(listLayoutManager);
        rvResults.setAdapter(resultAdapter);

        btnSearch.setOnClickListener(this);
        btnHorizontal.setOnClickListener(this);
        btnVertical.setOnClickListener(this);

        rvResults.addOnItemTouchListener(
                new SearchListItemTouchListener(getApplicationContext(), rvResults,
                        new SearchListItemTouchListener.IRecyclerTouchListener() {
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
        searchResults = results;
//        if (resultAdapter == null) {
//            resultAdapter = new SearchResultAdapter(results);
//            rvResults.setAdapter(resultAdapter);
//        } else {
//            resultAdapter.setResults(results);
//        }
        resultAdapter.setResults(results);
    }

    private void setLayoutManager(RecyclerView.LayoutManager layoutManager, int viewType) {
        if (rvResults.getLayoutManager() != layoutManager) {
            rvResults.setLayoutManager(layoutManager);
            resultAdapter.setLayout(viewType);
        }
    }

    private void initiateSearch() {
        page = parseInt(etSearch.getText().toString());
        if (page != 0) {
            toast("Loading results...", Toast.LENGTH_SHORT);
            searchResults = new ArrayList();
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
