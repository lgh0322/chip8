package com.vaca.chip8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaca.chip8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val symptomList = arrayOf("1","2","3","C","4","5","6","D","7","8","9","E","A","0","B","F")

    inner class touchClass(re:RecyclerView): OnKeyBoardTouchListener(re) {
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }

        override fun downClick(holder: RecyclerView.ViewHolder?) {
            if (holder != null && holder is KeyBoradAdapter.ViewHolder) {
                Log.e("fuck","fuck11111  ${holder.layoutPosition}")
                holder.myTextView.background = ContextCompat.getDrawable(
                    this@MainActivity,
                        R.drawable.symp_bg2
                )

                    holder.myTextView.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))

            }
        }

        override fun upClick(holder: RecyclerView.ViewHolder?) {
            if (holder != null&& holder is KeyBoradAdapter.ViewHolder) {
                Log.e("fuck","fuck2222  ${holder.layoutPosition}")
                holder.myTextView.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.symp_bg1)
                holder.myTextView.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.login_black))
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sympAdapter = KeyBoradAdapter(this).apply {
            addAll(symptomList)
        }

        val wordRecycler: RecyclerView =binding.ga
        wordRecycler.layoutManager = GridLayoutManager(this, 4)

        wordRecycler.adapter = sympAdapter


        binding.ga.addOnItemTouchListener(touchClass(binding.ga))
    }
}