package com.vaca.chip8

import android.content.Context
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList


class KeyBoradAdapter(var context: Context) : RecyclerView.Adapter<KeyBoradAdapter.ViewHolder>() {
    private val mKeyBoardData: MutableList<KeyBoardBean> = ArrayList()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.symp_layout, parent, false)
        return ViewHolder(view)
    }

    fun addAll(userBean: Array<String>) {
        mKeyBoardData.clear()
        for (k in userBean) {
            mKeyBoardData.add(KeyBoardBean(k))
        }
        notifyDataSetChanged()
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myTextView.text = mKeyBoardData[position].name

    }

    // total number of cells
    override fun getItemCount(): Int {
        return mKeyBoardData.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        var myTextView: TextView = itemView.findViewById(R.id.t)



    }

    fun returnSymp(): List<String> {
        val x: MutableList<String> = ArrayList()
        for (k in mKeyBoardData) {
            if (k.select) {
                x.add(k.name)
            }
        }
        return x
    }

    fun setSelect(a: Int) {
        mKeyBoardData[a].select = true
    }

    fun setUnSelect(a: Int) {
        mKeyBoardData[a].select = false
    }


}