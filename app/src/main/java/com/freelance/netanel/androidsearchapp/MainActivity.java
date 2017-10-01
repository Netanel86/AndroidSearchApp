package com.freelance.netanel.androidsearchapp;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    private List<ResultItem> results;
    private SearchResultRVAdapter resultAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private IListItemParser listParser;

    @BindView(R.id.rv_results)
    RecyclerView rvResults;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.btn_search)
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listParser = new ListItemParser();

        initButterknife();

        buildUI();
    }

    private void initButterknife()
    {
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
    }
    private void buildUI()
    {
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvResults.setLayoutManager(layoutManager);

        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                results = getData();
                populateResults(results);
            }});

        rvResults.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(), rvResults,
                new RecyclerItemListener.IRecyclerTouchListener() {
                    @Override
                    public void onClickItem(View view, int position) {
                        Toast.makeText(view.getContext(), results.get(position) + " " + position, Toast.LENGTH_SHORT).show();
                    }
                }));

        rvResults.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.recycler_divider)));

    }

    private List<ResultItem> getData()
    {
        return listParser.parse(this);
    }

    private void populateResults(List<ResultItem> results) {
        if(resultAdapter == null){
            resultAdapter = new SearchResultRVAdapter(results);
            rvResults.setAdapter(resultAdapter);
        }
        else {
            resultAdapter.setResults(results);
            resultAdapter.notifyDataSetChanged();
        }
    }
}
