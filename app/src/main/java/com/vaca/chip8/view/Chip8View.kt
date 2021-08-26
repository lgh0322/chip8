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
    private val keyboard=IntArray(16){
        0
    }
    private var addrRegister=0
    private var sp=0

    private var delayTimer=0
    private var soundTimer=0

    var canUpdate=false

    fun clearScreen(){
        for(k in screenBuffer.indices){
            screenBuffer[k]=false
        }
        canUpdate=true
    }


    fun isKeyPress():Boolean{
        for(k in keyboard){
            if(k==1){
                return true
            }
        }
        return false
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
                    }
                    0x00EE->{
                        sp--
                        pc=stack[sp]
                    }
                    else->{

                    }
                }
            }
            1->{
                pc=opcode.and(0xfff)
                pc-=2
            }
            2->{
                stack[sp]=pc
                sp++
                pc=opcode.and(0xfff)
                pc-=2
            }
            3->{
                if(vRegister[opcode.and(0xf00).shr(8)]==opcode.and(0xff)){
                    pc+=2
                }
            }
            4->{
                if(vRegister[opcode.and(0x0f00).shr(8)]!=opcode.and(0x00ff)){
                    pc+=2
                }

            }
            5->{
                if(vRegister[opcode.and(0x0f00).shr(8)]==vRegister[opcode.and(0x00f0).shr(4)]){
                    pc+=2
                }
            }
            6->{
                vRegister[opcode.and(0x0f00).shr(8)]=opcode.and(0x00ff)
            }
            7->{
                vRegister[opcode.and(0x0f00).shr(8)]+=opcode.and(0x00ff)
            }
            8->{
                val x=opcode.and(0x0f00).shr(8)
                val y=opcode.and(0x00f0).shr(4)
                when(opcode.and(0xf)){
                    0->{
                        vRegister[x]=vRegister[y]
                    }
                    1->{
                        vRegister[x]=vRegister[x].or(vRegister[y])
                    }
                    2->{
                        vRegister[x]=vRegister[x].and(vRegister[y])
                    }
                    3->{
                        vRegister[x]=vRegister[x].xor(vRegister[y])
                    }
                    4->{
                        if(vRegister[x]+vRegister[y]>255){
                            vRegister[0xf]=1
                            vRegister[x]+=vRegister[y]-255
                        }else{
                            vRegister[0xf]=0
                            vRegister[x]+=vRegister[y]
                        }
                    }
                    5->{
                        if(vRegister[x]-vRegister[y]<0){
                            vRegister[0xf]=0
                            vRegister[x]=vRegister[x]-vRegister[y]+255
                        }else{
                            vRegister[0xf]=1
                            vRegister[x]-=vRegister[y]
                        }
                    }
                    6->{
                        vRegister[0xf]=vRegister[x].and(0x1)
                        vRegister[x]=vRegister[x].shr(1)
                    }
                    7->{
                        if(vRegister[y]-vRegister[x]<0){
                            vRegister[0xf]=0
                            vRegister[x]=vRegister[y]-vRegister[x]+255
                        }else{
                            vRegister[0xf]=1
                            vRegister[x]=vRegister[y]-vRegister[x]
                        }
                    }
                    0xE->{
                        vRegister[0xf]=vRegister[x].shr(7)
                        vRegister[x]=vRegister[x].shl(1)
                    }
                    else->{

                    }

                }
            }
            9->{
                val x=opcode.and(0x0f00).shr(8)
                val y=opcode.and(0x00f0).shr(4)
                if(vRegister[x]!=vRegister[y]){
                    pc+=2
                }
            }
            0xA->{
                addrRegister=opcode.and(0xfff)
            }
            0xB->{
                pc=opcode.and(0xfff)+vRegister[0]
                pc-=2
            }
            0xC->{
                val x=opcode.and(0x0f00).shr(8)
                val n=opcode.and(0xff)
                vRegister[x]=(0..255).random().and(n)
            }
            0xD->{
                val x=opcode.and(0x0f00).shr(8)
                val y=opcode.and(0x00f0).shr(4)
                val location=y*64+x
                val height=opcode.and(0xf)
                vRegister[0xf]=0
                for(yline in 0 until height){
                    val pixel=program[addrRegister+yline].toInt()
                    for(xline in 0 until 8){
                        val there=screenBuffer[location]
                        if(there){
                            if(pixel.and(0x80.shr(xline))!=0){
                                vRegister[0xf]=1
                                screenBuffer[location]=false
                            }
                        }else{
                            if(pixel.and(0x80.shr(xline))!=0){
                                screenBuffer[location]=true
                            }
                        }
                    }
                }
                canUpdate=true
            }
            0xE->{
                val x=opcode.and(0x0f00).shr(8)
                when(opcode.and(0xff)){
                    0x9E->{
                        if(keyboard[vRegister[x]]==1){
                            pc+=2
                        }
                    }
                    0xA1->{
                        if(keyboard[vRegister[x]]==0){
                            pc+=2
                        }
                    }
                    else->{

                    }
                }
            }
            0xF->{
                val x=opcode.and(0x0f00).shr(8)
                when(opcode.and(0xff)){
                    0x07->{
                        vRegister[x]=delayTimer
                    }
                    0x0A->{
                        if(isKeyPress()){
                            pc+=2
                        }
                        pc-=2
                    }
                    0x15->{
                        delayTimer=vRegister[x]

                    }
                    0x18->{
                        soundTimer=vRegister[x]
                    }
                    0x1E->{
                        if(vRegister[x]+addrRegister>0xfff){
                            addrRegister=vRegister[x]+addrRegister-0xfff
                            vRegister[0xf]=1
                        }else{
                            addrRegister+=vRegister[x]
                            vRegister[0xf]=0
                        }

                    }
                    0x29->{
                        addrRegister=vRegister[x]*0x5
                    }
                    0x33->{
                        program[addrRegister]=(vRegister[x]/100).toUByte()
                        program[addrRegister+1]=((vRegister[x]/10)%10).toUByte()
                        program[addrRegister+2]=(vRegister[x]%10).toUByte()
                    }
                    0x55->{
                        for(k in 0..x){
                            program[addrRegister+k]=vRegister[k].toUByte()
                        }
                    }
                    0x65->{
                        for(k in 0..x){
                            vRegister[k]=program[addrRegister+k].toInt()
                        }
                    }
                    else->{

                    }
                }
            }
        }
        pc+=2
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