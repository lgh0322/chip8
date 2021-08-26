package com.vaca.chip8.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import com.vaca.chip8.R
import java.lang.Thread.sleep


class Chip8View : SurfaceView,Runnable {
    private val wavePaint = Paint()
    private val bgPaint = Paint()

    var chip8Program:UByteArray?=null

    private val program:UByteArray
    get() = chip8Program!!

    private var pc=0x200
    private val stack=IntArray(16){
        0
    }
    private val vRegister=IntArray(16){
        0
    }
    private var sp=0


    var canUpdate=false

    fun clearScreen(){
        for(k in screenBuffer.indices){
            screenBuffer[k]=false
        }
        canUpdate=true
    }



    fun emulate(){
        pc=0
        val opcode=program[pc].toInt().shl(8).or(program[pc+1].toInt())
        Log.e("fuyck",opcode.toString())

        when(opcode.and(0xf000).shr(12)){
            0->{
                when(opcode){
                    0x00E0->{
                        clearScreen()
                        pc+=2
                    }
                    0x00EE->{
                        sp--
                        pc=stack[sp]
                        pc+=2
                    }
                    else->{

                    }
                }
            }
            1->{
                pc=opcode.and(0xfff)
            }
            2->{
                stack[sp]=pc
                sp++
                pc=opcode.and(0xfff)
            }
            3->{
                if(vRegister[opcode.and(0xf00).shr(8)]==opcode.and(0xff)){
                    pc+=4
                }else{
                    pc+=2
                }
            }
            4->{
                if(vRegister[opcode.and(0x0f00).shr(8)]!=opcode.and(0x00ff)){
                    pc+=4
                }else{
                    pc+=2
                }

            }
            5->{
                if(vRegister[opcode.and(0x0f00).shr(8)]==vRegister[opcode.and(0x00f0).shr(4)]){
                    pc+=4
                }else{
                    pc+=2
                }
            }
            6->{
                vRegister[opcode.and(0x0f00).shr(8)]=opcode.and(0x00ff)
                pc+=2
            }
            7->{
                vRegister[opcode.and(0x0f00).shr(8)]+=opcode.and(0x00ff)
                pc+=2
            }
            8->{
                when(opcode.and(0xf)){
                    0->{

                    }
                    1->{

                    }
                    2->{

                    }
                    3->{

                    }
                    4->{

                    }
                    5->{

                    }
                    6->{

                    }
                    7->{

                    }
                    0xE->{

                    }
                    else->{

                    }

                }
            }
            9->{

            }
            0xA->{

            }
            0xB->{

            }
            0xC->{

            }
            0xD->{

            }
            0xE->{
                when(opcode.and(0xff)){
                    0x9E->{

                    }
                    0xA1->{

                    }
                    else->{

                    }
                }
            }
            0xF->{
                when(opcode.and(0xff)){
                    0x07->{

                    }
                    0x0A->{

                    }
                    0x15->{

                    }
                    0x18->{

                    }
                    0x1E->{

                    }
                    0x29->{

                    }
                    0x33->{

                    }
                    0x55->{

                    }
                    0x65->{

                    }
                    else->{

                    }
                }
            }
        }
    }











   private var surfaceHolder: SurfaceHolder = this.holder

    private val screenBuffer=BooleanArray(64*32){
        false
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
           sleep(10)
           if(surfaceHolder.surface.isValid&&canUpdate){
               canUpdate=false
               val canvas=surfaceHolder.lockCanvas()
               val h=height.toFloat()/32
               val w=width.toFloat()/64
               for(k in 0 until 64){
                   for(j in 0 until 32){
                       if(screenBuffer[j*64+k]){
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