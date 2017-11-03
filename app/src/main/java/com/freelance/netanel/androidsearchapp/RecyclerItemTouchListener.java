package com.freelance.netanel.androidsearchapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Netanel on 22/09/2017.
 */

public class RecyclerItemTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;

    public interface IRecyclerTouchListener
    {
        void onClickItem(View view,int position);
    }

    public RecyclerItemTouchListener(Context context, final RecyclerView rv, final IRecyclerTouchListener listener)
    {
        gestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        View view = rv.findChildViewUnder(e.getX(),e.getY());
                        listener.onClickItem(view,rv.getChildAdapterPosition(view));
                        return true;
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(),e.getY());

        return child != null && gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
