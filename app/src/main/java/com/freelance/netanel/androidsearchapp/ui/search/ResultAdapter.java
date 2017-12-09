package com.freelance.netanel.androidsearchapp.ui.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelance.netanel.androidsearchapp.R;
import com.freelance.netanel.androidsearchapp.domain.model.Product;
import com.freelance.netanel.androidsearchapp.ui.product.IImageLoader;
import com.freelance.netanel.androidsearchapp.ui.product.ImageLoader;

import java.util.ArrayList;
import java.util.List;

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

    private int mCurrentLayout = LAYOUT_TYPE_LIST;

    private List<Product> mResults;
    private IResultAdapterCallBack mCallBack;

    public ResultAdapter() {
        super();
        this.mResults = new ArrayList<>();
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
            ((ViewHolderItem)holder).bind(mResults.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return mResults.isEmpty() ? 1 : mResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewtype = 0;

        switch (mCurrentLayout) {
            case LAYOUT_TYPE_LIST:
                if(mResults.isEmpty()){
                    viewtype = VIEWTYPE_LIST_EMPTY;
                } else {
                    viewtype = VIEWTYPE_LIST_ITEM;
                }
                break;
            case LAYOUT_TYPE_GRID:
                if(mResults.isEmpty()) {
                    viewtype = VIEWTYPE_GRID_EMPTY;
                } else {
                    viewtype= VIEWTYPE_GRID_ITEM;
                }
                break;
        }

        return viewtype;
    }

    public int getCurrentLayout() {
        return mCurrentLayout;
    }

    public void setLayout(int layout) {
        this.mCurrentLayout = layout;
        notifyDataSetChanged();
    }

    public void setResults(List<Product> results) {
        this.mResults.clear();
        if (results != null) {
            mResults.addAll(results);
        }

        notifyDataSetChanged();
    }

    public void setCallback(IResultAdapterCallBack callback) {
        ResultAdapter.this.mCallBack = callback;
    }

    public interface IResultAdapterCallBack {
        void onItemClick(Product item);
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {
        private static final int PLACE_HOLDER_RES  = R.drawable.ic_buybuy_logo;

        /**
         * mPosition of current item in the collection
         */
        private int mPosition;
        private IImageLoader mImageLoader;

        @BindView(R.id.rv_item_product_iv_image)
        public ImageView mImageView;

        @BindView(R.id.rv_item_product_tv_name)
        public TextView mTextViewName;

        @BindView(R.id.rv_item_product_tv_description)
        public TextView mTextViewDescription;

        private ViewHolderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallBack != null) {
                        mCallBack.onItemClick(mResults.get(mPosition));
                    }
                }
            });
            mImageLoader = new ImageLoader();
        }

        private void bind(Product result, int position) {
            ViewHolderItem.this.mPosition = position;

            mTextViewName.setText(result.getName());
            mTextViewDescription.setText(result.getDescription());

            mImageLoader.load(result.getImageUrl(), ViewHolderItem.this.itemView.getContext(),
                    mImageView, PLACE_HOLDER_RES);
        }
    }

    class ViewHolderEmpty extends RecyclerView.ViewHolder {

        public ViewHolderEmpty(View itemView) {
            super(itemView);
        }
    }
}
