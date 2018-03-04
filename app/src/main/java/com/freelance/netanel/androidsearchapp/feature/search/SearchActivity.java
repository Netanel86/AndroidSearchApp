package com.freelance.netanel.androidsearchapp.feature.search;
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
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.freelance.netanel.androidsearchapp.feature.search.history.DividerItemDecoration;
import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.feature.search.history.HistoryAdapter;
import com.freelance.netanel.androidsearchapp.feature.search.results.ResultAdapter;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class SearchActivity extends AppCompatActivity
        implements View.OnClickListener, SearchContract.IView {

    @BindView(R.id.activity_search_progress)
    public View progress;

    @BindView(R.id.activity_search_vs)
    public ViewSwitcher viewSwitcher;

    @BindView(R.id.activity_search_rv_results)
    public RecyclerView rvResults;

    @BindView(R.id.activity_search_rv_history)
    public RecyclerView rvHistory;

    @BindView(R.id.activity_search_btn_list)
    public ImageButton listButton;

    @BindView(R.id.activity_search_btn_grid)
    public ImageButton gridButton;

    private LinearLayoutManager listLayoutManager;
    private GridLayoutManager gridLayoutManager;

    private SearchView searchView;

    @Inject
    public SearchContract.IPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        presenter.bindView(this);

        initButterknife();

        buildUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
        listButton.setOnClickListener(this);
        gridButton.setOnClickListener(this);
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
                presenter.onButtonListClicked();
                break;

            case R.id.activity_search_btn_grid:
                presenter.onButtonGridClicked();
                break;
        }
    }

    private void initButterknife() {
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
    }

    private void buildUI() {
        listLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.grid_col_count));

        rvResults.setAdapter(new ResultAdapter(presenter.getResultsPresenter()));
        rvHistory.setAdapter(new HistoryAdapter(presenter.getHistoryPresenter()));

        rvResults.setLayoutManager(listLayoutManager);
        rvHistory.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rvHistory.addItemDecoration(new DividerItemDecoration(getDrawable(R.drawable.divider_horizontal)));
    }

    private void createSearchView(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.menu_action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        presenter.onExpandSearchClicked();
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        presenter.onCollapseSearchClicked();
                        return true;
                    }
                });
        searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onQuerySubmit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.onQueryTextChanged(newText);
                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                presenter.onQueryFocusChanged(hasFocus);
            }
        });
    }

    private void toast(String message, int length) {
        Toast.makeText(this, message, length).show();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void setLayoutList() {
        rvResults.setLayoutManager(listLayoutManager);
    }

    @Override
    public void setLayoutGrid() {
        rvResults.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void showViewChild(int childId) {
        viewSwitcher.setDisplayedChild(childId);
    }

    @Override
    public void showLongToast(String message) {
        toast(message,Toast.LENGTH_LONG);
    }

    @Override
    public void showMessageFailed() {
        showLongToast(getResources().getString(R.string.message_load_failed));
    }

    @Override
    public void setSearchQuery(String query, boolean submit) {
        searchView.setQuery(query,submit);
    }

    @Override
    public void clearQueryFocus() {
        searchView.clearFocus();
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (!enabled) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}
