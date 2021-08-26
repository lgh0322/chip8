package com.vaca.chip8;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class TouchLayout extends LinearLayout {

    private Context context;

    public TouchLayout(Context context) {
        super(context);
        this.context=context;
    }

    public TouchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int count=ev.getPointerCount();
        switch (ev.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                Log.i("TAG-->","数量:"+count);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i("TAG-->","多指数量:"+count);
                return false;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}