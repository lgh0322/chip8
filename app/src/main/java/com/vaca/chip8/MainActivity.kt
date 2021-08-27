package com.vaca.chip8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaca.chip8.adapter.KeyBoradAdapter
import com.vaca.chip8.databinding.ActivityMainBinding
import com.vaca.chip8.utils.OnKeyBoardTouchListener
import android.os.Vibrator
import com.vaca.chip8.data.Chip8Font
import com.vaca.chip8.data.fuck


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    inner class TouchClass(re:RecyclerView): OnKeyBoardTouchListener(re) {
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }

        override fun downClick(vh: RecyclerView.ViewHolder?) {
            if (vh != null && vh is KeyBoradAdapter.ViewHolder) {
                binding.chip8.keyboard[vh.layoutPosition]=1
                Log.e("fuck","fuck11111  ${vh.layoutPosition}")
                vibrator.vibrate(100)
                vh.myTextView.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.symp_bg2)
                vh.myTextView.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))

            }
        }

        override fun upClick(vh: RecyclerView.ViewHolder?) {
            if (vh != null&& vh is KeyBoradAdapter.ViewHolder) {
                binding.chip8.keyboard[vh.layoutPosition]=0
                Log.e("fuck","fuck2222  ${vh.layoutPosition}")
                vh.myTextView.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.symp_bg1)
                vh.myTextView.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.login_black))
            }
        }

    }

    lateinit var vibrator: Vibrator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
       vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        setContentView(binding.root)
        val sympAdapter = KeyBoradAdapter(this).apply {
            addAll()
        }
        val wordRecycler: RecyclerView =binding.ga
        wordRecycler.layoutManager = GridLayoutManager(this, 4)
        wordRecycler.adapter = sympAdapter
        binding.ga.addOnItemTouchListener(TouchClass(binding.ga))
        binding.chip8.resume()


        val gg= UByteArray(4096){
            0.toUByte()
        }

        for(k in Chip8Font.chip8_fontset.indices){
            gg[k]=Chip8Font.chip8_fontset[k]
        }
        for(k in fuck.gaga.indices){
            gg[k+512]=fuck.gaga[k]
        }
        binding.chip8.chip8Program=gg
        binding.chip8.startProgram()

    }
}