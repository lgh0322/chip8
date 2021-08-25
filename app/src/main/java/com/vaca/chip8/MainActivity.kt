package com.vaca.chip8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaca.chip8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val symptomList = arrayOf("1","2","3","C","4","5","6","D","7","8","9","E","A","0","B","F")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sympAdapter = SymptomAdapter(this).apply {
            addAll(symptomList)
        }

        val wordRecycler: RecyclerView =binding.ga
        wordRecycler.layoutManager = GridLayoutManager(this, 4)

        wordRecycler.adapter = sympAdapter

    }
}