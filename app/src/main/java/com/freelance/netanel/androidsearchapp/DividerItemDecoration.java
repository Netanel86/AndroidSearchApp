package com.freelance.netanel.androidsearchapp;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Netanel on 22/09/2017.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable divider;
    public DividerItemDecoration(Drawable divider)
    {
        this.divider = divider;
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < parent.getChildCount(); i++)
        {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left,top,right,bottom);
            divider.draw(canvas);
        }
    }
}


