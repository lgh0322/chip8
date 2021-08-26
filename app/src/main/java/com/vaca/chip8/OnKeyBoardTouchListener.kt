package com.vaca.chip8


import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.vaca.chip8.TouchData
import android.view.MotionEvent


abstract class OnKeyBoardTouchListener(private val recyclerView: RecyclerView) :
    OnItemTouchListener {
    var touchData = arrayOfNulls<TouchData>(16)
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return true
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        when (e.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val index1 = e.actionIndex
                if (index1 >= 2) {
                    return
                }
                touchData[index1] = TouchData(e.getX(index1), e.getY(index1), true)
                down(e.getX(index1), e.getY(index1))
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_POINTER_UP -> {
                val index2 = e.actionIndex
                if (index2 >= 2) {
                    return
                }
                if (touchData[index2]!!.press) {
                    touchData[index2]!!.press = false
                    up(touchData[index2]!!.x, touchData[index2]!!.y)
                } else {
                    var k = 0
                    while (k < 16) {
                        if (touchData[k] != null) {
                            if (touchData[k]!!.press) {
                                touchData[k]!!.press = false
                                up(touchData[k]!!.x, touchData[k]!!.y)
                                break
                            }
                        }
                        k++
                    }
                }
            }
        }
    }

    fun down(x: Float, y: Float) {
        val child = recyclerView.findChildViewUnder(x, y)
        if (child != null) {
            val vh = recyclerView.getChildViewHolder(child)
            downClick(vh)
        }
    }

    fun up(x: Float, y: Float) {
        val child = recyclerView.findChildViewUnder(x, y)
        if (child != null) {
            val vh = recyclerView.getChildViewHolder(child)
            upClick(vh)
        }
    }

    abstract fun downClick(vh: RecyclerView.ViewHolder?)
    abstract fun upClick(vh: RecyclerView.ViewHolder?)
}