package com.vaca.chip8

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


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
        holder.myTextView.background = ContextCompat.getDrawable(
            context, if (mSympData[position].select) {
                R.drawable.symp_bg2
            } else {
                R.drawable.symp_bg1
            }
        )
        if (mSympData[position].select) {
            holder.myTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.myTextView.setTextColor(ContextCompat.getColor(context, R.color.login_black))
        }
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mSympData.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var myTextView: TextView = itemView.findViewById(R.id.t)
        override fun onClick(view: View) {
            var s = 0
            for (k in mSympData) {
                if (k.select) {
                    s++
                }
            }

            mSympData[layoutPosition].select = !mSympData[layoutPosition].select

            notifyItemChanged(layoutPosition)


        }

        init {
            itemView.setOnClickListener(this)
        }
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