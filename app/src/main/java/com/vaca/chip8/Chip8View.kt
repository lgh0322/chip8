package com.vaca.chip8

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.core.content.ContextCompat



class Chip8View : SurfaceView,Runnable {
    private val wavePaint = Paint()
    private val bgPaint = Paint()

    var surfaceHolder: SurfaceHolder = this.holder

    private val booleanArray=BooleanArray(64*32){
        (it %2)==0
    }

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }






    private fun init() {
        wavePaint.apply {
            color = getColor(R.color.wave_color)
            style = Paint.Style.FILL
        }

        bgPaint.apply {
            color = getColor(R.color.gray)
            style = Paint.Style.FILL

        }


    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

    }



    private fun getColor(resource_id: Int): Int {
        return ContextCompat.getColor(context, resource_id)
    }
    var t1 = System.currentTimeMillis()
    var t2 = 0
    var t3 = 0L
    var t4 = 0
    override fun run() {
       while (true){
           if(surfaceHolder.surface.isValid){
               t2++
               t3 = System.currentTimeMillis()
               if (t3 - t1 > 1000) {
             Log.e("fuck",t2.toString())
                   t1 = t3
                   t4 = t2
                   t2 = 0
               }
               val canvas=surfaceHolder.lockCanvas()
               val h=height.toFloat()/32
               val w=width.toFloat()/64
               for(k in 0 until 64){
                   for(j in 0 until 32){
                       if(booleanArray[j*64+k]){
                           canvas.drawRect(k*w,j* h,k* w +w,j* h +h,bgPaint)
                       }else{
                           canvas.drawRect(k* w,j* h,k* w +w,j* h +h,wavePaint)
                       }
                   }
               }
               surfaceHolder.unlockCanvasAndPost(canvas)
           }
       }
    }
    fun resume() {
        val thread = Thread(this)
        thread.start()
    }
}