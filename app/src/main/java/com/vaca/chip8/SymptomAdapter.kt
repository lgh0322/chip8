package com.vaca.chip8

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class SymptomAdapter(var context: Context) : RecyclerView.Adapter<SymptomAdapter.ViewHolder>() {
    private val mSympData: MutableList<SympBean> = ArrayList()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.symp_layout, parent, false)
        return ViewHolder(view)
    }

    fun addAll(userBean: Array<String>) {
        mSympData.clear()
        for (k in userBean) {
            mSympData.add(SympBean(k))
        }
        notifyDataSetChanged()
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myTextView.text = mSympData[position].name

    }

    // total number of cells
    override fun getItemCount(): Int {
        return mSympData.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        var myTextView: TextView = itemView.findViewById(R.id.t)



    }

    fun returnSymp(): List<String> {
        val x: MutableList<String> = ArrayList()
        for (k in mSympData) {
            if (k.select) {
                x.add(k.name)
            }
        }
        return x
    }

    fun setSelect(a: Int) {
        mSympData[a].select = true
    }

    fun setUnSelect(a: Int) {
        mSympData[a].select = false
    }


}