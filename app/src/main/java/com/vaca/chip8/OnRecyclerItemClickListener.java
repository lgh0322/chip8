package com.vaca.chip8;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * RecyclerView 的自定义点击监听事件。参考于：
 * http://blog.csdn.net/liaoinstan/article/details/51200600
 * <p>
 * author: jby
 * created at 2016/7/27 15:01
 */
public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat gestureDetector;
    private RecyclerView recyclerView;
    Data[] fuck=new Data[20];

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.gestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return true;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                int index1=e.getActionIndex();
                if(index1>=2){
                    return;
                }
                fuck[index1]=new Data(e.getX(index1), e.getY(index1),true);
                down(e.getX(index1), e.getY(index1));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_POINTER_UP:
                int index2=e.getActionIndex();
                if(index2>=2){
                    return;
                }
                if(fuck[index2].press){
                    fuck[index2].press=false;
                    up(fuck[index2].x,fuck[index2].y);
                }else{
                    for(int k=0;k<20;k++){
                        if(fuck[k]!=null){
                            if(fuck[k].press){
                                fuck[k].press=false;
                                up(fuck[k].x,fuck[k].y);
                                break;
                            }
                        }

                    }
                }

                break;


        }


    }


    void down(float x, float y) {
        View child = recyclerView.findChildViewUnder(x, y);
        if (child != null) {
            RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
            onItemClick(vh);
        }
    }


    void up(float x, float y) {
        View child = recyclerView.findChildViewUnder(x, y);
        if (child != null) {
            RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
            onItemLongClick(vh);
        }
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                onItemClick(vh);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                onItemLongClick(vh);
            }
        }
    }

    public abstract void onItemClick(RecyclerView.ViewHolder vh);

    public abstract void onItemLongClick(RecyclerView.ViewHolder vh);
}


