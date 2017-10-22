package com.freelance.netanel.androidsearchapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.freelance.netanel.androidsearchapp.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity
{
    private API api;
    private List<Product> searchResults;
    private SearchResultAdapter resultAdapter;

    @BindView(R.id.activity_search_rv_results)
    RecyclerView rvResults;

    @BindView(R.id.activity_search_et_search)
    EditText etSearch;

    @BindView(R.id.activity_search_btn_search)
    Button btnSearch;

    int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initButterknife();

        api = new API();
        buildUI();
    }

    private void initButterknife()
    {
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
    }

    private void buildUI() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvResults.setLayoutManager(layoutManager);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page = parseInt(etSearch.getText().toString());
                if(page != 0) {
                    toast("Loading results...");
                    searchResults = new ArrayList();
                    GetDataAsyncTask getDataAsyncTask = new GetDataAsyncTask();
                    getDataAsyncTask.execute(SearchActivity.this.api);
                }
            }
        });

        rvResults.addOnItemTouchListener(new SearchListItemTouchListener(getApplicationContext(), rvResults,
                new SearchListItemTouchListener.IRecyclerTouchListener() {
                    @Override
                    public void onClickItem(View view, int position) {
                        Intent intentProductView = new Intent(getApplicationContext(),ProductActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("image",searchResults.get(position).imageUrl);
                        bundle.putString("description",searchResults.get(position).description);
                        bundle.putInt("id",searchResults.get(position).id);

                        intentProductView.putExtra("product_bundle",bundle);
                        startActivity(intentProductView);
                    }
                }));

        rvResults.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.divider_horizontal)));

    }

    private void toast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private int parseInt(String integerString)
    {
        int value = 0;
        try {
            value = Integer.parseInt(integerString);
            if(value == 0)
            {
                throw new NumberFormatException("Value can't be zero");
            }
        }
        catch (NumberFormatException ex)
        {
            ex.printStackTrace();
            toast("Error: value should be a number bigger than zero");
        }

        return value;
    }

    private void setSearchResults(List<Product> results) {
        toast("Results Loaded!");
        searchResults = results;
        if (resultAdapter == null) {
            resultAdapter = new SearchResultAdapter(results);
            rvResults.setAdapter(resultAdapter);
        } else {
            resultAdapter.setResults(results);
            resultAdapter.notifyDataSetChanged();
        }
    }

    class GetDataAsyncTask extends AsyncTask<API,Void,List<Product>> {

        @Override
        protected void onPostExecute(List<Product> products) {
            setSearchResults(products);
        }

        @Override
        protected List<Product> doInBackground(API... params) {
            API api = params[0];
            List products = null;
            try {
                products = api.get(page);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return products;
        }
    }
}
