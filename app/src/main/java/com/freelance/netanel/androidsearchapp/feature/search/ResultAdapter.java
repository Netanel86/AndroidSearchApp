package com.freelance.netanel.androidsearchapp.feature.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.service.ioc_container.Injector;
import com.freelance.netanel.androidsearchapp.model.Product;
import com.freelance.netanel.androidsearchapp.service.image_loader.IImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Netanel on 22/09/2017.
 */

public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int LAYOUT_TYPE_LIST = 1;
    public static final int LAYOUT_TYPE_GRID = 2;
    private static final int VIEWTYPE_LIST_EMPTY = 3;
    private static final int VIEWTYPE_GRID_EMPTY = 4;
    private static final int VIEWTYPE_LIST_ITEM = 5;
    private static final int VIEWTYPE_GRID_ITEM = 6;

    private int currentLayout = LAYOUT_TYPE_LIST;

    private List<Product> results;
    private IResultAdapterCallBack callBack;

    @Inject
    public IImageLoader imageLoader;

    public ResultAdapter() {
        super();
        Injector.getInstance().inject(this);
        this.results = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case VIEWTYPE_LIST_ITEM:
                holder = new ViewHolderItem(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.rv_item_product_list,parent,false));
                break;
            case VIEWTYPE_GRID_ITEM:
                holder = new ViewHolderItem(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.rv_item_product_grid, parent, false));
                break;
            default:
                holder = new ViewHolderEmpty(
                        LayoutInflater.from(parent.getContext())
                                .inflate( R.layout.rv_item_product_empty, parent, false));
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderItem) {
            ((ViewHolderItem)holder).bind(results.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return results.isEmpty() ? 1 : results.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewtype = 0;

        switch (currentLayout) {
            case LAYOUT_TYPE_LIST:
                if(results.isEmpty()){
                    viewtype = VIEWTYPE_LIST_EMPTY;
                } else {
                    viewtype = VIEWTYPE_LIST_ITEM;
                }
                break;
            case LAYOUT_TYPE_GRID:
                if(results.isEmpty()) {
                    viewtype = VIEWTYPE_GRID_EMPTY;
                } else {
                    viewtype= VIEWTYPE_GRID_ITEM;
                }
                break;
        }

        return viewtype;
    }

    public int getCurrentLayout() {
        return currentLayout;
    }

    public void setLayout(int layout) {
        this.currentLayout = layout;
        notifyDataSetChanged();
    }

    public void setResults(List<Product> results) {
        this.results.clear();
        if (results != null) {
            this.results.addAll(results);
        }

        notifyDataSetChanged();
    }

    public void setCallback(IResultAdapterCallBack callback) {
        ResultAdapter.this.callBack = callback;
    }

    public interface IResultAdapterCallBack {
        void onItemClick(Product item);
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {
        private static final int PLACE_HOLDER_RES  = R.drawable.ic_buybuy_logo;

        /**
         * position of current item in the collection
         */
        private int position;

        @BindView(R.id.rv_item_product_iv_image)
        public ImageView imageView;

        @BindView(R.id.rv_item_product_tv_name)
        public TextView textViewName;

        @BindView(R.id.rv_item_product_tv_description)
        public TextView textViewDescription;

        private ViewHolderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack != null) {
                        callBack.onItemClick(results.get(position));
                    }
                }
            });
        }

        private void bind(Product result, int position) {
            ViewHolderItem.this.position = position;

            textViewName.setText(result.getName());
            textViewDescription.setText(result.getDescription());

            imageLoader.load(result.getImageUrl(), ViewHolderItem.this.itemView.getContext(),
                    imageView, PLACE_HOLDER_RES);
        }
    }

    class ViewHolderEmpty extends RecyclerView.ViewHolder {

        public ViewHolderEmpty(View itemView) {
            super(itemView);
        }
    }
}
